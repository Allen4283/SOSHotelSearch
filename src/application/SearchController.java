package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


/**
 * Desc: controller class for the search scene.
 */

public class SearchController {

  // intended to set map location from previous screen on startup. Flagged false
  private boolean isNewInstance = true;
  private int offset = 0;
  private boolean isLowToHigh = false;
  private ArrayList<Hotel> hotels;
  private static Random rand = new Random();
  private ArrayList<Image> images = new ArrayList<>();

  @FXML
  private BorderPane mapPane;

  @FXML
  private VBox mapBox;

  @FXML
  private TextField searchBar;

  @FXML
  private AnchorPane hotelList;

  //Side panel buttons

  /**
   * Desc: goes to dashboard scene.
   *
   * @throws Exception is thrown when dashboard scene does not exist.
   * @param: event - ActionEvent from the button
   */
  public void dashboard(ActionEvent event) throws Exception {
    Navigator.dashboard(event);
  }

  /**
   * Desc: goes to myAccount scene.
   *
   * @throws Exception is thrown when myAccount scene does not exist.
   * @param: event - ActionEvent from the button
   */
  public void myAccount(ActionEvent event) throws Exception {
    Navigator.myAccount(event);
  }

  /**
   * Desc: goes to logout scene.
   *
   * @throws Exception is thrown when logout scene does not exist.
   * @param: event - ActionEvent from the button
   */
  public void logout(ActionEvent event) throws Exception {
    Navigator.logout(event);
  }

  /**
   * Desc: initialize the scene by creating a map manager and creating images. Checks for new
   * instance and sets map location accordingly.
   */
  public void initialize() {
    final MapManager mapManager = new MapManager();

    images.add(new Image("application/hotelthumbs/La-Quinta.jpg"));
    images.add(new Image("application/hotelthumbs/holiday-inn-the-colony-4629618286-4x3.jpeg"));
    images.add(new Image("application/hotelthumbs/hotel1.jpg"));
    images.add(new Image("application/hotelthumbs/hotel2.jpg"));
    images.add(new Image("application/hotelthumbs/Hyatt-Place-St-George-Convention-Center-P004-"
        + "Exterior.adapt.16x9.1920.1080.jpg"));
    images.add(new Image("application/hotelthumbs/T1114MARRIOTTTUCSON.jpg"));

    // get list of hotels returned from mapManager
    hotels = mapManager.getHotelsToDisplay();

    if (isNewInstance) {
      mapManager.setAddress(DashController.getLocation());
    } else {
      mapManager.setAddress(searchBar.getText());
    }

    isNewInstance = false;
    mapManager.createMap();

    if (!mapManager.getErrorStatus()) {
      mapPane.getChildren().clear();
      mapPane = new BorderPane(mapManager.getMapView());
    }

    sortList();
  }

