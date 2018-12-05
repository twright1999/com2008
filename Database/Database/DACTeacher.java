package Database;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.*;

/**
* DACTeacher.java
*
* Data Access Controller for Teacher
*
* @author Team 20
*/

public class DACTeacher extends DAC {
	
	/**
	* addGrade
	* 
	* takes a grade, module and a student and adds that grade
	* 
	* @param gradePercent the value of the grade to be added
	* @param modID the unique identifier for the module 
	* @param regNumber unique identifier for a student
	*/
	
	public static void addInitialGrade(float initialGrade, String modID, int regNumber) throws SQLException {
		openConnection();
		String query = "INSERT INTO Grade (gradeID, initialGrade, resitGrade, modID, regNumber)" 
			+ " values (?, ?, ?, ?, ?)";
		PreparedStatement pstm = connection.prepareStatement(query);
		pstm.setInt(1, 0);
		pstm.setFloat(2, initialGrade);
		pstm.setFloat(3, -1); //-1 is a place holder for null 
		pstm.setString(4, modID);
		pstm.setInt(5, regNumber);
		pstm.executeUpdate();	
		closeConnection();
	}
	
	/**
	* updateInitialGrade
	* 
	* takes a gradeID, and a new grade and updates the value to be the new grade
	* 
	* @param gradeID the value of the gradeID to be updated
	* @param newGrade the new grade to be inserted into the table
	*/
	
	public static void updateInitialGrade(int gradeID, float newGrade) throws SQLException {
		openConnection();
		String update = "UPDATE Grade SET initialGrade = ? WHERE gradeID = " + gradeID;
		PreparedStatement pstm = connection.prepareStatement(update);
		pstm.setFloat(1, newGrade);
		pstm.executeUpdate();
		closeConnection();
	}
	
	/**
	* updateResitGrade
	* 
	* takes a gradeID, and a new grade and updates the value to be the new grade
	* 
	* @param gradeID the value of the gradeID to be updated
	* @param newGrade the new grade to be inserted into the table
	*/
	
	public static void updateResitGrade(int gradeID, float newGrade) throws SQLException {
		openConnection();
		String update = "UPDATE Grade SET resitGrade = ? WHERE gradeID = " + gradeID;
		PreparedStatement pstm = connection.prepareStatement(update);
		pstm.setFloat(1, newGrade);
		pstm.executeUpdate();
		closeConnection();
	}
	
	/**
	* removeGrade
	* 
	* takes a gradeID and removes it from the database
	* 
	* @param gradeID unique identifier for a grade
	*/
	
	public static void removeGrade(int gradeID) throws SQLException {
		openConnection();
		PreparedStatement pstm = connection.prepareStatement(
				"DELETE * FROM Grade WHERE gradeID = ?");
		pstm.setInt(1, gradeID);
		pstm.executeUpdate();
		closeConnection();
	}
	
	/**
	* calcPeriod
	* 
	* takes a regNumber and a periodID and determines whether the student has passed the given period
	* 
	* @param regNumber unique identifier for a student
	* @param periodID unique identifier for a period
	* 
	* @return boolean returns true if the student passed the period
	*/
	
	public static boolean calcPeriod(int regNumber, String periodID) throws SQLException {
		openConnection();
		Statement stmt = connection.createStatement();
		
		ResultSet periodQuery = stmt.executeQuery("SELECT * FROM PeriodOfStudy WHERE periodID = " + periodID);
		
		periodQuery.next();
		String level = periodQuery.getString("level");
		
		ResultSet gradeQuery = stmt.executeQuery("SELECT Grade.initialGrade, Grade.resitGrade, Module.level FROM Grade " + 
				"INNER JOIN Module ON Grade.modID = Module.modID " +
				"INNER JOIN Student_Module ON Module.modID = Student_Module.modID " +
				"WHERE Module.level = " + level + " && Student_Module.regNumber = " + regNumber);			
		
		float totalPercent = 0;
		int totalGrades = 0;
		boolean passedEveryModule = true;
		String gradeName;
		
		while (gradeQuery.next()) {
			if (gradeQuery.getFloat("resitGrade") >= 0) gradeName = "resitGrade";
			else gradeName = "initialGrade";
			
			totalPercent += gradeQuery.getFloat(gradeName);
			totalGrades += 1;
			if (gradeQuery.getString("level") == "P");
			else if (Integer.parseInt(gradeQuery.getString("level")) == 4 && gradeQuery.getFloat(gradeName) < 50)
				passedEveryModule = false;
			else if (Integer.parseInt(gradeQuery.getString("level")) < 4 && gradeQuery.getFloat(gradeName) < 40)
				passedEveryModule = false;				
		}
			
			closeConnection();
			float average = totalPercent/totalGrades;
			if (level == "4") {
				if (average >= 50.0 && passedEveryModule)
					return true;
				else
					return false;
			} else {
				if (average >= 40.0 && passedEveryModule)
					return true;
				else
					return false;
			}
		}
	
