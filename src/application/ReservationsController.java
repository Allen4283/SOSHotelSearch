package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Desc: controller class for the reservations scene.
 */

public class ReservationsController implements Initializable {

  private ObservableList<Data> list = FXCollections.observableArrayList();
  static String url = "jdbc:derby:lib/SOSHotelAccountDB";
  private int userId;
  private String getId = "SELECT SOS.SEARCHER.USER_ID FROM SOS.SEARCHER WHERE "
      + "SOS.SEARCHER.USERNAME='" + LogInController.getClientUsername() + "'";

  @FXML
  private TableView<Data> tableView;
  @FXML
  private TableColumn<Data, String> hotelNameCol;
  @FXML
  private TableColumn<Data, String> checkInCol;
  @FXML
  private TableColumn<Data, String> checkOutCol;
  @FXML
  private Label status;


  /**
   * Desc: initializes the scene by setting up columns and adding data.
   *
   * @param resources - a ResourceBundle object
   * @param: location - location of the database
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    initCol();
    addData();
  }

  /**
   * Desc: static inner class defining the data.
   */
  public static class Data {
    private final SimpleStringProperty hotelName;
    private final SimpleStringProperty checkIn;
    private final SimpleStringProperty checkOut;

    /**
     * Desc: Data constructor.
     *
     * @param: name - the name of the hotel
     * @param: checkIn - the check in date
     * @param: checkout - the check out date
     */
    Data(String name, String checkIn, String checkOut) {
      this.hotelName = new SimpleStringProperty(name);
      this.checkIn = new SimpleStringProperty(checkIn);
      this.checkOut = new SimpleStringProperty(checkOut);
    }

    /**
     * Desc: returns the hotel name.
     *
     * @return hotelName - the hotel name
     */
    public String getHotelName() {
      return hotelName.get();
    }

    /**
     * Desc: returns the check in date.
     *
     * @return checkIn - the check in date
     */
    public String getCheckIn() {
      return checkIn.get();
    }

    /**
     * Desc: returns the check out date.
     *
     * @return checkOut - the check out date
     */
    public String getCheckOut() {
      return checkOut.get();
    }
  }

  /**
   * Desc: initializes column data in the table.
   */
  private void initCol() {
    hotelNameCol.setCellValueFactory(new PropertyValueFactory<>("hotelName"));
    checkInCol.setCellValueFactory(new PropertyValueFactory<>("checkIn"));
    checkOutCol.setCellValueFactory(new PropertyValueFactory<>("checkOut"));
  }

  /**
   * Desc: gets data from the database to add to the table.
   */
  private void addData() {
    try (Connection conn = DriverManager.getConnection(Credentials.getUrl());
         Statement stmt = conn.createStatement();
         ResultSet resultSet = stmt.executeQuery(getId)) {
      resultSet.next();
      this.userId = resultSet.getInt(1);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    final String joinHotels = "SELECT SOS.HOTEL.NAME, SOS.RESERVATIONS.CHECKIN, "
            + "SOS.RESERVATIONS.CHECKOUT"
            + " FROM SOS.RESERVATIONS INNER JOIN "
            + "SOS.HOTEL ON SOS.RESERVATIONS.HOTEL_ID=SOS.HOTEL.ID "
            + "WHERE SOS.RESERVATIONS.USER_ID=" + userId;

    try (Connection connection = DriverManager.getConnection(url);
         Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(joinHotels)) {

      // Values from Resultset object are added into list
      while (resultSet.next()) {
        String hotelName = resultSet.getString("name");
        String checkin = resultSet.getString("checkin");
        String checkout = resultSet.getString("checkout");

        list.add(new Data(hotelName, checkin, checkout));
      }
    } catch (SQLException e) {
      status.setText("Error");
      e.printStackTrace();
    }
    // Associates tableView with list items
    tableView.getItems().setAll(list);
  }

  /**
   * Desc: goes to myAccount scene.
   *
   * @param: event - ActionEvent from the button
   * @throws Exception is thrown if review scene does not exist
   */
  public void myAccount(ActionEvent event) throws Exception {
    Navigator.myAccount(event);
  }

  /**
   * Desc: goes to dashbard scene.
   *
   * @param: event - ActionEvent from the button
   * @throws Exception is thrown if dashboard scene does not exist.
   */
  public void dashboard(ActionEvent event) throws Exception {
    Navigator.dashboard(event);
  }

  /**
   * Desc: goes to logout scene.
   *
   * @param: event - ActionEvent from the button
   * @throws Exception is thrown if logout scene does not exist.
   */
  public void logout(ActionEvent event) throws Exception {
    Navigator.logout(event);
  }
}

