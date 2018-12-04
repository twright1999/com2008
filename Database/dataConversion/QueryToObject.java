package dataConversion;
import Accounts.*;
import Database.DAC;
import Utility.*;
import Utility.Module;

import java.sql.*;
/**
 * 
 * @author Rokas
 * For converting query results to an Object, so that it could be used
 * in GUI display.
 * Should be static?
 */
public final class QueryToObject {
	/**
	 * used to convert SQL row in Account table to Account object (if such exists)
	 * @param res - sql result query.
	 * @return account
	 * @throws SQLException - displays a message in GUI that such account does not exist
	 */
	public static Account rowToAccount(ResultSet res) throws SQLException {
		Account account;
		try {
			res.next();
			int userID = res.getInt("userID"); 
			String name = res.getString("name");
			String password = res.getString("password");
			char permission = res.getString("permission").charAt(0);
			account = new Account (userID, name, password, permission);
			System.out.println(">>getAccount: " + account.toString() );
			return account;
		}
		catch (SQLException ex) {
			//output in GUI that such account does not exists given the query
			System.out.println("rowToAcc exception: " + ex.toString());
			System.out.println("Such account does not exsit");
		}
		//returns null if such account does not exist.
		return null;
	}
	
	public static Account[] rowsToAccounts(ResultSet res, int count) throws SQLException {
		Account[] accounts = new Account[count];
		try {
			int index = 0;
			while (res.next()) {
				int userID = res.getInt("userID");
				String name = res.getString("name");
				String password = "hidden"; //placeholder (no need to see hashed passwords)
				char permission = res.getString("permission").charAt(0);
				Account account = new Account (userID, name, password, permission);
				accounts[index] = account;
				index++;
				System.out.println(">>rowsToAccounts: " + account.toString());
			}
			return accounts;
		}
		catch (SQLException ex) {
			System.out.println("rowsToAccounts: " + ex.toString());
		}
		//if something goes wrong, show an error message in GUI
		return null;
	}
	
	public static Student rowToStudent(ResultSet resStudent, ResultSet resDegree, ResultSet resPeriod) {
		Student student;
		try {
			//get values from the main account table
			System.out.println("rowToStudent before rowToAccount");
			//Account acc = rowToAccount(resStudent);
			//then add the missing student instance variables
			resStudent.next();
			int userID = resStudent.getInt("userID"); 
			String name = resStudent.getString("name");
			String password = resStudent.getString("password");
			char permission = resStudent.getString("permission").charAt(0);
			Account acc = new Account (userID, name, password, permission);
			int regNumber = resStudent.getInt("regNumber");
			String email = resStudent.getString("email");
			String tutor = resStudent.getString("tutor");
			
			//pulling degree values from result set and creating Degree instance
			resDegree.next();
			String degID = resDegree.getString("degID");
			String nameD = resDegree.getString("name");
			char level = resDegree.getString("level").charAt(0);
			String depID = resDegree.getString("depID");
			Degree degree = new Degree(degID, nameD, level, depID);
			
			//pulling periodOfStudy values from res set and creating PeriodOfStudy instance
			resPeriod.next();
			int periodID = resPeriod.getInt("periodID");
			char label = resPeriod.getString("label").charAt(0);
			Date startDate = resPeriod.getDate("startDate");
			Date endDate = resPeriod.getDate("endDate");
			char levelP  = resPeriod.getString("level").charAt(0);
			int regNumberP = resPeriod.getInt("regNumber");
			
			PeriodOfStudy period = new PeriodOfStudy(periodID, label, startDate, endDate, levelP, regNumberP);
			
	        student = new Student(acc.getUserID(), acc.getName(), acc.getPassword(),
	        		acc.getPermission(),regNumber, degree, tutor, period, email);
	        System.out.println(">>student is created");
			return student;
		}
		catch (SQLException ex) {
			System.out.println("rowToStudent exception: " + ex.toString());
			System.out.println("Such student does not exsist");
		}
		//returns null if the student was not found 
		return null;
	}
	
	public static Degree rowToDegree(ResultSet res) throws SQLException  {
		Degree degree;
		try {
			res.next();
			String degID = res.getString("degID");
			String name = res.getString("name");
			char level = res.getString("level").charAt(0);
			String depID = res.getString("depID");
			degree = new Degree(degID, name, level, depID);
			return degree;
		}
		catch (SQLException ex) {
			System.out.println("rowToDegree exception: " + ex.toString());
			degree = null;
		}
		//Returns null if Degree was not found
		return null;
		
	}
	
	public static PeriodOfStudy rowToPeriod(ResultSet res) {
		PeriodOfStudy period;
		try {
			res.next();
			int periodID = res.getInt("periodID");
			char label = res.getString("label").charAt(0);
			Date startDate = res.getDate("startDate");
			Date endDate = res.getDate("endDate");
			char level = res.getString("level").charAt(0);
			int regNumber = res.getInt("regNumber");
			period = new PeriodOfStudy(periodID, label, startDate, endDate,
						level, regNumber);
			return period;
			
		}
		catch (SQLException ex) {
			//output in GUI that such account does not exists given the query
			System.out.println("SQLexception in rowToPeriod: " + ex.toString());
		}
		
		return null;
		
	}
	
