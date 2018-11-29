/* Registrar.java subclass for account */
package Accounts;

import java.sql.SQLException;

import Database.DACRegistrar;

public class Registrar extends Account {

	public Registrar(int userID, String name, String password, char permission) {
		super(userID, name, password, permission);
	}
	
	// need database stuff working first 
	
	private void addStudent(int regNumber, String email, String tutor, int studentUserID) throws SQLException {
		DACRegistrar.addStudent(regNumber, email, tutor, studentUserID);
		return;	
	}
	
	private void removeStudent(int regNumber) throws SQLException {
		DACRegistrar.removeStudent(regNumber);
		return;	
	}
	
	private void addModule(int regNumber, String modID) throws SQLException {
		DACRegistrar.addModule(regNumber, modID);
		return;	
	}
	
	private void dropModule(int regNumber, String modID) throws SQLException {
		DACRegistrar.dropModule(regNumber, modID);
	}
	
	private void checkRegistered(int userID) throws SQLException {
		DACRegistrar.checkRegistered(userID);
	}
	
	private void checkCredits(int regNumber, int periodID) throws SQLException {
		DACRegistrar.checkCredits(regNumber, periodID);
	}
	
	
}