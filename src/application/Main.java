package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

/**
 * Desc: Main class for program that handles Application functions.
 */

public class Main extends Application {

  /**
   * Desc: intializes the JavaFX GUI and sets scene to Login.
   *
   * @param: primaryStage - the stage for the GUI
   * @throws: Exception
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
    primaryStage.setResizable(false);
    primaryStage.setTitle("SOS Hotel Search");
    primaryStage.setScene(new Scene(root, 600, 432));
    primaryStage.show();
  }

  /**
   * Desc: launches the program.
   *
   * @param: args - array of Strings
   */
  public static void main(String[] args) {
    launch(args);
  }
}
