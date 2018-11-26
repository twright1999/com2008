/* Account.java superclass for account */
package Accounts;
public class Account {

  protected String userID;
  protected String name;
  protected String password;
  protected char permission; //A - Admin; R - registrar; T - Teacher; S - Student; Null - Account;

  public Account(String userID, String name, String password, char permission) {

    this.userID = userID;
    this.name = name;
    this.password = password; //will be stored as # in database
    this.permission = permission;

  }

  protected String getUserID() {
    return userID;
  }

  protected String getName() {
    return name;
  }

  protected String getPassword() {
    return password;
  }
  
  protected char getPermission() {
	  return permission;
  }

}
