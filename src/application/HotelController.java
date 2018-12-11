package application;

import java.sql.*;
import java.util.ArrayList;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;


/**
 * Desc: displays information for specific hotels, and allows the user
 * to view hotels, go to reviews, and go to payment.
 */

@SuppressWarnings("JavaDoc")
public class HotelController {

  @FXML
  private ImageView hotelPhotos;    //photos

  @FXML
  private Text hotelName;           //hotel name
  @FXML
  private Text hotelLocation;       //location
  @FXML
  private Text hotelStars;          //stars
  @FXML
  private Text hotelPrice;          //price


  private int numImages = 2;        //there are 3 hotel images with indexes 0, 1, and 2
  private int imageArrayIndex = 0;  //initializes image index to 0
  private ArrayList<Image> images = new ArrayList<>(numImages); //array list for images
  private static Hotel hotel;
  private static Reservation reservation;
  private int id;
  private String getId = "SELECT SOS.SEARCHER.USER_ID FROM SOS.SEARCHER"
      + " WHERE SOS.SEARCHER.USERNAME ='"
      + LogInController.getClientUsername() + "'";


  /**
   * desc: loads the name, location, stars, price, and images for the hotel.
   */
  public void initialize() {
    //adds 3 images for the hotel
    images.add(new Image("application/hotelpics/holiday-inn-the-colony-4629618286-16x5.jpg"));
    images.add(new Image("application/hotelpics/room.jpg"));
    images.add(new Image("application/hotelpics/holiday-inn-the-colony-4549822872-4x3.jpg"));

    hotelName.setText(hotel.getName());                                 //sets name
    hotelLocation.setText("Location: " + hotel.getCity()
        + ", " + hotel.getCountryName());  //sets location
    hotelStars.setText("This is a " + hotel.getStars() + " star hotel.");  //sets stars
    hotelPrice.setText("Price : $" + hotel.getPrice() + "/night");          //sets price

  }

  /**
   * Desc: goes to the dashboard scene.
   *
   * @param event - the ActionEvent for the button
   * @throws Exception is thrown when there isn't a dashboard scene.
   */
  public void dashboardButton(ActionEvent event) throws Exception {
    Navigator.dashboard(event);
  }

  /**
   * Desc: goes to the login scene.
   *
   * @param event - the ActionEvent for the button
   * @throws Exception is thrown when there isn't a logout scene.
   */
  public void logout(ActionEvent event) throws Exception {
    Navigator.logout(event);
  }

  /**
   * Desc: goes to the my account scene.
   *
   * @param event - the ActionEvent for the button
   * @throws Exception is thrown when there isn't a myAccount scene.
   */
  public void myAccount(ActionEvent event) throws Exception {
    Navigator.myAccount(event);
  }

  /**
   * Desc: makes a reservation and goes to the payment scene.
   *
   * @param event - the ActionEvent for the button
   * @throws Exception is thrown when there is a SQLException.
   */
  public void bookItButton(ActionEvent event) throws Exception {
    try (Connection conn = DriverManager.getConnection(Credentials.getUrl());
        Statement stmt = conn.createStatement();
        ResultSet resultSet = stmt.executeQuery(getId)) {
      resultSet.next();
      id = resultSet.getInt(1);
      PreparedStatement insert = conn.prepareStatement("INSERT INTO "
          + "SOS.RESERVATIONS (USER_ID) VALUES (" + id + ")");
      insert.executeUpdate();
      insert.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    reservation = new Reservation(hotel, DashController.getUserCheckInDate(),
        DashController.getUserCheckOutDate(), DashController.getNumOfRooms());

    Navigator.payment(event);     //go to payment scene

    String checkInDate = reservation.getCheckInDate().toString();
    String checkOutDate = reservation.getCheckOutDate().toString();

    String bookedHotelName = reservation.getHotel().getCity();
    int bookedHotelPrice = reservation.getFinalCost();
    double rating = reservation.getHotel().getPrice();
    String location = DashController.getLocation();
    int rooms = DashController.getNumOfRooms();
    String hotelId = hotel.getHotelId();

    String insertReservationsql = "INSERT INTO SOS.RESERVATIONS "
        + "(CHECKIN, CHECKOUT, HOTEL_ID, USER_ID) VALUES"
        + "('" + checkInDate + "','" + checkOutDate
        + "'," + hotelId + "," + id + ")";


    String insertHotelsql = "INSERT INTO SOS.HOTEL"
        + " (ID, NAME, PRICE, RATING, LOCATION, ROOMS) VALUES"
        + "(" + hotelId + ",'" + bookedHotelName + "',"
        + bookedHotelPrice + "," + rating + ",'"
        + location + "'," + rooms + ")";
    try {
      Connection connection = DriverManager.getConnection(Credentials.getUrl());
      PreparedStatement insertReservation = connection.prepareStatement(insertReservationsql);
      PreparedStatement insertHotel = connection.prepareStatement(insertHotelsql);
      insertHotel.executeUpdate();
      insertReservation.executeUpdate();
      insertHotel.close();
      insertReservation.close();
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Desc: goes to the reviews scene.
   *
   * @param event - the ActionEvent for the button
   * @throws Exception is thrown when there isn't a review scene.
   */
  public void goToReviews(ActionEvent event) throws Exception {
    Navigator.reviews(event);
  }

  /**
   * Desc: allows user to view the next hotel image.
   */
  public void nextImage() {
    try {
      imageArrayIndex++;                        //increase image index by 1
      hotelPhotos.setImage(images.get(imageArrayIndex));  //set image according to the index
    } catch (IndexOutOfBoundsException e) {
      hotelPhotos.setImage(images.get(0));      //if user increases past index 2, wraps to index 0
      imageArrayIndex = 0;
    }
  }

  /**
   * Desc: allows user to view the previous hotel image.
   */
  public void previousImage() {
    try {
      imageArrayIndex--;                                  //decrease image index by 1
      hotelPhotos.setImage(images.get(imageArrayIndex));  //set image according to the index
    } catch (IndexOutOfBoundsException e) {
      //if user decreases past index 0, wraps to index 2
      hotelPhotos.setImage(images.get(images.size() - 1));
      imageArrayIndex = images.size() - 1;
    }
  }

  /**
   * Desc: sets the hotel to the current hotel being viewed.
   *
   * @param thisHotel - the current hotel being viewed
   */
  public static void setHotel(Hotel thisHotel) {
    hotel = thisHotel;
  }

  /**
   * Desc: gets the current hotel.
   *
   * @return: hotel - the hotel being viewed
   */
  public static Hotel getHotel() {
    return hotel;
  }

  /**
   * Desc: sets the reservation to the current one.
   *
   * @param thisReservation - the reservation being made
   */
  public static void setReservation(Reservation thisReservation) {
    reservation = thisReservation;
  }

  /**
   * Desc: gets the reservation.
   *
   * @return: reservation - the current reservation
   */
  public static Reservation getReservation() {
    return reservation;
  }
}
