package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class HotelOwnerController implements Initializable {

  ObservableList<Info> list = FXCollections.observableArrayList();
  static String url = "jdbc:derby:lib/SOSHotelAccountDB";

  @FXML
  private TableView<Info> tableView;
  @FXML
  private TableColumn<Info, Integer> NameCol;
  @FXML
  private TableColumn<Info, String> CheckInCol;
  @FXML
  private TableColumn<Info, String> CheckOutCol;
  @FXML
  private TableColumn<Info, String> NumRoomsCol;
  @FXML
  private Label status;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // function will initialize column values
    initCol();
  }

  public static class Info {
    private final SimpleStringProperty Name;
    private final SimpleStringProperty CheckIn;
    private final SimpleStringProperty CheckOut;
    private final SimpleIntegerProperty NumRoom;
    // Class constructor takes in fields as parameters
    Info(String name, String checkIn, String checkOut, Integer numRoom) {
      this.Name = new SimpleStringProperty(name);
      this.CheckIn = new SimpleStringProperty(checkIn);
      this.CheckOut = new SimpleStringProperty(checkOut);
      this.NumRoom = new SimpleIntegerProperty(numRoom);
    }
    public String getName(){ return Name.get();}
    public String getCheckIn(){ return CheckIn.get();}
    public String getCheckOut(){ return CheckOut.get();}
    public Integer getNumRoom(){ return NumRoom.get();}
  }
  private void initCol() {
    NameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
    CheckInCol.setCellValueFactory(new PropertyValueFactory<>("CheckIn"));
    CheckOutCol.setCellValueFactory(new PropertyValueFactory<>("CheckOut"));
    NumRoomsCol.setCellValueFactory(new PropertyValueFactory<>("NumRoom"));

  }
  private void addData() {
    final String JOIN_RECIPES = "SELECT hotel.searchername, reservations.checkindata, "
        + "reservations.checkoutdate, hotel.numrooms"
        + " FROM hotel INNER JOIN "
        + "reservations ON reservations.id=hotel.id";
    try (Connection connection = DriverManager.getConnection(url);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(JOIN_RECIPES)) {

      // Values from Resultset object are added into list
      while(resultSet.next()) {
        String Name = resultSet.getString("searcherName");
        String CheckIn = resultSet.getString("checkindata");
        String CheckOut = resultSet.getString("checkoutdate");
        Integer NumRooms = resultSet.getInt("numrooms");

        list.add(new Info(Name, CheckIn,CheckOut, NumRooms));
      }
    }
    catch (SQLException e) {
      status.setText("Error");
      e.printStackTrace();
    }
    // Associates tableView with list items
    tableView.getItems().setAll(list);

  }

  public void logout(ActionEvent event) throws Exception {
    Parent Dashboard = FXMLLoader.load(getClass().getResource("Login.fxml"));
    Scene dashboard = new Scene(Dashboard);

    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    window.setScene(dashboard);
    window.show();
  }
}

