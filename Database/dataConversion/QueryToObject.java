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
		try {
		res.next(); String userID = res.getString("userID");
		res.next(); String name = res.getString("name");
		res.next(); String password = res.getString("password");
		res.next(); char permission = res.getString("permission").charAt(0);
		Account acc = new Account (userID, name, password, permission);
		return acc;
		}
		catch (SQLException ex) {
			//output in GUI that such account does not exists given the query
			System.out.println("Such account does not exsist");
		}
		finally {
			return null;
		}
		
	}
	
	public static Student rowToStudent(ResultSet res) {
		try {
		//get values from the main account table
		Account acc = rowToAccount(res);
		//then add the missing student instance variables
		res.next(); String title = res.getString("title");
		res.next(); String email = res.getString("email");
		res.next(); String tutor = res.getString("tutor");
		//Goes to DAC, then to rowToDegree for row -> Degree conversion
		Degree degree = DAC.getStudentDegree(acc.getUserID());
        PeriodOfStudy periodOfStudy = DAC.getPeriodOfStudy(acc.getUserID());
		//Pull periodOfStudy from PeriodOfStudy table
        Student student = new Student(acc.getUserID(), acc.getName(), acc.getPassword(),
        		acc.getPermission(), title, degree, email, tutor, periodOfStudy);
		return student;
		}
		catch (SQLException ex) {
			//output in GUI that such account does not exists given the query
			System.out.println("Such Student does not exsist");
		}
		finally {
			return null;
		}
	}
	
	public static Degree rowToDegree(ResultSet res) {
		try {
		res.next(); String degID = res.getString("degID");
		res.next(); String name = res.getString("name");
		res.next(); char level = res.getString("level").charAt(0);
		res.next(); String depID = res.getString("depID");
		Degree degree = new Degree(degID, name, level, depID);
		return degree;
		}
		catch (SQLException ex) {
			//output in GUI that such account does not exists given the query
			System.out.println("Such Degree does not exsist");
		}
		finally {
			return null;
		}
	}
	
	public static PeriodOfStudy rowToPeriod(ResultSet res) {
		try {
			while(res.next()) {
				int periodID = res.getInt("periodID");
				char label = res.getString("label").charAt(0);
				Date startDate = res.getDate("startDate");
				Date endDate = res.getDate("endDate");
				char level = res.getString("level").charAt(0);
				int regNumber = res.getInt("regNumber");
				PeriodOfStudy poS = new PeriodOfStudy(periodID, label, startDate, endDate,
						level, regNumber);
				return poS;
			}
		}
		catch (SQLException ex) {
			//output in GUI that such account does not exists given the query
			System.out.println("Such Period of Study does not exsist");
		}
		finally {
			return null;
		}
	}
	
	public static void main(String[] arg) throws SQLException {
		
	}
}
