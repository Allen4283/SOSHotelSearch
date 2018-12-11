package application;

import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Paint;

/**
 * Desc: controller for the register screen.
 */

public class RegController extends Credentials {

  @FXML
  private Label nameStatus;
  @FXML
  private Label userStatus;
  @FXML
  private Label passwordStatus;
  @FXML
  private Label emailStatus;
  @FXML
  private Label dobStatus;

  @FXML
  private TextField txtFullName;
  @FXML
  private TextField txtUserName;
  @FXML
  private TextField txtEmail;
  @FXML
  private TextField txtDob;

  @FXML
  private PasswordField txtPassword;

  @FXML
  private RadioButton regSearcherBtn;
  @FXML
  private RadioButton regOwnerBtn;

  @FXML
  private ToggleGroup registerType;

  /**
   * Desc: registers the user.
   *
   * @param: event - the ActionEvent from the button.
   * @throws: Exception
   */
  public void register(ActionEvent event) throws Exception {
    createHotelSearcher(event);
  }

  /**
   * Desc: creates a hotel searcher. Contains regex functionality to
   * ensure the user inputs proper naming conventions
   *
   * @param: event - the ActionEvent from the button
   * @throws: Exception
   */
  private void createHotelSearcher(ActionEvent event) throws Exception {
    //creates account information
    String name = txtFullName.getText();
    String user = txtUserName.getText();
    String pass = txtPassword.getText();
    String email = txtEmail.getText();
    String birthDate = txtDob.getText();

    //recognizes which radio button is selected (hotel searcher or hotel owner)
    RadioButton selectedRadioButton = (RadioButton) registerType.getSelectedToggle();

    //checks if full name is valid
    if (validFullNamePattern(txtFullName.getText())) {
      nameStatus.setText("Invalid name input!");
      nameStatus.setTextFill(Paint.valueOf("red"));

      //checks if password is valid
    } else if (validPasswordPattern(txtPassword.getText())) {
      passwordStatus.setText("Password must not contain special characters!");
      passwordStatus.setTextFill(Paint.valueOf("red"));

      //checks if username is valid
    } else if (!validUserNamePattern(txtUserName.getText())) {
      userStatus.setText("Username must not contain spaces!");
      userStatus.setTextFill(Paint.valueOf("red"));

      //checks if email is valid
    } else if (validEmailPattern(txtEmail.getText())) {
      emailStatus.setText("Must be a valid email address!");
      emailStatus.setTextFill(Paint.valueOf("red"));

      //checks if date of birth is valid
    } else if (validDobPattern(txtDob.getText())) {
      dobStatus.setText("DOB Pattern: MM/DD/YYYY");
      dobStatus.setTextFill(Paint.valueOf("red"));

      //checks if hotel searcher is selected
    } else if (selectedRadioButton == regSearcherBtn) {
      try {
        //create hotel searcher account
        registerClient(user, birthDate, pass, name, email, Credentials.getSearcherSql());
        login(event);

      } catch (SQLException | ClassNotFoundException e) {
        e.printStackTrace();
      }
      //checks if hotel owner account is selected
    } else if (selectedRadioButton == regOwnerBtn) {
      try {
        //creates hotel owner account
        registerClient(user, birthDate, pass, name, email, Credentials.getOwnerSql());
        login(event);

      } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Desc: goes to the login scene.
   *
   * @param: event - the ActionEvent from the button
   * @throws: Exception
   */
  @FXML
  private void login(ActionEvent event) throws Exception {
    Navigator.logout(event);
  }
}
