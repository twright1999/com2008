package Accounts;
import java.sql.Connection;

import Database.DACAdmin;

import java.sql.*;
public class Admin extends Account {
	
	private DACAdmin admin;

	public Admin(String userID, String name, String password, char permission) {
		super(userID, name, password, permission);

	}

	private void addAcount(Account account) {
		admin.addAccount;
		return;	
	}

	private void removeAccount(String userID) {
		DACAdmin.removeAccount(userID);
		return;
	}
	

	private void setPrivilige(Account account) {
		return DACAdmin.setPrivilige;
	}

	private void setUserID(String userId) {
		return DACAdmin.setUserID;
	}

	private void setUserPassword(String pass) {
		return DACAdmin.setUserPassword;
	}

	private void setUserName(String name) {
		return DACAdmin.setUserName;
	}

	private void addDepartment(Department department) {
		return DACAdmin.addDepartment;
	}

	private void removeDepartment(Department department) {
		return DACAdmin.removeDepartment;
	}

	private void addDegree(Degree degree) {
		return DACAdmin.addDegree;
	}

	private void removeDegree(Degree degree) {
		return DACAdmin.removeDegree;
	}

	private void addModule(Module module) {
		return DACAdmin.addModule;
	}

	private void removeModule(Module module) {
		return DACAdmin.removeModule;
	}

	public static void main(String[] arg) throws SQLException {
		Admin admin = new Admin("A01", "Rokas", "gg");
		admin.addAcount("Pokas", "pp");

	}

}
