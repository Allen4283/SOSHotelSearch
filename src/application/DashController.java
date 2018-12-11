package application;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;


/**
 * Desc: allows user to start a search by entering location,
 * check-in date, check-out date, and number of rooms.
 */
public class DashController implements Initializable {
  @FXML
  private TextField searchBar;        //searchbar where location is to be entered

  private static String location;     //location

  @FXML
  private DatePicker checkInDate;     //check-in date
  @FXML
  private DatePicker checkOutDate;    //check-out date

  @FXML
  private Label searchStatus;         //notifies the user if they try to search without
  //    filling in all required fields
  @FXML
  private Spinner roomCount;          //spinner for number of rooms

  @FXML
  private ProgressIndicator indicator;

  //allows user to increase/decrease the number of rooms
  private SpinnerValueFactory<Integer> roomCountFactory = new IntegerSpinnerValueFactory(0, 9, 1);
  private static LocalDate userCheckInDate;
  private static LocalDate userCheckOutDate;
  private static int numOfRooms;

  //Side Panel buttons

  /**
   * Desc: goes to my account scene.
   *
   * @param: event - the ActionEvent from the button
   */
  public void myAccount(ActionEvent event) throws Exception {
    Navigator.myAccount(event);
  }

  /**
   * Desc: goes to login scene.
   *
   * @param: event - the ActionEvent from the button
   */
  public void logout(ActionEvent event) throws Exception {
    Navigator.logout(event);
  }

  /**
   * Desc: performs a search by getting the location from the searchbar
   * if all required fields are filled in. If they are not filled
   * in, it will notify the user with a message.
   *
   * @param: event - the ActionEvent from the button
   */
  public void search(ActionEvent event) throws Exception {
    setLocation(searchBar.getText());

    //checks if a location was not entered
    if (location.isEmpty()) {
      searchStatus.setText("Please enter a location.");

      //checks if check-in date or check-in date were not entered
    } else if (checkInDate.getValue() == null || checkOutDate.getValue() == null) {
      searchStatus.setText("Please select check-in and check-out dates.");

      //checks if check-out date is later than check-in date
    } else if (checkInDate.getValue().compareTo(checkOutDate.getValue()) > 0
        || checkInDate.getValue().isBefore(LocalDate.now())) {
      searchStatus.setText("Please select valid check-in and check-out dates.");
      //all information was entered, goes to map scene
    } else {
      // setup a new task for UI thread to run while loading the new scene
      Task<Parent> loadAnimation = new Task<Parent>() {
        @Override
        public Parent call() throws IOException {
          searchStatus.setVisible(false);
          indicator.setVisible(true);

          // dummy data because the function needs to have the parent returned
          FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
          Parent root = loader.load();
          return root;
        }
      };

      loadAnimation.setOnSucceeded(successEvent -> {
        indicator.setVisible(false);
        searchStatus.setVisible(true);
        MapManager mapManager = new MapManager();
        mapManager.setAddress(location);                // sets map location
        mapManager.createMap();                         // creates map
        userCheckInDate = checkInDate.getValue();       // gets check-in date
        userCheckOutDate = checkOutDate.getValue();     // gets check-out date
        numOfRooms = (int) roomCount.getValue();        // gets number of rooms

        //checks for error
        if (mapManager.getErrorStatus()) {
          searchStatus.setText(mapManager.getError());    //if error, write message
        } else {
          searchStatus.setVisible(false);                 //no error
          try {
            Navigator.search(event);                      // go to search screen
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      });

      loadAnimation.setOnFailed(e -> loadAnimation.getException().printStackTrace());

      Thread thread = new Thread(loadAnimation);
      thread.start();
    }
  }

  /**
   * Desc: gets the location from the dashboard searchbar.
   *
   * @return: location - location the user entered in the searchbar
   */
  public static String getLocation() {
    return location;
  }

  /**
   * Desc: sets the location for from the user search.
   */
  public static void setLocation(String location) {
    DashController.location = location;
  }

  /**
   * Desc: gets the check-in date the user picked from the calendar.
   *
   * @return: userCheckInDate - the date the user chose to check-in to the hotel
   */
  static LocalDate getUserCheckInDate() {
    return userCheckInDate;
  }

  /**
   * Desc: gets the check-out date the user picked from the calendar.
   *
   * @return: userCheckOutDate - the date the user chose to check-out from the hotel
   */
  static LocalDate getUserCheckOutDate() {
    return userCheckOutDate;
  }

  /**
   * Desc: gets the number of rooms the user chose from the dashboard.
   *
   * @return: numOfRooms - the number of rooms the user selected
   */
  static int getNumOfRooms() {
    return numOfRooms;
  }

  /**
   * Desc: sets the number of rooms.
   */
  public void modifyRoom() {
    this.roomCount.setValueFactory(roomCountFactory);
  }

  /**
   * Desc: loads the current room count set by the user.
   *
   * @param: location
   * @param: resources
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    roomCount.setValueFactory(roomCountFactory);
  }
}