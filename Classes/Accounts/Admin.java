package Accounts;
import Database.DACAdmin;

import java.sql.*;
public class Admin extends Account {

	public Admin(int userID, String name, String password, char permission) {
		super(userID, name, password, permission);

	}

	private void addAcount(int accountUserID, String accountName, String accountPassword, char accountPermission) throws SQLException {
		DACAdmin.addAccount(accountUserID, accountName, accountPassword, accountPermission);
		return;	
	}

	private void removeAccount(int accountUserID) throws SQLException {
		DACAdmin.removeAccount(accountUserID);
		return;
	}
	

	private void setPermission(int accountUserID, char accountPermission) throws SQLException {
		DACAdmin.setPermission(accountUserID, accountPermission);
		return;
	}

	private void setUserID(int accountOldUserID, int accountNewUserID) throws SQLException {
		DACAdmin.setUserID(accountOldUserID, accountNewUserID);
		return;
	}

	private void setPassword(int accountUserID, String newAccountPassword) throws SQLException {
		DACAdmin.setPassword(accountUserID, newAccountPassword);
		return;
	}

	private void setAccountName(int accountUserID, String newAccountName) throws SQLException {
		DACAdmin.setUsername(accountUserID, newAccountName);
		return;
	}

	private void addDepartment(String depID, String depName) throws SQLException {
		DACAdmin.addDepartment(depID, depName);
		return;	
	}

	private void removeDepartment(String depID) throws SQLException {
		DACAdmin.removeDepartment(depID);
		return;	
	}

	private void addDegree(String degID, String depName, String level, String depID) throws SQLException {
		DACAdmin.addDegree(depID, depName, level, depID);
		return;	
	}

	private void removeDegree(String degID) throws SQLException {
		DACAdmin.removeDegree(degID);
		return;
	}

	private void addModule(String modID, String modName, int credits, String taught, String obligatory, String degCode) throws SQLException {
		DACAdmin.addModule(modID, modName, credits, taught, obligatory, degCode);
		return;
	}

	private void removeModule(String modID) throws SQLException {
		DACAdmin.removeModule(modID);
		return;
	}

	public static void main(String[] arg) throws SQLException {

	}

}
