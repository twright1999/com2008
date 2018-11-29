/* Account.java superclass for account */
package Accounts;
public class Account {

  protected int userID;
  protected String name;
  protected String password;
  protected char permission; //A - Admin; R - registrar; T - Teacher; S - Student; Null - Account;
  public Account(int userID, String name, String password, char permission) {

    this.userID = userID;
    this.name = name;
    this.password = password; //will be stored as # in database
    this.permission = permission;

  }

  public int getUserID() {
    return userID;
  }

  public String getName() {
    return name;
  }

  public String getPassword() {
    return password;
  }
  
  public char getPermission() {
	  return permission;
  }
  
  public String toString() {
	  String all = "UserID: " + userID + " name: " + name + " permission: " + permission + " ";
	  return all;
  }

}
