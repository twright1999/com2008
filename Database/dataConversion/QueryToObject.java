package dataConversion;
import Accounts.*;
import Database.DAC;
import Utility.*;
import java.sql.*;
/**
 * 
 * @author Rokas
 * For converting query results to an Object, so that it could be used
 * in GUI display.
 * Should be static?
 */
public final class QueryToObject {
	/**
	 * used to convert SQL row in Account table to Account object (if such exists)
	 * @param res - sql result query.
	 * @return account
	 * @throws SQLException - displays a message in GUI that such account does not exist
	 */
	public static Account rowToAccount(ResultSet res) throws SQLException {
		Account account;
		try {
			res.next();
			int userID = res.getInt("userID"); 
			String name = res.getString("name");
			String password = res.getString("password");
			char permission = res.getString("permission").charAt(0);
			account = new Account (userID, name, password, permission);
			System.out.println(">>account is created");
			return account;
		}
		catch (SQLException ex) {
			//output in GUI that such account does not exists given the query
			System.out.println("rowToAcc exception: " + ex.toString());
		}
		//returns null if such account does not exist.
		return null;
		
	}
	
	public static Student rowToStudent(ResultSet res) {
		Student student;
		try {
			//get values from the main account table
			
			Account acc = rowToAccount(res);
			//then add the missing student instance variables
			//res.next();
			
			int regNumber = res.getInt("regNumber");
			System.out.println("<>Student regNumber: " + regNumber);
			String email = res.getString("email");
			String tutor = res.getString("tutor");
			//Goes to DAC, then to rowToDegree for row -> Degree conversion
			Degree degree = DAC.getStudentDegree(acc.getUserID());
			//Pull periodOfStudy from PeriodOfStudy table
	        PeriodOfStudy periodOfStudy = DAC.getStudentPeriodOfStudy(regNumber);
	        student = new Student(acc.getUserID(), acc.getName(), acc.getPassword(),
	        		acc.getPermission(), degree, email, tutor, periodOfStudy);
	        System.out.println(">>student is created");
			return student;
		}
		catch (SQLException ex) {
			System.out.println("rowToStudent exception: " + ex.toString());
			//System.out.println("sql exception in rowToStudent");
		}
		//returns null if the student was not found 
		return null;
	}
	
	public static Degree rowToDegree(ResultSet res) throws SQLException  {
		Degree degree;
		try {
			res.next();
			String degID = res.getString("degID");
			String name = res.getString("name");
			char level = res.getString("level").charAt(0);
			String depID = res.getString("depID");
			degree = new Degree(degID, name, level, depID);
			return degree;
		}
		catch (SQLException ex) {
			System.out.println("SQLexception in rowToDegree");
			degree = null;
		}
		//Returns null if Degree was not found
		return null;
		
	}
	
	public static PeriodOfStudy rowToPeriod(ResultSet res) {
		PeriodOfStudy period;
		try {
			res.next();
			int periodID = res.getInt("periodID");
			char label = res.getString("label").charAt(0);
			Date startDate = res.getDate("startDate");
			Date endDate = res.getDate("endDate");
			char level = res.getString("level").charAt(0);
			int regNumber = res.getInt("regNumber");
			period = new PeriodOfStudy(periodID, label, startDate, endDate,
						level, regNumber);
			return period;
			
		}
		catch (SQLException ex) {
			//output in GUI that such account does not exists given the query
			System.out.println("SQLexception in rowToPeriod: " + ex.toString());
		}
		
		return null;
		
	}
	
	public static void main(String[] arg) throws SQLException {
		
	}
}
