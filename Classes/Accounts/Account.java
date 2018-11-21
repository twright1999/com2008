/* Account.java superclass for account */

public class Account {

  private String userID;
  private String name;
  private String password;

  public Account(String userID, String name, String password) {

    this.userID = userID;
    this.name = name;
    this.password = password; //will be stored as # in database

  }

  public String getUserID() {
    return userID;
  }

  public String getName() {
    return name;
  }

  public String getPassword() {
    return password;
  }

}
