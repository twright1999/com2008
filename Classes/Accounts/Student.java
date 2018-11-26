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
	
	/*
	public String generateEmail() throws SQLException {
		try {
			
		}
		String stName = this.getName();
		//getting initial character of the forename and full surname
		String initials = stName.substring(0,1) + stName.substring(stName.lastIndexOf(" "));
		//Check if a student with exact same initials exist
		Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team020", "team020", "aa429b86");
		Statement stmt = con.createStatement() ;
		Statement s=con.createStatement();
		
		
		con.close();
		return em;
	}
	*/
}
