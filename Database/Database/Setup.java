package Database;
import java.sql.*;
import Accounts.*;
public final class Setup{
	public static void main(String[] arg) throws SQLException {
		Statement stmt = null;
		try (Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team020", "team020", "aa429b86")) {
			stmt = con.createStatement();
			
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Account("+
					   "userID int (8) NOT NULL PRIMARY KEY UNIQUE,"+
					   "name varchar (50) NOT NULL,"+
					   "password text NOT NULL,"+
					   "permission char NOT NULL)");
			
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Student("+
					   "regNumber int (9) NOT NULL PRIMARY KEY UNIQUE,"+
					   "email varchar (50) NOT NULL,"+
					   "tutor varchar (50) NOT NULL,"+
					   "userID int (8) NOT NULL UNIQUE,"+
					   "FOREIGN KEY (userID) references Account(userID) on delete cascade)");
			
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Department("+
					   "depID varchar (3) NOT NULL PRIMARY KEY UNIQUE,"+
					   "name varchar (50) NOT NULL)");
		
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Degree("+
					   "degID varchar (6) NOT NULL PRIMARY KEY UNIQUE,"+
					   "name varchar (50) NOT NULL,"+
					   "level char NOT NULL,"+
					   "depID varchar (3) NOT NULL UNIQUE,"+
					   "FOREIGN KEY (depID) references Department(depID) on delete cascade)");

			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Module("+
					   "modID varchar (7) NOT NULL PRIMARY KEY UNIQUE,"+
					   "name varchar (50) NOT NULL,"+
					   "credits int NOT NULL,"+
					   "taught varchar (20) NOT NULL,"+
					   "obligatory bit NOT NULL,"+
					   "degID varchar (6) NOT NULL UNIQUE,"+
					   "FOREIGN KEY (degID) references Degree(degID) on delete cascade)");
			
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Student_Module("+
					   "regNumber int (9) NOT NULL UNIQUE,"+
					   "modID varchar (7) NOT NULL UNIQUE,"+
					   "FOREIGN KEY (regNumber) references Student(regNumber) on delete cascade,"+
					   "FOREIGN KEY (modID) references Module(modID) on delete cascade)");
			
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Teacher("+
					   "teacherID int (8) NOT NULL PRIMARY KEY UNIQUE,"+
					   "depID varchar (3) NOT NULL UNIQUE,"+
					   "userID int (8) NOT NULL UNIQUE,"+
					   "FOREIGN KEY (depID) references Department(depID) on delete cascade,"+
					   "FOREIGN KEY (userID) references Account(userID) on delete cascade)");
			
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS PeriodOfStudy("+
					   "periodID int (8) NOT NULL PRIMARY KEY UNIQUE,"+
					   "label char NOT NULL,"+
					   "startDate date NOT NULL,"+
					   "endDate date NOT NULL,"+
					   "level char NOT NULL,"+
					   "regNumber int (9) NOT NULL UNIQUE,"+
					   "FOREIGN KEY (regNumber) references Student(regNumber) on delete cascade)");
			
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Grade("+
					   "gradeID int (8) NOT NULL PRIMARY KEY UNIQUE,"+
					   "gradePercent float NOT NULL,"+
					   "modID varchar (7) NOT NULL UNIQUE,"+
					   "regNumber int (9) NOT NULL UNIQUE,"+
					   "FOREIGN KEY (modID) references Module(modID) on delete cascade,"+
					   "FOREIGN KEY (regNumber) references Student(regNumber) on delete cascade)");
			

				
			stmt.close();
		}
		catch (SQLException ex) {
			System.out.println("-------Exception-------");
			stmt.close();
			ex.printStackTrace();
		}
	}
}