	public static Grade[] rowsToGrades(ResultSet res, int count) throws SQLException {
		Grade[] grades= new Grade[count];
			try {
				int index = 0;
				while(res.next()) {
					int gradeID = res.getInt("gradeID");
					float gradeP = res.getFloat("gradePercent");
					String modID = res.getString("modID");
					int regNumber = res.getInt("regNumber");
					Grade grade = new Grade(gradeID, gradeP, modID, regNumber);
					grades[index] = grade;
					index++;
					System.out.println("while'ing");
				}
				return grades;
			}
				
				catch(SQLException ex) {
					System.out.println("rowsToGrades: " + ex.toString() );
				}
		
		return null;
	}
	
	/*
	 * there is no Degree and PeriodOfStudy in this type of Student
	 */
	public static Student[] rowsToStudents(ResultSet res, int count) throws SQLException {
		Student[] students= new Student[count];
		try {
			int index = 0;
			while(res.next()) {
				int userID = res.getInt("userID"); 
				String name = res.getString("name");
				String password = "hidden"; //no need to display hashed password
				char permission = res.getString("permission").charAt(0);
				Account acc = new Account (userID, name, password, permission);
				String email = res.getString("email");
				int regNumber = res.getInt("regNumber");
				
				String tutor = res.getString("tutor");
				Student student = new Student(acc.getUserID(), acc.getName(),
						acc.getPassword(), acc.getPermission(), regNumber, tutor, email);
				System.out.println(">>rowsToStudents: " + student.toStringSimple());
				students[index] = student;
				index++;
				
			}
			return students;
		}
			
			catch(SQLException ex) {
				System.out.println("rowsToStudents: " + ex.toString() );
			}
	
	return null;
	}
	
	public static Module[] rowsToModules(ResultSet res, int count) throws SQLException {
		Module[] modules = new Module[count];
		int index = 0;
		try {
		while (res.next()) {
			String moduleID = res.getString("modID");
			String name = res.getString("name");
			int credits = res.getInt("credits");
			String taught = res.getString("taught");
			Boolean obligatory = res.getBoolean("obligatory");
			String degID = res.getString("degID");
			Module module = new Module(moduleID, name, credits, taught, obligatory, degID);
			modules[index] = module;
			System.out.println("rowsToModules name: " + modules[index].getName());
			index++;
		}
		return modules;
		}
		catch (SQLException ex) {
			System.out.println("rowsToModules: " + ex.toString());
		}
		//if something goes wrong, show an error message in GUI
		return null;
	}
	
	public static Student_Module[] rowsToStudentModules(ResultSet res, int count) throws SQLException {
		Student_Module[] student_modules = new Student_Module[count];
		try {
			int index = 0;
			while (res.next()) {
				int regNumber = res.getInt("regNumber");
				String modID = res.getString("modID");
				Student_Module std_mdl = new Student_Module(regNumber, modID);
				student_modules[index] = std_mdl;
				index++;
				System.out.println("rowsToStdModules: " + std_mdl.toString());
				
			}
			return student_modules;
		}
		catch (SQLException ex) {
			System.out.println("rowsToStdModules: " + ex.toString());
		}
		//if something goes wrong, show an error message in GUI
		return null;
	}
	
	public static Degree[] rowsToDegrees(ResultSet res, int count) throws SQLException {
		Degree[] degrees = new Degree[count];
		try {
			int index = 0;
			while (res.next()) {
				String degID = res.getString("degID");
				String name = res.getString("name");
				char level = res.getString("level").charAt(0);
				String depID = res.getString("depID");
				Degree degree = new Degree(degID, name, level, depID);
				degrees[index] = degree;
				index++;
				System.out.println(degree.toString());
			}
			return degrees;
		}
			catch(SQLException ex) {
				System.out.println("rowsToDegrees: " + ex.toString());
			}
		//if something goes wrong, show an error message in GUI
		return null;
		
	}
	
	public static Department[] rowsToDepartments(ResultSet res, int count) throws SQLException {
		Department[] departments = new Department[count];
		try {
			int index = 0;
			while (res.next()) {
				String depID = res.getString("depID");
				String name = res.getString("name");
				Department department = new Department(depID, name);
				departments[index] = department;
				index++;
				System.out.println("rowsToDepartments: " + department.toString());
			}
			return departments;
		}
		catch (SQLException ex) {
			System.out.println("rowsToDepartments: " + ex.toString());
		}
		//if something goes wrong, show an error message in GUI
		return null;
	}
	
	public static void main(String[] arg) throws SQLException {
		
	}
}
