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
	
	public static void addInitialGrade(float initialGrade, String modID, String periodID) throws SQLException {
		openConnection();
		String query = "INSERT INTO Grade (gradeID, initialGrade, resitGrade, modID, periodID)" 
			+ " values (?, ?, ?, ?, ?)";
		PreparedStatement pstm = connection.prepareStatement(query);
		pstm.setInt(1, 0);
		pstm.setFloat(2, initialGrade);
		pstm.setFloat(3, -1); //-1 is a place holder for null 
		pstm.setString(4, modID);
		pstm.setString(5, periodID);
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
	
	public static boolean calcPeriod(String periodID) throws SQLException {
		openConnection();
		Statement stmt = connection.createStatement();
		ResultSet periodQuery = stmt.executeQuery("SELECT * FROM PeriodOfStudy WHERE periodID = '" + periodID + "'");
		
		periodQuery.next();
		String level = periodQuery.getString("level");
		
		ResultSet gradeQuery = stmt.executeQuery("SELECT Grade.initialGrade, Grade.resitGrade, Module.level FROM Grade " +
				"INNER JOIN Module ON Grade.modID = Module.modID " +
				"WHERE Grade.periodID = '" + periodID + "'");			
		
		float totalPercent = 0;
		int totalGrades = 0;
		boolean passedEveryModule = true;
		List<Float> failed = new ArrayList<Float>();
		List<Integer> failedLevel = new ArrayList<Integer>();
		
		String gradeName;
		
		while (gradeQuery.next()) {
			if (gradeQuery.getFloat("resitGrade") >= 0) gradeName = "resitGrade";
			else gradeName = "initialGrade";
			
			totalPercent += gradeQuery.getFloat(gradeName);
			totalGrades += 1;
			if (level == "P");
			else if (Integer.parseInt(level) == 4 && gradeQuery.getFloat(gradeName) < 50) {
				passedEveryModule = false;
				failed.add(gradeQuery.getFloat(gradeName));
				failedLevel.add(gradeQuery.getInt("level"));
			} else if (Integer.parseInt(level) < 4 && gradeQuery.getFloat(gradeName) < 40) {
				passedEveryModule = false;	
				failed.add(gradeQuery.getFloat(gradeName));
				failedLevel.add(gradeQuery.getInt("level"));
			}			
		}
			
		closeConnection();
		
		if (failed.size() == 1) {
			if (failedLevel.get(0) < 4 && failed.get(0) >= 30) passedEveryModule = true;
			else if (failedLevel.get(0) == 4 && failed.get(0) >= 40) passedEveryModule = true;
		}	
		
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
		
		ResultSet degreeQuery = stmt.executeQuery("SELECT Degree.name, Degree.level FROM Degree " +
				"INNER JOIN Student ON Student.degID = Degree.degID " +
				"WHERE regNumber = " + regNumber);
		
		degreeQuery.next();
		String degreeName = degreeQuery.getString("name");
		String degreeLevel = degreeQuery.getString("level");
		
		
		ResultSet gradeQuery = stmt.executeQuery("SELECT Grade.initialGrade, Grade.resitGrade, Module.level, Module.credits FROM Grade " + 
				"INNER JOIN PeriodOfStudy ON Grade.periodID = PeriodOfStudy.periodID " +
				"INNER JOIN Module ON Grade.modID = Module.modID " +
				"WHERE PeriodOfStudy.regNumber = " + regNumber);
		
		int creditAmount = 0;
		if (degreeName.contains("MSc") || degreeName.contains("MComp") || degreeName.contains("MEng")) {
			creditAmount = 180;
		} else if (degreeName.contains("BSc") || degreeName.contains("BEng")) {
			creditAmount = 120;
		}
		
		double[] grades = {0,0,0,0};
		
		int level = 0;
		String gradeName;
		boolean resitYear = false;
		
		while (gradeQuery.next()) {
			if (gradeQuery.getFloat("resitGrade") >= 0) gradeName = "resitGrade";
			else gradeName = "initialGrade";
			
			level = Integer.parseInt(gradeQuery.getString("level"));
			
			Double grade = gradeQuery.getDouble(gradeName);
			
			if (gradeName == "resitGrade") {
				resitYear = true;
				if (grade > 40 && level == 3) grade = 40.0;
				else if (grade > 50 && level == 4) grade = 50.0;
			}
			
			grades[level-1] += grade * (gradeQuery.getFloat("credits")/creditAmount);
		}
		
		double bachelors = (grades[1] + grades[2]*2)/3;
		double masters = (grades[1] + grades[2]*2 + grades[3]*2)/5;
		
		ResultSet periodQuery = stmt.executeQuery("SELECT level FROM PeriodOfStudy " +
				"WHERE regNumber = " + regNumber +
				" ORDER BY level DESC");
		
		periodQuery.next();
		String periodLevel = periodQuery.getString("level");
		
		boolean lastYearResit;
		if (resitYear && degreeLevel == periodQuery.getString("level")) lastYearResit = true;
		else lastYearResit = false;
		
		ResultSet moduleQuery = stmt.executeQuery("SELECT Module.name, Grade.initialGrade, Grade.resitGrade FROM Module " + 
				"INNER JOIN Grade ON Module.modID = Grade.modID " + 
				"INNER JOIN PeriodOfStudy ON Grade.periodID = PeriodOfStudy.periodID " +
				"WHERE PeriodOfStudy.regNumber = " + regNumber);
		
		float dissertationGrade = 0;
		while(moduleQuery.next()) {
			if (moduleQuery.getString("name").contains("Dissertation")) {
				if (moduleQuery.getFloat("resitGrade") >= 0) dissertationGrade = moduleQuery.getFloat("resitGrade");
				else dissertationGrade = moduleQuery.getFloat("initialGrade");
			}
		}
		
		ResultSet periodIDQuery = stmt.executeQuery("SELECT periodID FROM PeriodOfStudy WHERE regNumber = " + regNumber);
		
		List<String> periodIDs = new ArrayList<String>();
		while(periodIDQuery.next()) {
			periodIDs.add(periodIDQuery.getString("periodID"));
		}
		
		for (String id : periodIDs) {
			if (!calcPeriod(id)) return "fail";
		}
			
		closeConnection();
		
		boolean failedMasters = false;
		
		if (degreeName.contains("MSc")) {
			if (grades[3] < 49.5) {
				if (degreeLevel == periodLevel) failedMasters = true;
				else return "fail";		
			}
			if (dissertationGrade < 49.5) return "PGDip";
			else if (grades[3] >= 49.5 && grades[3] < 59.5) return "Masters pass";
			else if (grades[3] >= 59.5 && grades[3] < 69.5) return "Masters merit";
			else if (grades[3] >= 69.5) return "Masters disinction";
		} else if (degreeName.contains("MComp") || degreeName.contains("MEng") || degreeName.contains("MPsy")) {
			if (masters < 49.5) {
				if (degreeLevel == periodQuery.getString("level")) failedMasters = true;
				else return "fail";	
			}
			else if (masters >= 49.5 && masters < 59.5) return "Masters lower second";
			else if (masters >= 59.5 && masters < 69.5) return "Masters upper second";
			else if (masters >= 69.5) return "Masters first class";
		} 
		
		if (failedMasters || degreeName.contains("BSc") || degreeName.contains("BEng")) {
			if (bachelors < 39.5) return "fail";
			else if (bachelors >= 39.5 && lastYearResit) return "Bachelors: pass (non-honours)";
			else if (bachelors >= 39.5 && bachelors < 44.5) return "Bachelors: pass (non-honours)";
			else if (bachelors >= 44.5 && bachelors < 49.5) return "Bachelors: third class";
			else if (bachelors >= 49.5 && bachelors < 59.5) return "Bachelors: lower second";
			else if (bachelors >= 59.5 && bachelors < 69.5) return "Bachelors: upper second";
			else if (bachelors >= 69.5) return "Bachelors: first class";
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
	* @return List<List<String>> returns a 2d array list of strings stating all grades for all modules,
	*                the average grade for every level and
	*                the degree earned
	*/
	
	public static List<List<String>> getStudentStatus(int regNumber) throws SQLException {
		openConnection();
		Statement stmt = connection.createStatement();
		
		ResultSet degreeQuery = stmt.executeQuery("SELECT Degree.name FROM Degree " +
				"INNER JOIN Student ON Student.degID = Degree.degID " +
				"WHERE regNumber = " + regNumber);
		
		degreeQuery.next();
		String degreeName = degreeQuery.getString("name");
		
		ResultSet gradeQuery = stmt.executeQuery("SELECT Grade.initialGrade, Grade.resitGrade, Module.level, Module.name, Module.credits FROM Grade " + 
				"INNER JOIN Module ON Grade.modID = Module.modID " +
				"INNER JOIN Student_Module ON Module.modID = Student_Module.modID " +
				"WHERE Student_Module.regNumber = " + regNumber);
		
		List<List<String>> outputArray = new ArrayList<List<String>>();
		
		double[] grades = {0,0,0,0};
		
		int level = 0;
		String gradeName;
		
		List<String> subArray = new ArrayList<String>();
		
		int creditAmount = 0;
		if (degreeName.contains("MSc") || degreeName.contains("MComp") || degreeName.contains("MEng")) {
			creditAmount = 180;
		} else if (degreeName.contains("BSc") || degreeName.contains("BEng")) {
			creditAmount = 120;
		}
		
		while (gradeQuery.next()) {
			if (gradeQuery.getFloat("resitGrade") >= 0) gradeName = "resitGrade";
			else gradeName = "initialGrade";
			
			subArray.add(gradeQuery.getString("name"));
			subArray.add(Double.toString(gradeQuery.getDouble(gradeName)));
			outputArray.add(subArray);
			
			subArray = new ArrayList<String>();
			
			Double grade = gradeQuery.getDouble(gradeName);
			
			if (gradeName == "resitGrade") {
				if (grade > 40 && level == 3) grade = 40.0;
				else if (grade > 50 && level == 4) grade = 50.0;
			}
			
			level = Integer.parseInt(gradeQuery.getString("level"));
			grades[level-1] += grade * (gradeQuery.getFloat("credits")/creditAmount);
		}
		
		for (int i = 0; i <= 3; i++) {
			if (grades[i] != 0) {
				subArray.add("Level " + Integer.toString(i+1));
				subArray.add(Double.toString(grades[i]));
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
		
		ResultSet degreeQuery = stmt.executeQuery("SELECT Degree.level FROM Degree " +
				"INNER JOIN Student ON Degree.degID = Student.degID " +
				"WHERE Student.regNumber = " + regNumber);
		
		degreeQuery.next();
		String degreeLevel = degreeQuery.getString("level");
		
		ResultSet periodQuery =  stmt.executeQuery("SELECT label, level FROM PeriodOfStudy " +
				"ORDER BY label DESC " +
				"WHERE regNumber = " + regNumber);
		
		if (calcPeriod(periodID)) {
			PreparedStatement pstm = connection.prepareStatement("DELETE FROM Student_Module WHERE regNumber = ?");
			pstm.setInt(1, regNumber);
			pstm.executeUpdate();
			
			closeConnection();
			
			periodQuery.next();
			if (degreeLevel == periodQuery.getString("level")) {
				return calcDegree(regNumber);
			}
			
			String nextLabel = Character.toString((char)(((int)(periodQuery.getString("label").charAt(0)))+1));
			String nextLevel = Integer.toString((Integer.parseInt(periodQuery.getString("level")))+1);
			DACRegistrar.registerStudent(nextLabel, startDate, endDate, nextLevel, regNumber);
			
			return "Next Level";
		} else {
			closeConnection();
			
			String nextLabel = Character.toString((char)(((int)(periodQuery.getString("label").charAt(0)))+1));
			DACRegistrar.registerStudent(nextLabel, startDate, endDate, periodQuery.getString("level"), regNumber);
			
			return "Resit";
		}
	}
	
	//for testing
	public static void main(String[] arg) throws SQLException {
//		updateResitGrade(6,-1);
//		addInitialGrade(25,"COM2008","A1");
		
//		addInitialGrade(100,"COM2008","1A");
//		updateInitialGrade(22,60);
//		System.out.println(calcDegree(9));
	}
			
}
