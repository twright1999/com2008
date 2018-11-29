package Database;
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
	
	public static void nextPeriod() {
		
	}
	
	//for testing
	public static void main(String[] arg) throws SQLException {
		System.out.println(calcPeriod(987654321,4));
	}
			
}
