package application;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


/**
 * Desc: controller class for the hotel owner scene.
 */
public class HotelOwnerController implements Initializable {

  private ObservableList<Info> list = FXCollections.observableArrayList();

  @FXML
  private TableView<Info> tableView;
  @FXML
  private TableColumn<Info, Integer> nameCol;
  @FXML
  private TableColumn<Info, String> checkInCol;
  @FXML
  private TableColumn<Info, String> checkOutCol;
  @FXML
  private TableColumn<Info, String> numRoomsCol;

  /**
   * Desc: initializes column values.
   *
   * @param: location
   * @param: resources
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // function will initialize column values
    initCol();
    addData();
  }

  /**
   * Desc: information for name, check-in date, check-out date, and number of rooms.
   */
  public static class Info {
    private final SimpleStringProperty name;
    private final SimpleStringProperty checkIn;
    private final SimpleStringProperty checkOut;
    private final SimpleIntegerProperty numRoom;

    /**
     * Desc: class constructor takes in fields as parameters.
     *
     * @param checkIn  - check-in date
     * @param checkOut - check-out date
     * @param numRoom  - number of rooms
     * @param: name - full name
     */
    Info(String name, String checkIn, String checkOut, Integer numRoom) {
      this.name = new SimpleStringProperty(name);
      this.checkIn = new SimpleStringProperty(checkIn);
      this.checkOut = new SimpleStringProperty(checkOut);
      this.numRoom = new SimpleIntegerProperty(numRoom);
    }

    public String getName() {
      return name.get();
    }

    public String getCheckIn() {
      return checkIn.get();
    }

    public String getCheckOut() {
      return checkOut.get();
    }

    public Integer getNumRoom() {
      return numRoom.get();
    }
  }

  /**
   * Desc: initializes the name, check-in, check-out,
   * and number of rooms in the columns.
   */
  private void initCol() {
    nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    checkInCol.setCellValueFactory(new PropertyValueFactory<>("checkIn"));
    checkOutCol.setCellValueFactory(new PropertyValueFactory<>("checkOut"));
    numRoomsCol.setCellValueFactory(new PropertyValueFactory<>("numRoom"));

  }

  /**
   * Desc: adds reservations data to the database.
   */
  private void addData() {
    final String join = "SELECT SOS.hotel.name, sos.reservations.checkin, "
        + "sos.reservations.checkout, sos.hotel.rooms"
        + " FROM sos.hotel INNER JOIN "
        + "sos.reservations ON sos.reservations.HOTEL_ID=sos.hotel.id";
    try (Connection connection = DriverManager.getConnection(Credentials.getUrl());
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(join)) {

      // Values from Resultset object are added into list
      while (resultSet.next()) {
        String name = resultSet.getString("name");
        String checkIn = resultSet.getString("checkin");
        String checkOut = resultSet.getString("checkout");
        Integer numRooms = resultSet.getInt("rooms");

        list.add(new Info(name, checkIn, checkOut, numRooms));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    // Associates tableView with list items
    tableView.getItems().setAll(list);

  }

  /**
   * Desc: goes to login scene.
   *
   * @param: event - the ActionEvent for the button
   * @throws: Exception
   */
  public void logout(ActionEvent event) throws Exception {
    Navigator.logout(event);
  }
}

