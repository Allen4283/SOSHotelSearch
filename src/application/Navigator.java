package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Desc: class containing methods to navigate around the GUI.
 */

public class Navigator {
  /**
   * Desc: goes to the dashboard scene.
   *
   * @param: event - the ActionEvent from the button
   * @throws: Exception
   */
  static void dashboard(ActionEvent event) throws Exception {
    Parent logout = FXMLLoader.load(Navigator.class.getResource("Dashboard.fxml"));
    Scene dashboardScene = new Scene(logout);

    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    window.setScene(dashboardScene);
    window.show();
  }

  /**
   * Desc: goes to the hotel info scene.
   *
   * @param: event - the ActionEvent from the button
   * @throws: Exception
   */
  static void hotelInfo(Hotel hotel, ActionEvent event) throws Exception {
    HotelController hotelController = new HotelController();
    HotelController.setHotel(hotel);

    Parent hotelInfo = FXMLLoader.load(Navigator.class.getResource("Hotel.fxml"));
    Scene hotelInfoScene = new Scene(hotelInfo);

    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    window.setScene(hotelInfoScene);
    window.show();
  }

  static void hotelInfo(ActionEvent event) throws Exception {
    HotelController hotelController = new HotelController();

    Parent hotelInfo = FXMLLoader.load(Navigator.class.getResource("Hotel.fxml"));
    Scene hotelInfoScene = new Scene(hotelInfo);

    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    window.setScene(hotelInfoScene);
    window.show();
  }

  /**
   * Desc: goes to the logout scene.
   *
   * @param: event - the ActionEvent from the button
   * @throws: Exception
   */
  static void logout(ActionEvent event) throws Exception {
    Parent logout = FXMLLoader.load(Navigator.class.getResource("Login.fxml"));
    Scene logoutScene = new Scene(logout);

    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    window.setScene(logoutScene);
    window.show();
  }

  /**
   * Desc: goes to the my account scene.
   *
   * @param: event - the ActionEvent from the button
   * @throws: Exception
   */
  static void myAccount(ActionEvent event) throws Exception {
    Parent logout = FXMLLoader.load(Navigator.class.getResource("MyAccount.fxml"));
    Scene myAccountScene = new Scene(logout);

    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    window.setScene(myAccountScene);
    window.show();
  }

  /**
   * Desc: goes to the payment scene.
   *
   * @param: event - the ActionEvent from the button
   * @throws: Exception
   */
  static void payment(ActionEvent event) throws Exception {
    Parent paymentInfo = FXMLLoader.load(Navigator.class.getResource("Payment.fxml"));
    Scene paymentScene = new Scene(paymentInfo);

    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    window.setScene(paymentScene);
    window.show();
  }

  /**
   * Desc: goes to the register scene.
   *
   * @param: event - the ActionEvent from the button
   * @throws: Exception
   */
  static void register(ActionEvent event) throws Exception {
    Parent register = FXMLLoader.load(Navigator.class.getResource("Register.fxml"));
    Scene registerScene = new Scene(register);

    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    window.setScene(registerScene);
    window.show();
  }

  /**
   * Desc: goes to the review scene.
   *
   * @param: event - the ActionEvent from the button
   * @throws: Exception
   */
  static void reviews(ActionEvent event) throws Exception {
    Parent reviews = FXMLLoader.load(Navigator.class.getResource("Reviews.fxml"));
    Scene reviewScene = new Scene(reviews);

    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    window.setScene(reviewScene);
    window.show();
  }

  /**
   * Desc: goes to the search scene.
   *
   * @param: event - the ActionEvent from the button
   * @throws: Exception
   */
  static void search(ActionEvent event) throws Exception {
    Parent search = FXMLLoader.load(Navigator.class.getResource("Search.fxml"));
    Scene searchScene = new Scene(search);

    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    window.setScene(searchScene);
    window.show();
  }

  /**
   * Desc: goes to the reservation scene.
   *
   * @param: event - the ActionEvent from the button
   * @throws: Exception
   */
  static void reservation(ActionEvent event) throws Exception {
    Parent reservations = FXMLLoader.load(Navigator.class.getResource("Reservations.fxml"));
    Scene reservation = new Scene(reservations);

    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    window.setScene(reservation);
    window.show();
  }

  /**
   * Desc: goes to the thank you scene.
   *
   * @param: event - the ActionEvent from the button
   * @throws: Exception
   */
  static void thankYouScene(ActionEvent event) throws Exception {

    Parent finalScenes = FXMLLoader.load(Navigator.class.getResource("FinalScene.fxml"));
    Scene finalScene = new Scene(finalScenes);

    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    window.setScene(finalScene);
    window.show();

  }

  /**
   * Desc: goes to the hotel owner scene.
   *
   * @param: event - the ActionEvent from the button
   * @throws: Exception
   */
  static void hotelOwner(ActionEvent event) throws Exception {

    Parent hotelOwners = FXMLLoader.load(Navigator.class.getResource("HotelOwner.fxml"));
    Scene hotelOwner = new Scene(hotelOwners);

    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    window.setScene(hotelOwner);
    window.show();
  }
}
