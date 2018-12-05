package Accounts;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import Database.DAC;
import Utility.PeriodOfStudy;

public class Student extends Account {
	
	private int regNumber;
	private String degID;
	private String email; //First letter of forname(s) ++ surname ++ 2 unique 2 digit integer (if the same name already exsits) ++ university address
	private String tutor;
	
	/**
	 * Constructor used to print all the Students. 
	 */
	public Student(int userID, String name, String password, char permission,
			int regNumber, String degID,  String tutor, String email) {
		super(userID, name, password, permission);
		this.regNumber = regNumber;
		this.degID = degID;
		this.tutor = tutor;
		this.email = email;
	}
	//get methods
	public int getRegNumber() { return regNumber ; }
	public String getDegID() { return degID ; }
	public String getTutor() { return tutor ; }
	public String getEmail() { return email ; }
	/**
	 * Generates an email 
	 * @param sname student's name
	 * @return String (email)
	 * @throws SQLException
	 */
	public static String generateEmail(String sname) throws SQLException {
		return DAC.generateEmail(sname);
	}
	
	public String toString() {
		String all = super.toString() + " regNumber: " + regNumber +
				" degID: " + degID + " tutor: " + tutor + " email: " + email;
		return all;
	}
	
	//for testing
	public static void main(String[] arg) throws SQLException {
			
	}
	
}
