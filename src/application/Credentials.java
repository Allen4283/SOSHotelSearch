package application;

import java.sql.*;
import java.util.regex.Pattern;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;


/**
 * Desc: verifies the validity of the information the user
 * uses to create or edit their account.
 */
abstract class Credentials {

  private static final String passwordREGEX = "^([0-9A-Za-z@.]{1,255})$";
  private static final String fullNameREGEX = "[a-zA-Z]*( [a-zA-Z]*)?";
  private static final String userNameREGEX = "^([a-zA-Z])[a-zA-Z_-]*[\\w_-]*[\\S]$|^([a-zA-Z])"
          + "[0-9_-]*[\\S]$|^[a-zA-Z]*[\\S]$";
  private static final String emailREGEX = "^\\w+[\\w-.]*@\\w+((-\\w+)|(\\w*))\\.[a-z]{2,3}$";
  private static final String dobREGEX = "((?:0[1-9])|(?:1[0-2]))/((?:0[0-9])|(?:[1-2][0-9])|"
          + "(?:3[0-1]))/(\\d{4})";

  private static final String searcherSql = "INSERT INTO SOS.SEARCHER(USERNAME, "
      + "DOB, PASSWORD, NAME, EMAIL) VALUES(?,?,?,?,?)";
  private static final String ownerSql = "INSERT INTO SOS.OWNER VALUES(?,?,?,?,?)";
  private static final String updateSQL = "UPDATE SOS.SEARCHER SET NAME = ?, PASSWORD = ?, "
          + "EMAIL = ?, DOB = ? WHERE USERNAME = ?";

  private static final String url = "jdbc:derby:lib/SOSHotelAccountDB";
  private static final String driver = "org.apache.derby.jdbc.EmbeddedDriver";

  private static String clientUsername;
  private static String clientPassword;
  private static boolean isSearcher = false;

  private final Pattern passwordPattern = Pattern.compile(passwordREGEX);
  private final Pattern fullNamePattern = Pattern.compile(fullNameREGEX);
  private final Pattern userNamePattern = Pattern.compile(userNameREGEX);
  private final Pattern emailPattern = Pattern.compile(emailREGEX);
  private final Pattern dobPattern = Pattern.compile(dobREGEX);

  /**
   * Desc: checks validity of the password.
   *
   * @param: password - the password being used for the account
   * @return: true if password is invalid
   */
  boolean validPasswordPattern(String password) {
    return !passwordPattern.matcher(password).matches();
  }

  /**
   * Desc: checks validity of the full name.
   *
   * @param: fullName - the full name being used for the account
   * @return: true if full name is invalid
   */
  boolean validFullNamePattern(String fullName) {
    return !fullNamePattern.matcher(fullName).matches();
  }

  /**
   * Desc: checks validity of the username.
   *
   * @param: username - the username being for the account
   * @return: true if username is invalid
   */
  boolean validUserNamePattern(String userName) {
    return userNamePattern.matcher(userName).matches();
  }

  /**
   * Desc: checks validity of the email.
   *
   * @param: email - the email being used for the account
   * @return: true if email is invalid
   */
  boolean validEmailPattern(String email) {
    return !emailPattern.matcher(email).matches();
  }

  /**
   * Desc: checks validity of the date of birth.
   *
   * @param: dob - the date of birth used for the account
   * @return: true if DOB is invalid
   */
  boolean validDobPattern(String dob) {
    return !dobPattern.matcher(dob).matches();
  }

  /**
   * Desc: registers an account by using the parameters for account information.
   *
   * @param: user - username
   * @param: birthDate - date of birth
   * @param: pass - password
   * @param: name - full name
   * @param: email - email
   * @param: typeSQL
   * @throws: ClassNotFoundException
   * @throws: SQLException
   */
  void registerClient(String user, String birthDate, String pass, String name, String email,
                      String typeSql) throws ClassNotFoundException, SQLException {
    Class.forName(driver);
    Connection loginConnection = DriverManager.getConnection(url);
    PreparedStatement registerStatement = loginConnection.prepareStatement(typeSql);

    registerStatement.setString(1, user);
    registerStatement.setString(2, birthDate);
    registerStatement.setString(3, pass);
    registerStatement.setString(4, name);
    registerStatement.setString(5, email);

    registerStatement.executeUpdate();
    registerStatement.close();
    loginConnection.close();
  }

  /**
   * Desc: updates an account's information.
   *
   * @param: newName - updated full name
   * @param: newPassword - updated password
   * @param: newWEmail - updated email
   * @param: newBirthDate - updated DOB
   * @param: userName - updated username
   * @param: typeSQL
   * @param: updateStatus
   * @throws: ClassNotFoundException
   * @throws: SQLException
   */
  void update(String newName, String newPassword, String newEmail, String newBirthDate,
              String userName, String typeSql, Label updateStatus)
          throws ClassNotFoundException, SQLException {
    Class.forName(driver);
    Connection loginConnection = DriverManager.getConnection(url);
    PreparedStatement update = loginConnection.prepareStatement(typeSql);

    update.setString(1, newName);
    update.setString(2, newPassword);
    update.setString(3, newEmail);
    update.setString(4, newBirthDate);
    update.setString(5, userName);
    update.executeUpdate();
    updateStatus.setTextFill(Paint.valueOf("green"));
    update.close();
    loginConnection.close();
  }

  /**
   * Desc: gets url to database.
   *
   * @return: url to access database
   */
  public static String getUrl() {
    return url;
  }

  /**
   * Desc: gets the client's username.
   *
   * @return: clientUsername - the username of the client
   */
  public static String getClientUsername() {
    return clientUsername;
  }

  /**
   * Desc: sets the client's username.
   *
   * @param: thisClientUsername - the username of this client
   */
  public static void setClientUsername(String thisClientUsername) {
    clientUsername = thisClientUsername;
  }

  /**
   * Desc: gets the client's password.
   *
   * @return: clientPassword - the client's password
   */
  public static String getClientPassword() {
    return clientPassword;
  }

  /**
   * Desc: sets the client's password.
   *
   * @param: thisClientPassword - the password of the client
   */
  public static void setClientPassword(String thisClientPassword) {
    clientPassword = thisClientPassword;
  }

  /**
   * Desc: gets whether the user is a searcher.
   *
   * @return: isSearcher - true if searcher
   */
  public static boolean getIsSearcher() {
    return isSearcher;
  }

  /**
   * Desc: sets user as a searcher.
   *
   * @param: thisIsSearcher - true if searcher
   */
  public static void setIsSearcher(boolean thisIsSearcher) {
    isSearcher = thisIsSearcher;
  }

  /**
   * Desc: gets driver.
   *
   * @return: driver
   */
  public static String getDriver() {
    return driver;
  }

  /**
   * Desc: gets updated SQL.
   *
   * @return: updateSQL - the updated SQL
   */
  public static String getUpdateSql() {
    return updateSQL;
  }

  /**
   * Desc: gets searcher SQL.
   *
   * @return: searcherSql - the hotel searcher SQL data
   */
  public static String getSearcherSql() {
    return searcherSql;
  }

  /**
   * Desc: gets owner SQL.
   *
   * @return: ownerSql - the hotel owner SQL data
   */
  public static String getOwnerSql() {
    return ownerSql;
  }
}