	/**
	* calcDegree
	* 
	* takes a regNumber and determines what class of degree the student has
	* 
	* @param regNumber unique identifier for a student
	* 
	* @return String returns the name of the degree earned
	*/
	
	public static String calcDegree(int regNumber) throws SQLException {
		openConnection();
		Statement stmt = connection.createStatement();
		ResultSet gradeQuery = stmt.executeQuery("SELECT Grade.initialGrade, Grade.resitGrade, Module.level FROM Grade " + 
				"INNER JOIN Module ON Grade.modID = Module.modID " +
				"INNER JOIN Student_Module ON Module.modID = Student_Module.modID " +
				"WHERE Student_Module.regNumber = " + regNumber);
		
		double[] grades = {0,0,0,0};
		int[] gradeCounts = {0,0,0,0};
		
		int level = 0;
		String gradeName;
		
		while (gradeQuery.next()) {
			if (gradeQuery.getFloat("resitGrade") >= 0) gradeName = "resitGrade";
			else gradeName = "initialGrade";
			
			level = Integer.parseInt(gradeQuery.getString("level"));
			
			grades[level-1] += gradeQuery.getDouble(gradeName);
			gradeCounts[level-1] += 1;
		}
		
		for (int i = 0; i <= 3; i++) {
			if (grades[i] != 0) grades[i] /= gradeCounts[i];
		}
		
		ResultSet degreeQuery = stmt.executeQuery("SELECT Degree.name FROM Degree " +
				"INNER JOIN Student ON Student.degID = Degree.degID " +
				"WHERE regNumber = " + regNumber);
		
		degreeQuery.next();
		String degreeName = degreeQuery.getString("name");
		
		closeConnection();
		
		double bachelors = (grades[1] + grades[2]*2)/2;
		double masters = (grades[1] + grades[2]*2 + grades[3]*2)/3;
				
		if (degreeName.contains("MSc")) {
			if (grades[0] < 49.5) return "fail";
			else if (grades[0] >= 49.5 && grades[0] < 59.5) return "pass";
			else if (grades[0] >= 59.5 && grades[0] < 69.5) return "merit";
			else if (grades[0] >= 69.5) return "disinction";
		} else if (degreeName.contains("BSc") || degreeName.contains("BEng")) {
			if (bachelors < 39.5) return "fail";
			else if (bachelors >= 39.5 && bachelors < 44.5) return "pass (non-honours)";
			else if (bachelors >= 44.5 && bachelors < 49.5) return "third class";
			else if (bachelors >= 49.5 && bachelors < 59.5) return "lower second";
			else if (bachelors >= 59.5 && bachelors < 69.5) return "upper second";
			else if (bachelors >= 69.5) return "first class";
		} else if (degreeName.contains("MComp") || degreeName.contains("MEng")) {
			if (masters < 49.5) return "fail";
			else if (masters >= 49.5 && masters < 59.5) return "lower second";
			else if (masters >= 59.5 && masters < 69.5) return "upper second";
			else if (masters >= 69.5) return "first class";
		} 
			
		return "degree not found";
	}
	
	/**
	* getStudentStatus
	* 
	* takes a regNumber and returns all grades that the student has
	* 
	* @param regNumber unique identifier for a student
	* 
	* @return String returns a string containing all grades for all modules,
	*                the average grade for every level and
	*                the degree earned
	*/
	
