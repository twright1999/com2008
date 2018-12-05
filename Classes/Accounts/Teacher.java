package Accounts;
import java.sql.SQLException;
import java.util.List;

import Database.DACTeacher;

public class Teacher extends Account {

	public Teacher(int userID, String name, String password, char permission) {
		super(userID, name, password, permission);
	}
	
	private void addInitialGrade(float initialGrade,String modID, String periodID) throws SQLException {
		DACTeacher.addInitialGrade(initialGrade, modID, periodID);
	}
	
	private void removeGrade(int gradeID) throws SQLException {
		DACTeacher.removeGrade(gradeID);
	}
	
	private boolean calcPeriod(int regNumber, String periodID) throws SQLException {
		return DACTeacher.calcPeriod(regNumber, periodID);	
	}
	
	private String calcDegree(int regNumber) throws SQLException {
		return DACTeacher.calcDegree(regNumber);
	}
	
	private List<List<String>> getStudentStatus(int regNumber) throws SQLException {
		return DACTeacher.getStudentStatus(regNumber);
	}
	
	private String advanceStudent(int regNumber, String periodID, String startDate, String endDate) throws SQLException {
		return DACTeacher.advanceStudent(regNumber, periodID, startDate, endDate);
	}
}