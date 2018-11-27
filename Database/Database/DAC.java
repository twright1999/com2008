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
			//closeConnection();
		}
		catch (SQLException ex) {
			System.out.println("catch SQL");
			//closeConnection();
		}
		
	}
	
	private static void closeConnection() throws SQLException {
		connection.close();
		System.out.println("Conenction is closed");
	}
	
	public static Student getStudent(int userID) throws SQLException, NullPointerException {
		System.out.println("before openConnection");
		openConnection();
		System.out.println("after openConnection");
		PreparedStatement pstm = connection.prepareStatement(
				"SELECT * FROM Student WHERE userID = ? LIMIT 1");
		pstm.setInt(1, userID);
		ResultSet res = pstm.executeQuery();
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
	
	public PeriodOfStudy getPeriodOfStudy(String periodID) throws SQLException {
		openConnection();
		PreparedStatement pstmt = connection.prepareStatement(
				"SELECT * FROM PeriodOfStudy WHERE periodID = ? LIMIT 1");
		pstmt.setString(1, periodID);
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
		
	}
}