	public static List<List<String>> getStudentStatus(int regNumber) throws SQLException {
		openConnection();
		Statement stmt = connection.createStatement();
		ResultSet gradeQuery = stmt.executeQuery("SELECT Grade.initialGrade, Grade.resitGrade, Module.level, Module.name FROM Grade " + 
				"INNER JOIN Module ON Grade.modID = Module.modID " +
				"INNER JOIN Student_Module ON Module.modID = Student_Module.modID " +
				"WHERE Student_Module.regNumber = " + regNumber);
		
		List<List<String>> outputArray = new ArrayList<List<String>>();
		
		double[] grades = {0,0,0,0};
		int[] gradeCounts = {0,0,0,0};
		
		int level = 0;
		String gradeName;
		
		List<String> subArray = new ArrayList<String>();
		
		while (gradeQuery.next()) {
			if (gradeQuery.getFloat("resitGrade") >= 0) gradeName = "resitGrade";
			else gradeName = "initialGrade";
			
			subArray.add(gradeQuery.getString("name"));
			subArray.add(Double.toString(gradeQuery.getDouble(gradeName)));
			outputArray.add(subArray);
			
			subArray = new ArrayList<String>();
			
			level = Integer.parseInt(gradeQuery.getString("level"));
			grades[level-1] += gradeQuery.getDouble(gradeName);
			gradeCounts[level-1] += 1;
		}
		
		for (int i = 0; i <= 3; i++) {
			if (grades[i] != 0) {
				subArray.add("Level " + Integer.toString(i+1));
				subArray.add(Double.toString(grades[i] / gradeCounts[i]));
				outputArray.add(subArray);
				
				subArray = new ArrayList<String>();
			}
		}
		
		subArray.add("Degree");
		subArray.add(calcDegree(regNumber));
		outputArray.add(subArray);
		
		closeConnection();
		return outputArray;	
	}
	
	/**
	* advanceStudent
	* 
	* if a student passes the year they are registered for the next year, if not they resit the year
	* 
	* @param regNumber unique identifier for a student
	* @param periodID unique identifier for a period
	* @param startDate first day of the the next period
	* @param endDate last day of the the next period
	* 
	* @return String returns a string stating what will happen to the student
	*/
	
	public static String advanceStudent(int regNumber, String periodID, String startDate, String endDate) throws SQLException {
		openConnection();
		Statement stmt = connection.createStatement();
		
		if (calcPeriod(regNumber, periodID)) {
			PreparedStatement pstm = connection.prepareStatement("DELETE FROM Student_Module WHERE regNumber = ?");
			pstm.setInt(1, regNumber);
			pstm.executeUpdate();
			
			ResultSet degreeQuery = stmt.executeQuery("SELECT Degree.level FROM Degree " +
					"INNER JOIN Student ON Degree.degID = Student.degID " +
					"WHERE Student.regNumber = " + regNumber);
			
			degreeQuery.next();
			String degreeLevel = degreeQuery.getString("level");
			
			ResultSet periodQuery =  stmt.executeQuery("SELECT label, level FROM PeriodOfStudy " +
					"ORDER BY label DESC " +
					"WHERE regNumber = " + regNumber);
			
			closeConnection();
			
			periodQuery.next();
			if (degreeLevel == periodQuery.getString("level")) {
				if (degreeLevel == "4") return "Masters";
				else return "Graduate";
			}
			
			String nextLabel = Character.toString((char)(((int)(periodQuery.getString("label").charAt(0)))+1));
			String nextLevel = Integer.toString((Integer.parseInt(periodQuery.getString("level")))+1);
			DACRegistrar.registerStudent(nextLabel, startDate, endDate, nextLevel, regNumber);
			
			return "Next Level";
		} else {
			return "Resit";
		}
	}
	
	//for testing
	public static void main(String[] arg) throws SQLException {
//		updateResitGrade(6,-1);
		addInitialGrade(25,"COM2008",1);
		System.out.println(getStudentStatus(1));
	}
			
}
