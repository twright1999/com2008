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
			connection = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team020", "team020", "aa429b86");
			if (connection != null) System.out.println(connection.toString());
		}
		catch (SQLException ex) {
			closeConnection();
		}
		catch (NullPointerException nex) {
			System.out.println("connection is null");
		}
		
	}
	
	private static void closeConnection() throws SQLException {
		connection.close();
	}
	
	public static Student getStudent(String userID) throws SQLException, NullPointerException {
		
		openConnection();
		PreparedStatement pstm = connection.prepareStatement(
				"SELECT * FROM Student WHERE userID = ? LIMIT 1");
		pstm.setString(1, userID);
		ResultSet res = pstm.executeQuery();
		Student student = QueryToObject.rowToStudent(res);
		closeConnection();
		return student;
		
		
	}
	
	public static Degree getDegree(String degID) throws SQLException {
		openConnection();
		PreparedStatement pstmt = connection.prepareStatement(
				"SELECT * FROM Degree WHERE degID = ? LIMIT 1");
		pstmt.setString(1, degID);
		ResultSet res = pstmt.executeQuery();
		Degree degree = QueryToObject.rowToDegree(res);
		closeConnection();
		return degree;
	}
	
	public static Degree getStudentDegree(String userID) throws SQLException {
		openConnection();
		//navigating from Student to Module: Student -> Student_Module -> Module
		PreparedStatement pstmt = connection.prepareStatement(
				"SELECT degID FROM Module WHERE modID = "
				+   "SELECT modID FROM Student_Module WHERE regNumber = "
				+      "(SELECT regNumber FROM Student WHERE userID = ? LIMIT 1)"
				+   "LIMIT 1)"
				+"LIMIT 1");
		pstmt.setString(1, userID);
		ResultSet res = pstmt.executeQuery();
		String degID = res.getString("degID");
		closeConnection();
		return getDegree(degID);
		
	}
	
	public static PeriodOfStudy getPeriodOfStudy(String periodID) throws SQLException {
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
		//DAC test = new DAC();
		Student aa = DAC.getStudent("1");
		if (aa != null)
		System.out.println("not null :) ");
		//System.out.println("getDegree: " + DAC.getDegree("COMU01").getDegID() );
		
	}
}
