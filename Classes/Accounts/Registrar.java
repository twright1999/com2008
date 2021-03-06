/* Registrar.java subclass for account */
package Accounts;

import java.sql.SQLException;

import Database.DACRegistrar;

public class Registrar extends Account {

	public Registrar(int userID, String name, String password, char permission) {
		super(userID, name, password, permission);
	}
	
	// need database stuff working first 
	
	private void addStudent(String email, String tutor, String degID, int studentUserID) throws SQLException {
		DACRegistrar.addStudent(tutor, degID, studentUserID);
		return;	
	}
	
	private void removeStudent(int regNumber) throws SQLException {
		DACRegistrar.removeStudent(regNumber);
		return;	
	}
	
	private void addStudentModule(int regNumber, String modID) throws SQLException {
		DACRegistrar.addStudentModule(regNumber, modID);
		return;	
	}
	
	private void dropModule(int regNumber, String modID) throws SQLException {
		DACRegistrar.dropModule(regNumber, modID);
	}
	
	public void registerStudent(String label, String startDate, String endDate, String level, int regNumber) throws SQLException {
		DACRegistrar.registerStudent(label, startDate, endDate, level, regNumber);
		
	}
	
	private void checkRegistered(int regNumber) throws SQLException {
		DACRegistrar.checkRegistered(regNumber);
	}
	
	private void checkCredits(int regNumber, String periodID) throws SQLException {
		DACRegistrar.checkCredits(regNumber, periodID);
	}
	
	
}