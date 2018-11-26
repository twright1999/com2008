package Accounts;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import Database.DAC;
import Utility.Degree;
import Utility.PeriodOfStudy;

public class Student extends Account {
	
	private String title; //Mr, Ms...
	private Degree degree;
	private String email; //First letter of forname(s) ++ surname ++ 2 unique 2 digit integer (if the same name already exsits) ++ university address
	private String tutor;
	private PeriodOfStudy periodOfStudy;
	
	
	public Student(String userID, String name, String password,
			String title, Degree degree, String tutor, PeriodOfStudy
			periodOfstudy) {
		super(userID, name, password);
		this.title = title;
		this.degree = degree;
		this.tutor = tutor;
		this.periodOfStudy = periodOfStudy;
		//this.email = generateEmail();
	}
	
	/*
	 * For getting Student instance fields. Called in <GUI Name>
	 * @param userId userId for SELECT query in getStudentStatus
	 * @return Student Instance
	 */
	public Student viewStatus(String userID) {
		return DAC.getStudent(userID);
	}
	
	
	public String generateEmail(String sname) {
		String initials = name.substring(0,1) + name.substring(name.lastIndexOf(" "));
		
		
	}
	
}
