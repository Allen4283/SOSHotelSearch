package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class RegController {

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
  private TextField txtDOB;

  @FXML
  private PasswordField txtPassword;

  @FXML
  private RadioButton regSearcherBtn;
  @FXML
  private RadioButton regOwnerBtn;

  @FXML
  private ToggleGroup registerType;

  private String passwordREGEX = "^([0-9A-Za-z@.]{1,255})$";
  private String fullNameREGEX = "[a-zA-Z]*( [a-zA-Z]*)?";
  private String userNameREGEX = "^([a-zA-Z])[a-zA-Z_-]*[\\w_-]*[\\S]$|^([a-zA-Z])"
      + "[0-9_-]*[\\S]$|^[a-zA-Z]*[\\S]$";
  private String emailREGEX = "^([\\w\\-\\.]+)@((\\[([0-9]{1,3}\\.){3}[0-9]{1,3}\\])"
      + "|(([\\w\\-]+\\.)+)([a-zA-Z]{2,4}))$";


  private Pattern passwordPattern = Pattern.compile(passwordREGEX);
  private Pattern fullNamePattern = Pattern.compile(fullNameREGEX);
  private Pattern userNamePattern = Pattern.compile(userNameREGEX);
  private Pattern emailPattern = Pattern.compile(emailREGEX);

  public void Register(ActionEvent event) throws Exception {
    createHotelSearcher(event);
  }

  private boolean validPSWDPattern(String password) {
    return passwordPattern.matcher(password).matches();
  }

  private boolean validFullNamePattern(String fullName) {
    return fullNamePattern.matcher(fullName).matches();
  }

  private boolean validUserNamePattern(String userName) {
    return userNamePattern.matcher(userName).matches();
  }

  private boolean validEmailPattern(String email) {
    return emailPattern.matcher(email).matches();
  }

  private void createHotelSearcher(ActionEvent event) throws IOException, SQLException {

    String name = txtFullName.getText();
    String user = txtUserName.getText();
    String pass = txtPassword.getText();
    String email = txtEmail.getText();
    String birthDate = txtDOB.getText();

    String searcherSql = "INSERT INTO SOS.SEARCHER VALUES(?,?,?,?,?)";
    String ownerSql = "INSERT INTO SOS.OWNER VALUES(?,?,?,?,?)";

    PreparedStatement reg = null;

    RadioButton selectedRadioButton = (RadioButton) registerType.getSelectedToggle();

    if (!validFullNamePattern(txtFullName.getText())) {
      nameStatus.setText("Invalid name input!");
      nameStatus.setTextFill(Paint.valueOf("red"));

    } else if (!validPSWDPattern(txtPassword.getText())) {
      passwordStatus.setText("Password must not contain special characters!");
      passwordStatus.setTextFill(Paint.valueOf("red"));

    } else if (!validUserNamePattern(txtUserName.getText())) {
      userStatus.setText("Username must not contain spaces!");
      userStatus.setTextFill(Paint.valueOf("red"));

    } else if (!validEmailPattern(txtEmail.getText())) {
      emailStatus.setText("Must be a valid email address!");
      emailStatus.setTextFill(Paint.valueOf("red"));

    } else if (selectedRadioButton == regSearcherBtn) {
      try {
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        Connection loginConnection = DriverManager.getConnection(LogInController.url);

        reg = loginConnection.prepareStatement(searcherSql);

        reg.setString(1, user);
        reg.setString(2, birthDate);
        reg.setString(3, pass);
        reg.setString(4, name);
        reg.setString(5, email);

      } catch (SQLException e) {
        e.printStackTrace();
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      } finally {
        assert reg != null;
        reg.executeUpdate();
        reg.close();

        Parent Dashboard = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene dashboard = new Scene(Dashboard);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(dashboard);
        window.show();
      }
    } else if (selectedRadioButton == regOwnerBtn) {
      try {
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        Connection loginConnection = DriverManager.getConnection(LogInController.url);

        reg = loginConnection.prepareStatement(ownerSql);

        reg.setString(1, name);
        reg.setString(2, birthDate);
        reg.setString(3, user);
        reg.setString(4, email);
        reg.setString(5, pass);

      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      } catch (SQLException e) {
        e.printStackTrace();
      } finally {
        assert reg != null;
        reg.executeUpdate();
        reg.close();

        Parent Dashboard = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene dashboard = new Scene(Dashboard);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(dashboard);
        window.show();
      }
    }
  }

  public void Login(ActionEvent event) throws IOException {

    Parent Dashboard = FXMLLoader.load(getClass().getResource("Login.fxml"));
    Scene dashboard = new Scene(Dashboard);

    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    window.setScene(dashboard);
    window.show();
  }
}
