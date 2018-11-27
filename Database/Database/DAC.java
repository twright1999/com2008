package Database;
import Accounts.*;
import dataConversion.QueryToObject;
import Utility.*;
import java.sql.*;

/**
 * 
 * @author Rokas
 * Main DataAccessController
 */
public final class DAC {
	private static Connection connection;
	/*
	public DAC() throws SQLException {
		connection = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team020", "team020", "aa429b86");
	}*/
	
	private static void openConnection() throws SQLException {
		try {
			System.out.println("try openning conenction");
			if (connection == null) {
				connection = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team020", "team020", "aa429b86");
				System.out.println("Connection value is assigned");
			}
			//if connection is already opened, do nothing
		}
		catch (NullPointerException nex) {
			System.out.println("connection is null");
			closeConnection();
		}
		catch (SQLException ex) {
			System.out.println("catch openConn: " + ex.toString());
			closeConnection();
		}
		
	}
	
	private static void closeConnection() throws SQLException {
		connection.close();
		System.out.println("Conenction is closed");
	}
	
	public static Account getAccount(int userID) throws SQLException {
		openConnection();
		PreparedStatement pstmt = connection.prepareStatement(
				"SELECT * FROM Account WHERE userID = ? LIMIT 1");
		pstmt.setInt(1, userID);
		ResultSet res = pstmt.executeQuery();
		Account account = QueryToObject.rowToAccount(res);
		closeConnection();
		return account;
				
	}
	
	public static Student getStudent(int userID) throws SQLException {
		System.out.println("before openConnection");
		openConnection();
		System.out.println("after openConnection");
		PreparedStatement pstmt = connection.prepareStatement(
				"SELECT * FROM Account, Student  "
				+ "WHERE Account.userID = ? AND Student.userID = ? LIMIT 1");
		/*
		PreparedStatement pstm = connection.prepareStatement(
				"SELECT * FROM Account WHERE userID = ? UNION "
			     +"SELECT * FROM Student WHERE userID = ? LIMIT 1");*/
		pstmt.setInt(1, userID);
		pstmt.setInt(2, userID);
		ResultSet res = pstmt.executeQuery();
		Student student = QueryToObject.rowToStudent(res);
		closeConnection();
		return student;
		
	}
	
	public static Degree getDegree(String degID) throws SQLException {
		openConnection();
		System.out.println("after openConnection");
		PreparedStatement pstmt = connection.prepareStatement(
				"SELECT * FROM Degree WHERE degID = ? LIMIT 1");
		pstmt.setString(1, degID);
		ResultSet res = pstmt.executeQuery();
		Degree degree = QueryToObject.rowToDegree(res);
		closeConnection();
		return degree;
	}
	
	public static Degree getStudentDegree(int userID) throws SQLException {
		openConnection();
		//navigating from Student to Module: Student -> Student_Module -> Module
		PreparedStatement pstmt = connection.prepareStatement(
				"SELECT degID FROM Module WHERE modID = "
				+   "SELECT modID FROM Student_Module WHERE regNumber = "
				+      "(SELECT regNumber FROM Student WHERE userID = ? LIMIT 1)"
				+   "LIMIT 1)"
				+"LIMIT 1");
		pstmt.setInt(1, userID);
		ResultSet res = pstmt.executeQuery();
		String degID = res.getString("degID");
		closeConnection();
		return getDegree(degID);
		
	}
	
	public static PeriodOfStudy getStudentPeriodOfStudy(int regID) throws SQLException {
		openConnection();
		PreparedStatement pstmt = connection.prepareStatement(
				"SELECT * FROM PeriodOfStudy WHERE regID = ? LIMIT 1");
		pstmt.setInt(1, regID);
		ResultSet res = pstmt.executeQuery();
		closeConnection();
		return QueryToObject.rowToPeriod(res);
	}
	
	
	//for testing
	public static void main(String[] arg) throws SQLException {
		
		Degree degree = DAC.getDegree("COMU01");
		if (degree == null)
			System.out.println("null degree");
		else System.out.println("Degree ID isss: " + degree.getDegID());
		Account acc = DAC.getAccount(00000001);
		System.out.println(acc.getName());
		Student student = DAC.getStudent(1);
	}
}
