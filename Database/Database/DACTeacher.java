package Database;
import java.util.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class DACTeacher extends DAC {
	
	public static void addGrade(float gradePercent, String modID, int regNumber) throws SQLException {
		openConnection();
		String query = "INSERT INTO Grade (gradeID, gradePercent, modID, regNumber)" 
			+ " values (?, ?, ?, ?)";
		PreparedStatement pstm = connection.prepareStatement(query);
		pstm.setInt(1, 0);
		pstm.setFloat(2, gradePercent);
		pstm.setString(3, modID);
		pstm.setInt(4, regNumber);
		pstm.executeUpdate();	
		closeConnection();
	}
	
	public static void removeGrade(int gradeID) throws SQLException {
		openConnection();
		PreparedStatement pstm = connection.prepareStatement(
				"DELETE * FROM Grade WHERE gradeID = ?");
		pstm.setInt(1, gradeID);
		pstm.executeUpdate();
		closeConnection();

	}
	
	public static boolean calcPeriod(int regNumber, int periodID) throws SQLException {
		openConnection();
		Statement stmt = connection.createStatement();
		
		ResultSet periodQuery = stmt.executeQuery("SELECT * FROM PeriodOfStudy WHERE periodID = " + periodID);
		
		periodQuery.next();
		String level = periodQuery.getString("level");
		
		ResultSet gradeQuery = stmt.executeQuery("SELECT Grade.gradePercent, Module.level FROM Grade " + 
				"INNER JOIN Module ON Grade.modID = Module.modID " +
				"INNER JOIN Student_Module ON Module.modID = Student_Module.modID " +
				"WHERE Module.level = " + level + " && Student_Module.regNumber = " + regNumber);			
		
		float totalPercent = 0;
		int totalGrades = 0;
		boolean passedEveryModule = true;
		
		while (gradeQuery.next()) {
			totalPercent += gradeQuery.getFloat("gradePercent");
			totalGrades += 1;
			if (gradeQuery.getString("level") == "P");
			else if (Integer.parseInt(gradeQuery.getString("level")) == 4 && gradeQuery.getFloat("gradePercent") < 50)
				passedEveryModule = false;
			else if (Integer.parseInt(gradeQuery.getString("level")) < 4 && gradeQuery.getFloat("gradePercent") < 40)
				passedEveryModule = false;				
		}
			
			closeConnection();
			float average = totalPercent/totalGrades;
			if (average >= 40.0 && passedEveryModule)
				return true;
			else
				return false;
		}
	
	public static String calcDegree(int regNumber) throws SQLException {
		openConnection();
		Statement stmt = connection.createStatement();
		ResultSet gradeQuery = stmt.executeQuery("SELECT Grade.gradePercent, Module.level FROM Grade " + 
				"INNER JOIN Module ON Grade.modID = Module.modID " +
				"INNER JOIN Student_Module ON Module.modID = Student_Module.modID " +
				"WHERE Student_Module.regNumber = " + regNumber);
		
		double[] grades = {0,0,0,0};
		int[] gradeCounts = {0,0,0,0};
		
		int level = 0;
		while (gradeQuery.next()) {
			level = Integer.parseInt(gradeQuery.getString("level"));
			
			grades[level-1] += gradeQuery.getDouble("gradePercent");
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
		
		double bachelors = (grades[1] + grades[2])/2;
		double masters = (grades[1] + grades[2] + grades[3])/3;
				
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
	
	public static String getStudentStatus(int regNumber) throws SQLException {
		openConnection();
		Statement stmt = connection.createStatement();
		ResultSet gradeQuery = stmt.executeQuery("SELECT Grade.gradePercent, Module.level, Module.name FROM Grade " + 
				"INNER JOIN Module ON Grade.modID = Module.modID " +
				"INNER JOIN Student_Module ON Module.modID = Student_Module.modID " +
				"WHERE Student_Module.regNumber = " + regNumber);
		
		String outputString = "";
		
		double[] grades = {0,0,0,0};
		int[] gradeCounts = {0,0,0,0};
		
		int level = 0;
		while (gradeQuery.next()) {
			outputString += gradeQuery.getString("name") + ": ";
			outputString += Double.toString(gradeQuery.getDouble("gradePercent")) + "%\n";
			
			level = Integer.parseInt(gradeQuery.getString("level"));
			grades[level-1] += gradeQuery.getDouble("gradePercent");
			gradeCounts[level-1] += 1;
		}
		
		for (int i = 0; i <= 3; i++) {
			if (grades[i] != 0) outputString += "Level " + Integer.toString(i+1) + ": " + Double.toString(grades[i] / gradeCounts[i]) + "%\n";
		}
		
		outputString += "Degree: " + calcDegree(regNumber);	
		
		closeConnection();
		return outputString;	
	}
	
	//for testing
	public static void main(String[] arg) throws SQLException {
		System.out.println(getStudentStatus(987654321));
	}
			
}
