package Database;
import Accounts.*;
import dataConversion.QueryToObject;
import Utility.*;
import Utility.Module;
import java.sql.*;

/**
 * @author Team20
 * Main DataAccessController
 */
public class DAC {
	protected static Connection connection;
	/**
	 * Opens a connection to database. Used in most DAC methods
	 * @throws SQLException
	 */
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
	/**
	 * Closes a connection. Used in most of DAC methods
	 * @throws SQLException
	 */
	protected static void closeConnection() throws SQLException {
		connection.close();
		System.out.println("Conenction is closed");
	}
	/**
	 * Gets an account from database
	 * @param userID
	 * @return Account
	 * @throws SQLException
	 */
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
	/**
	 * Returns an array of all Accounts
	 * @return Account[]
	 * @throws SQLException
	 */
	public static Account[] getAccounts() throws SQLException {
		openConnection();
		Statement stmt = connection.createStatement();
		ResultSet res = stmt.executeQuery("SELECT * FROM Account");
		int count = getCount("Account");
		Account[] accounts = QueryToObject.rowsToAccounts(res, count);
		closeConnection();
		return accounts;
		
	}
	/**
	 * gets Student from the Database using userID 
	 * @param userID
	 * @return Student
	 * @throws SQLException
	 */
	public static Student getStudent(int userID) throws SQLException {
		openConnection();
		PreparedStatement pstmt1 = connection.prepareStatement(
				"SELECT * FROM Account, Student  "
				+ "WHERE Account.userID = ? AND Student.userID = ? LIMIT 1");
		pstmt1.setInt(1, userID);
		pstmt1.setInt(2, userID);
		ResultSet resStudent = pstmt1.executeQuery();
		Student student = QueryToObject.rowToStudent(resStudent/*, resDegree, resPeriod*/);
		closeConnection();
		return student;
		
	}
	/**
	 * Returns all the Student from database (as objects), provided that the number of
	 * accounts with permission 'S' and number of Students (should never
	 * be false)
	 * @return Student[] or null if there are no Students/something went wrong
	 * @throws SQLException
	 */
	public static Student[] getAllStudents() throws SQLException {
		try {
			openConnection();
			Statement stmt = connection.createStatement();
			//counting how many students there are 
			int countS = getCount("Student");
			ResultSet resStudents = stmt.executeQuery(
					"SELECT * FROM Student NATURAL JOIN Account WHERE permission = 'S'");
			Student[] students = QueryToObject.rowsToStudents(resStudents, countS);
			closeConnection();
			return students;
	}
		catch(SQLException ex) {
			closeConnection();
			System.out.println(ex.toString());
		}
		return null;
	}
	/**
	 * Returns Student Grades
	 * @param regNumber
	 * @return Grade[] or null if there are no grades/something went wrong
	 * @throws SQLException
	 */
	public static Grade[] getStudentGrades(int regNumber) throws SQLException {
		try {
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
			Grade[] grades = QueryToObject.rowsToGrades(resGrades, count);
			closeConnection();
			return grades;
		}
		catch (SQLException ex) {
			closeConnection();
			System.out.println("getStudentGrades: " + ex.toString());
		}
		return null;
	}
	/**
	 * Returns Student Degree that a Student is currently registered for
	 * @param userID
	 * @return Degree or null if something went wrong
	 * @throws SQLException
	 */
	public static Degree getStudentDegree(int userID) throws SQLException {
		try {
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
		catch (SQLException ex) {
			closeConnection();
			System.out.println("getStudentDegree: " + ex.toString());
		}
		return null;
	}
	/**
	 * gets current/latest student level
	 * @param regNumber
	 * @return char - level index
	 * @throws SQLException
	 */
	public static char getStudentLevel(int regNumber) throws SQLException {
		try {
			openConnection();
			
			PreparedStatement pstmt = connection.prepareStatement(
					"SELECT level FROM PeriodOfStudy WHERE regNumber = ? ORDER BY startDate DESC LIMIT 1");
			pstmt.setInt(1, regNumber);
			ResultSet res = pstmt.executeQuery();
			res.next();
			char level = res.getString("level").charAt(0);
			closeConnection();
			return level; 
			
		}
		catch (SQLException ex) {
			closeConnection();
			System.out.println("getStudentLevel: " + ex.toString());
		}
		return (Character)null;
	}
	/**
	 * returns a Degree object
	 * @param degID
	 * @return Degree
	 * @throws SQLException
	 */
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
	/**
	 * Returns all Degree objects in Database
	 * @return Degree[]
	 * @throws SQLException
	 */
	public static Degree[] getDegrees() throws SQLException {
		openConnection();
		PreparedStatement pstmt = connection.prepareStatement(
				"SELECT * FROM Degree");
		ResultSet res = pstmt.executeQuery();
		//counting how many Degrees there are to properly define an array of Degree[]
		int count = getCount("Degree");
		//converting rows to Degrees
		Degree[] degrees = QueryToObject.rowsToDegrees(res, count);
		closeConnection();
		return degrees;
	}
	/**
	 * Returns all Department objects from Database
	 * @return Department[]
	 * @throws SQLException
	 */
	public static Department[] getDepartments() throws SQLException {
		openConnection();
		Statement stmt = connection.createStatement();
		ResultSet res = stmt.executeQuery("SELECT * FROM Department");
		int count = getCount("Department");
		//converting rows to Departments
		Department[] departments = QueryToObject.rowsToDepartments(res, count);
		closeConnection();
		return departments;
		}
	/**
	 * Returns current Student_Modules as objects that a Student is registered for
	 * @param regNumber
	 * @return Student_Module[]
	 * @throws SQLException
	 */
	public static Module[] getCurrentStudentModules(int regNumber) throws SQLException {
		openConnection();
		PreparedStatement pstmt = connection.prepareStatement(
				"SELECT * FROM Module"
				+ " RIGHT JOIN (SELECT * FROM Student_Module WHERE regNumber = ?) AS st_md ON Module.modID = st_md.modID ");
		pstmt.setInt(1, regNumber);
		ResultSet res = pstmt.executeQuery();
		int count = getCountWhere("Student_Module", regNumber);
		
		Module[] currentStudentModules = QueryToObject.rowsToModules(res, count);
		closeConnection();
		return currentStudentModules;
	}
	/**
	 * Returns all modules as objects from Database
	 * @return Module[]
	 * @throws SQLException
	 */
	public static Module[] getModules() throws SQLException {
		try {
			openConnection();
			int count = getCount("Module");
			Statement stmt = connection.createStatement();
			ResultSet res = stmt.executeQuery("SELECT * FROM Module");
			Module[] modules = QueryToObject.rowsToModules(res, count);
			//closeConnection();
			return modules;
		}
		catch (SQLException ex) {
			System.out.println("getModules: " + ex.toString());
			//closeConnection();
		}
		finally {
			closeConnection();
		}
		return null;
	}
	/**
	 * Returns available Modules that a Student can choose from
	 * @param regNumber
	 * @param degID
	 * @return Module[]
	 * @throws SQLException
	 */
	public static Module[] getAvailableModules(int regNumber, String degID) throws SQLException {
		try {
			openConnection();
			//pull Student's level
			PreparedStatement pstmt0 = connection.prepareStatement(
					"SELECT level FROM PeriodOfStudy WHERE regNumber = ? LIMIT 1");
			pstmt0.setInt(1, regNumber);
			ResultSet resPeriod = pstmt0.executeQuery();
			resPeriod.next();
			String level = resPeriod.getString("level");
			
			//count how many available modules there are available
			PreparedStatement pstmt1 = connection.prepareStatement(
					"SELECT COUNT(*) FROM Module WHERE degID = ? AND level = ?");
			pstmt1.setString(1, degID);
			pstmt1.setString(2, level);
			ResultSet resCount = pstmt1.executeQuery();
			resCount.next() ; int count = resCount.getInt(resCount.getRow());
			
			//--
			if (count != 0) {
				PreparedStatement pstmt2 = connection.prepareStatement(
						"SELECT * FROM Module WHERE  degID = ? AND level = ?");
				pstmt2.setString(1, degID);
				pstmt2.setString(2, level);
				ResultSet res = pstmt2.executeQuery();
				Module[] modules = QueryToObject.rowsToModules(res, count);
				return modules ;
			}
			else return null;
		}
		catch (SQLException ex) {
			System.out.println("getAvailableModules: " + ex.toString());
			ex.printStackTrace();
			
		}
		finally {
			closeConnection();
		}
		return null;
	}
	/**
	 * Returns a PeriodOfStudy object provided student's regNumber and the level
	 * @param regNumber
	 * @return PeriodOfStudy
	 * @throws SQLException
	 */
	public static PeriodOfStudy getStudentPeriodOfStudy(int regNumber,  char label) throws SQLException {
		openConnection();
		PreparedStatement pstmt = connection.prepareStatement(
				"SELECT * FROM PeriodOfStudy WHERE regNumber = ? AND label = ? LIMIT 1");
		pstmt.setInt(1, regNumber);
		pstmt.setString(2, Character.toString(label));
		ResultSet res = pstmt.executeQuery();
		PeriodOfStudy period = QueryToObject.rowToPeriod(res);
		closeConnection();
		return period;
	}
	/**
	 * Utility method for counting how many rows there are in particular table
	 * @param table
	 * @return int
	 * @throws SQLException
	 */
	public static int getCount(String table) throws SQLException {
		String query = "SELECT COUNT(*) FROM " + table;
		PreparedStatement pstmt = connection.prepareStatement(query);
		ResultSet resCount = pstmt.executeQuery();
		resCount.next(); int count = resCount.getInt(resCount.getRow());
		return count;
	}
	/**
	 * Utility method for counting how many rows there are for a particular regNumber
	 * @param table
	 * @param id
	 * @return int
	 * @throws SQLException
	 */
	public static int getCountWhere(String table, int id) throws SQLException {
		String query = "SELECT COUNT(*) FROM " + table + " WHERE regNumber = " + id ;
		Statement stmt = connection.createStatement();
		ResultSet resCount = stmt.executeQuery(query);
		resCount.next();
		int count = resCount.getInt(resCount.getRow()); 
		return count;
	}
	/**
	 * Generates an email for a Student when registering. This method is
	 * used in Student constructor.
	 * @param name
	 * @return String (email)
	 * @throws SQLException
	 */
	public static String generateEmail(String name) throws SQLException {
		//check if such name already exsits
		//openConnection();
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
			//closeConnection();
			return email;
		}
		
	}
	//for testing
	public static void main(String[] arg) throws SQLException {
		/*
		Degree degree = DAC.getDegree("COMU01");
		System.out.println(degree.toString());
		
		Account acc = DAC.getAccount(1);
		System.out.println(acc.toString());
		
		Student student = DAC.getStudent(1);
		System.out.println(student.toString());
		
		Grade[] grades = DAC.getStudentGrades(1);
		System.out.println(">>after DAC");
		System.out.println(grades[0].toString());
		System.out.println(grades[1].toString());
		
		PeriodOfStudy period = DAC.getStudentPeriodOfStudy(1, 'A');
		System.out.println(period.getStartDate());
		
		Student[] students = DAC.getAllStudents();
		System.out.println(students[0].getName());
		
		
		DAC.getDegrees();
		DAC.getDepartments(); 
		
		DAC.getAllStudents(); 
		
		DAC.getCurrentStudentModules(1);
		DAC.getAccounts();
		DAC.getAvailableModules(1, "COMU01");*/
		DAC.getModules();
		Module[] modules = DAC.getCurrentStudentModules(1);
		
		System.out.println(getStudentLevel(1));
	}
}
