package Accounts;
import Database.DACAdmin;

import java.sql.*;
public class Admin extends Account {

	public Admin(int userID, String name, String password, char permission) {
		super(userID, name, password, permission);

	}

	private void addAcount(String accountName, String accountPassword, char accountPermission) throws SQLException {
		DACAdmin.addAccount(accountName, accountPassword, accountPermission);
		return;	
	}

	private void removeAccount(int accountUserID) throws SQLException {
		DACAdmin.removeAccount(accountUserID);
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

	private void addDegree(String degID, String depName, char level, String depID, boolean placement) throws SQLException {
		DACAdmin.addDegree(depID, depName, level, depID, placement);
		return;	
	}

	private void removeDegree(String degID) throws SQLException {
		DACAdmin.removeDegree(degID);
		return;
	}

	private void addModule(String modID, String modName, int credits, String taught, boolean obligatory, char level,  String degID) throws SQLException {
		DACAdmin.addModule(modID, modName, credits, taught, obligatory, level, degID);
		return;
	}

	private void removeModule(String modID) throws SQLException {
		DACAdmin.removeModule(modID);
		return;
	}

	public static void main(String[] arg) throws SQLException {
		
	}

}
