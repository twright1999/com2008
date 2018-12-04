package Accounts;
import java.sql.SQLException;
import Database.DACTeacher;

public class Teacher extends Account {

	public Teacher(int userID, String name, String password, char permission) {
		super(userID, name, password, permission);
	}
	
	private void addGrade(float initialGrade, float resitGrade, String modID, int regNumber) throws SQLException {
		DACTeacher.addGrade(initialGrade, resitGrade, modID, regNumber);
	}
	
	private void removeGrade(int gradeID) throws SQLException {
		DACTeacher.removeGrade(gradeID);
	}
	
	private boolean calcPeriod(int regNumber, int periodID) throws SQLException {
		return DACTeacher.calcPeriod(regNumber, periodID);	
	}
	
	private String calcDegree(int regNumber) throws SQLException {
		return DACTeacher.calcDegree(regNumber);
	}
	
	private String getStudentStatus(int regNumber) throws SQLException {
		return DACTeacher.getStudentStatus(regNumber);
	}
}