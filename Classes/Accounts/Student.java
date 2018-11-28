package Accounts;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import Database.DAC;
import Utility.Degree;
import Utility.PeriodOfStudy;

public class Student extends Account {
	
	private int regNumber;
	private Degree degree;
	private String email; //First letter of forname(s) ++ surname ++ 2 unique 2 digit integer (if the same name already exsits) ++ university address
	private String tutor;
	private PeriodOfStudy periodOfStudy;
	
	
	public Student(int userID, String name, String password, char permission,
			int regNumber, Degree degree, String tutor, PeriodOfStudy
			periodOfstudy) {
		super(userID, name, password, permission);
		this.regNumber = regNumber;
		this.degree = degree;
		this.tutor = tutor;
		this.periodOfStudy = periodOfStudy;
		//this.email = generateEmail();
	}
	/*there is an email field, because this constructor is used when Student is already
	 * created and inserted in database. 
	 */
	public Student(int userID, String name, String password, char permission,
			int regNumber, Degree degree, String tutor, PeriodOfStudy periodOfStudy,
			String email) {
		super(userID, name, password, permission);
		this.regNumber = regNumber;
		this.degree = degree;
		this.tutor = tutor;
		this.periodOfStudy = periodOfStudy;
		this.email = email;
	}
	
	/*
	 * For getting Student instance fields. Called in <GUI Name>
	 * @param userId userId for SELECT query in getStudentStatus
	 * @return Student
	 */
	public Student getStudent() throws SQLException {
		return DAC.getStudent(userID);
	}
	
	/*
	public String generateEmail() {
		String initials = name.substring(0,1) + name.substring(name.lastIndexOf(" "));
		//Check if such name already exists in the database
	}*/
	
	public String toString() {
		String all = super.toString() + degree.toString() + 
				" ; tutor: " + tutor + periodOfStudy.toString();
		return all;
	}
	
}
