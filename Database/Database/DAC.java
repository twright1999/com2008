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
public class DAC {
	private static Connection connection;
	/*
	public DAC() throws SQLException {
		connection = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team020", "team020", "aa429b86");
	}*/
	
	private static void openConnection() throws SQLException {
		
		try {
			System.out.print("try openning conenction: ");
			if (connection == null || connection.isClosed()) {
				connection = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team020", "team020", "aa429b86");
				System.out.println("Connection is open");
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
		openConnection();
		PreparedStatement pstmt1 = connection.prepareStatement(
				"SELECT * FROM Account, Student  "
				+ "WHERE Account.userID = ? AND Student.userID = ? LIMIT 1");
		pstmt1.setInt(1, userID);
		pstmt1.setInt(2, userID);
		ResultSet resStudent = pstmt1.executeQuery();
		PreparedStatement pstmt2 = connection.prepareStatement(
				"SELECT * FROM Degree WHERE degID = "
				+ "(SELECT degID FROM Module WHERE modID = "
						+ "(SELECT modID FROM Student_Module WHERE regNumber = "
						+   "(SELECT regNumber FROM Student WHERE UserID = ? LIMIT 1) LIMIT 1 ) LIMIT 1) LIMIT 1");
		pstmt2.setInt(1, userID);
		ResultSet resDegree = pstmt2.executeQuery();
		
		PreparedStatement pstmt3 = connection.prepareStatement(
				"SELECT * FROM PeriodOfStudy WHERE regNumber = (SELECT regNumber FROM Student WHERE userID = ? LIMIT 1) LIMIT 1");
		pstmt3.setInt(1, userID);
		ResultSet resPeriod = pstmt3.executeQuery();
		Student student = QueryToObject.rowToStudent(resStudent, resDegree, resPeriod);
		closeConnection();
		return student;
		
	}
	
	public static Grade[] getStudentGrades(int regNumber) throws SQLException {
		openConnection();
		PreparedStatement pstmt = connection.prepareStatement(
				"SELECT * FROM Grade WHERE regNumber = ?");
		pstmt.setInt(1, regNumber);
		ResultSet resGrades = pstmt.executeQuery();
		//counting how many grades there are to know the array size of grades
		pstmt = connection.prepareStatement("SELECT COUNT(*) FROM Grade WHERE regNumber = ?");
		pstmt.setInt(1, regNumber);
		ResultSet resCount = pstmt.executeQuery();
		resCount.next();
		int count = resCount.getInt(resCount.getRow());
		System.out.println("Count: " + count) ;
		System.out.println("before rowsToGrades");
		Grade[] grades = QueryToObject.rowsToGrades(resGrades, count);
		closeConnection();
		return grades;
	}
	
	public static Degree getStudentDegree(int userID) throws SQLException {
		openConnection();
		//navigating from Student to Module: Student -> Student_Module -> Module
		PreparedStatement pstmt = connection.prepareStatement(
				"SELECT degID FROM Module WHERE modID = "
				+ "(SELECT modID FROM Student_Module WHERE regNumber = "
				+   "(SELECT regNumber FROM Student WHERE UserID = ? LIMIT 1) LIMIT 1 ) LIMIT 1");
		
		pstmt.setInt(1, userID);
		ResultSet res = pstmt.executeQuery();
		closeConnection();
		String degID = res.getString("degID");
		System.out.println(">>GETStudentDegree is executed");
		return getDegree(degID);
		
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
		/*
		Degree degree = DAC.getDegree("COMU01");
		System.out.println(degree.toString());
		
		Account acc = DAC.getAccount(13);
		System.out.println(acc.toString());
		
		Student student = DAC.getStudent(13);
		System.out.println(student.toString());
		*/
		Grade[] grades = DAC.getStudentGrades(987654321);
		System.out.println(">>after DAC");
		System.out.println(grades[0].toString());
		System.out.println(grades[1].toString());
	}
}
