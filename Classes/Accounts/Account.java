/* Account.java superclass for account */
package Accounts;
public class Account {

  protected String userID;
  protected String name;
  protected String password;
  protected char permission; //A - Admin; R - registrar; T - Teacher; S - Student; Null - Account;

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
