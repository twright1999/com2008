/* Registrar.java subclass for account */
package Accounts;

import java.sql.SQLException;

import Database.DACRegistrar;

public class Registrar extends Account {

	public Registrar(int userID, String name, String password, char permission) {
		super(userID, name, password, permission);
	}
	
	// need database stuff working first 
	
	private void addStudent(String regNumber, String email, String tutor, int studentUserID) throws SQLException {
		DACRegistrar.addStudent(regNumber, email, tutor, studentUserID);
		return;	
	}
	
	private void removeStudent(int studentUserID) throws SQLException {
		DACRegistrar.removeStudent(studentUserID);
		return;	
	}
	
	private void addModule(String regNumber, String modID) throws SQLException {
		DACRegistrar.addModule(regNumber, modID);
		return;	
	}
	
	private void dropModule(String regNumber, String modID) throws SQLException {
		DACRegistrar.dropModule(regNumber, modID);
	}
	
}