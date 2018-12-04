package Accounts;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import Database.DAC;
import Utility.PeriodOfStudy;

public class Student extends Account {
	
	private int regNumber;
	private String degID;
	private String email; //First letter of forname(s) ++ surname ++ 2 unique 2 digit integer (if the same name already exsits) ++ university address
	private String tutor;
	
	/**
	 * Initial Student constructor. Email field is missing because it will be
	 * generated automatically
	 */
	public Student(int userID, String name, String password, char permission,
			int regNumber, String degID, String tutor) throws SQLException {
		super(userID, name, password, permission);
		this.regNumber = regNumber;
		this.degID = degID;
		this.tutor = tutor;
		this.email = generateEmail(name);
	}

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
	public static void main(String[] arg) throws SQLException, ParseException, java.text.ParseException {
		
		 SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		 try {
		        java.util.Date utilDate1 = format.parse("2019/01/05");
		        java.sql.Date startDate = new java.sql.Date(utilDate1.getTime());
		        java.util.Date utilDate2 = format.parse("2069/04/20");
		        java.sql.Date endDate = new java.sql.Date(utilDate2.getTime());
		        System.out.println(startDate);
		        System.out.println(endDate);
		        PeriodOfStudy period = new PeriodOfStudy("696969B", 'B', startDate, endDate, 'B', 696969);
				Student student = new Student(696969, "Mr Takeshi sixNine", "gucci", 'S', 169, "COMU01", "Lil Pumpy");
				System.out.println("student is created");
				System.out.println("Student: " + student.toString());
		 }catch (ParseException e) {
		        e.printStackTrace();
		    }
		 		
	}
	
	
}