  /**
   * Desc: sorts the hotel list by price. Every pass the list must be redrawn, so creates all
   * objects comprising each entry in the list using data pulled from hotelsToDisplay in
   * mapManager.
   */
  private void sortList() {
    //clear any children on the list
    hotelList.getChildren().removeAll();

    // sort the list based on price (either high to low or low to high)
    if (isLowToHigh) {
      Collections.sort(hotels);
    } else {
      hotels.sort(Collections.reverseOrder());
    }

    int hotelPaneOffset = 0; // Y offset that each successive hotel pane will need to be at

    for (Hotel hotel : hotels) {
      // create nodes for each hotel preview
      Pane hotelPane = new Pane();

      Image image = images.get(rand.nextInt(images.size()));
      ImageView hotelImage = new ImageView(image);

      final Label hotelName = new Label();
      final Label hotelStars = new Label();
      final Label hotelPrice = new Label();
      final Label hotelRating = new Label();
      final Button infoButton = new Button();

      // set the height of each pane to be equal to the thumbnail in it
      hotelPane.setPrefHeight(hotelImage.getImage().getHeight());

      // set fields of preview window to those from each hotel object
      Text name = new Text(hotel.getName());
      Text stars = new Text(hotel.getStars() + "-Star Hotel");
      final Text rating = new Text("User rating: 4/5");
      Text price = new Text("$" + hotel.getPrice());

      price.setFill(Paint.valueOf("#1b9dc1"));

      hotelName.setText(name.getText());
      hotelStars.setText(stars.getText());
      hotelRating.setText(rating.getText());
      hotelPrice.setText(price.getText());

      // configure layout of elements on hotel list
      double baseXOffset = hotelImage.getImage().getWidth() + 10;
      double baseYOffset = (hotelImage.getImage().getHeight() + 5)
          - hotelImage.getImage().getHeight();
      final int yPadding = 17;
      final int hotelPriceOffset = 100;

      Font hotelNameFont = new Font(14);
      final Font hotelStarsFont = new Font(12);
      final Font hotelRatingFont = new Font(16);
      final Font hotelPriceFont = new Font(30);

      hotelName.setLayoutX(baseXOffset);
      hotelName.setLayoutY(baseYOffset);
      hotelName.setFont(hotelNameFont);
      hotelName.setMaxWidth(450);

      hotelStars.setLayoutX(baseXOffset);
      hotelStars.setLayoutY(hotelName.getLayoutY() + yPadding);
      hotelStars.setFont(hotelStarsFont);

      hotelRating.setLayoutX(baseXOffset);
      hotelRating.setLayoutY(hotelStars.getLayoutY() + yPadding);
      hotelRating.setFont(hotelRatingFont);

      hotelPrice.setLayoutX(baseXOffset + hotelPriceOffset);
      hotelPrice.setLayoutY(hotelRating.getLayoutY() + yPadding);
      hotelPrice.setFont(hotelPriceFont);

      infoButton.setOpacity(0); // make button invisible
      infoButton.setLayoutX(hotelImage.getLayoutX());
      infoButton.setLayoutY(hotelImage.getLayoutY());
      infoButton.setPrefWidth(hotelImage.getImage().getWidth() + baseXOffset
          + hotelName.getMaxWidth() + 10);
      infoButton.setPrefHeight(hotelImage.getImage().getHeight());

      infoButton.addEventHandler(ActionEvent.ACTION, event -> {
        try {
          Navigator.hotelInfo(hotel, event);
        } catch (Exception e) {
          e.printStackTrace();
        }
      });

      // add each element to the hotel preview pane one after the other
      hotelPane.getChildren().addAll(hotelImage, hotelName, hotelStars, hotelPrice, infoButton);
      hotelPane.setLayoutY(hotelPane.getPrefHeight() * hotelPaneOffset);
      hotelPane.setPrefWidth(infoButton.getPrefWidth());

      // make every other pane have a light background
      if (hotelPaneOffset % 2 == 0) {
        hotelPane.setBackground(new Background(new BackgroundFill(Paint.valueOf("#78c9f4"),
            null, null)));
      } else {
        hotelPane.setBackground(new Background(new BackgroundFill(Paint.valueOf("#FFFFFF"),
            null, null)));
      }
      hotelList.getChildren().add(hotelPane);

      // increment hotelPaneOffset so that the next hotel preview pane is situated below the last
      hotelPaneOffset++;
    }

    // for some reason the prefHeight has to be set manually and also shifted up every time the map
    // is redrawn. Unsure of reason. Please ignore magic numbers.
    mapPane.setPrefHeight(900.0 + offset);
    offset += 600;

    mapBox.getChildren().add(mapPane);
  }

  /**
   * Desc: sets lowToHigh to true and calls sortList.
   *
   * @param: lowToHigh - boolean controlling sorting scheme for list
   */
  public void setLowToHigh() {
    isLowToHigh = true;
    sortList();
  }

  /**
   * Desc: sets lowToHigh to false and calls sortList.
   *
   * @param: lowToHigh - boolean controlling sorting scheme for list
   */
  public void setHighToLow() {
    isLowToHigh = false;
    sortList();
  }
}
