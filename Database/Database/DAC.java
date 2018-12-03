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
	protected static Connection connection;

	protected static void openConnection() throws SQLException {
		
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
	
	protected static void closeConnection() throws SQLException {
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
	
	public static Student[] getAllStudents() throws SQLException {
		try {
		openConnection();
		Statement stmt = connection.createStatement();
		//checking if the number of accounts is the same as students (should always be true)
		ResultSet resCountS = stmt.executeQuery("SELECT COUNT(*) FROM Student");
		stmt = connection.createStatement();
		ResultSet resCountA = stmt.executeQuery("SELECT COUNT(*) FROM Account WHERE permission = 'S'");
		resCountS.next();
		int countS = resCountS.getInt(resCountS.getRow());
		resCountA.next(); 
		int countA = resCountA.getInt(resCountA.getRow());
		//check if number of Students match number of Accounts over the same ID (should never be false)
		if (countA == countS && countA != 0) {
			ResultSet resStudents = stmt.executeQuery(
					"SELECT *FROM Student NATURAL JOIN Account WHERE permission = 'S'");
			/*
			ResultSet resStudents = stmt.executeQuery(
					"SELECT * FROM Account WHERE permission = 'S' "
					+ "UNION SELECT * FROM Student");*/
			Student[] students = QueryToObject.rowsToStudents(resStudents, countS);
			closeConnection();
			return students;
		}
		else {
			closeConnection();
			return null;
		}
	}
		catch(SQLException ex) {
			closeConnection();
			System.out.println(ex.toString());
		}
		return null;
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
	
	public static Degree[] getDegrees() throws SQLException {
		openConnection();
		PreparedStatement pstmt = connection.prepareStatement(
				"SELECT * FROM Degree");
		ResultSet res = pstmt.executeQuery();
		//counting how many Degrees there are to properly define an array of Degree[]
		PreparedStatement pstmt2 = connection.prepareStatement(
				"SELECT COUNT(*) FROM Degree");
		ResultSet resCount = pstmt2.executeQuery();
		resCount.next(); int count = resCount.getInt(resCount.getRow());
		//converting rows to Degrees
		Degree[] degrees = QueryToObject.rowsToDegrees(res, count);
		closeConnection();
		return degrees;
	}
	
	public static Department[] getDepartments() throws SQLException {
		openConnection();
		PreparedStatement pstmt = connection.prepareStatement(
				"SELECT * FROM Department");
		ResultSet res = pstmt.executeQuery();
		//counting how many departments there are to define Department[] array
		PreparedStatement pstmt2 = connection.prepareStatement(
				"SELECT COUNT(*) FROM Department");
		ResultSet resCount = pstmt2.executeQuery();
		resCount.next(); int count = resCount.getInt(resCount.getRow());
		//converting rows to Departments
		Department[] departments = QueryToObject.rowsToDepartments(res, count);
		closeConnection();
		return departments;
		}
	
	public static PeriodOfStudy getStudentPeriodOfStudy(int regNumber) throws SQLException {
		openConnection();
		PreparedStatement pstmt = connection.prepareStatement(
				"SELECT * FROM PeriodOfStudy WHERE regNumber = ? LIMIT 1");
		pstmt.setInt(1, regNumber);
		ResultSet res = pstmt.executeQuery();
		PeriodOfStudy period = QueryToObject.rowToPeriod(res);
		closeConnection();
		return period;
	}
	
	public static String generateEmail(String name) throws SQLException {
		//check if such name already exsits
		openConnection();
		PreparedStatement pstmt = connection.prepareStatement(
				"SELECT email FROM Student WHERE userID = "
				+ "(SELECT userID FROM Account WHERE name = ? LIMIT 1)"
				+ " ORDER BY email DESC LIMIT 1");
		pstmt.setString(1 , "name");
		ResultSet res = pstmt.executeQuery();
		
		//check if there are any duplicate names
		//++1 to the last email
		if (res.next()) {
			String email = res.getString("email");
			int lc = email.lastIndexOf('@');
			int length = email.length();
			//incremented email index
			int incremented = 1 + Integer.valueOf(email.substring(lc-1,lc));
			email = email.substring(0, lc-1) + incremented + email.substring(lc+1, length ) ;
			closeConnection();
			return email;
		}
		//or generate new email
		else {
			String uniEmail = "@WrightUni.Tom.uk";
			int firstSpace = name.indexOf(" ");
			String nameLetter = name.substring(firstSpace+1, firstSpace+2);
			int lastSpace = name.lastIndexOf(" ");
			String surname = name.substring(lastSpace+1);
			String email = nameLetter + surname + "1" + uniEmail;
			closeConnection();
			return email;
		}
		
	}
	//for testing
	public static void main(String[] arg) throws SQLException {
		/*
		Degree degree = DAC.getDegree("COMU01");
		System.out.println(degree.toString());
		
		Account acc = DAC.getAccount(16);
		System.out.println(acc.toString());
		
		Student student = DAC.getStudent(16);
		System.out.println(student.toString());
		
		Grade[] grades = DAC.getStudentGrades(987654321);
		System.out.println(">>after DAC");
		System.out.println(grades[0].toString());
		System.out.println(grades[1].toString());
		
		PeriodOfStudy period = DAC.getStudentPeriodOfStudy(987654321);
		System.out.println(period.getStartDate());
		
		Student[] students = DAC.getAllStudents();
		System.out.println(students[0].getName());
		*/
		DAC.getDegrees();
		DAC.getDepartments();
		
	}
}
