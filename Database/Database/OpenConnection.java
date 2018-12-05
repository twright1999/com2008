package Database;
import java.sql.*;
import Accounts.*;
public class OpenConnection {
	public static void main(String[] arg) throws SQLException {
		Statement stmt = null;
		try (Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team020", "team020", "aa429b86")) {
			stmt = con.createStatement();

			//stmt.executeUpdate("INSERT INTO Account VALUES (122, 'John', '123')");
			//stmt.executeQuery("SELECT * FROM Account WHERE accountID = 122");
			/*
			PreparedStatement values = 
					con.prepareStatement("SELECT name FROM Account WHERE accountID "
							+ "=?");
			values.setString(1, "122");*/
			
//			stmt.executeUpdate("INSERT INTO Account VALUES (1, 'Tom', '123', 'S')");
//		    stmt.executeUpdate("INSERT INTO Student VALUES (123456789, 'twright6@sheffield.ac.uk', 'richard', '1')");

			/*
			ResultSet res = stmt.executeQuery(
					"SELECT * FROM Account WHERE userID = 1 LIMIT 1 UNION "
				   +"SELECT * FROM Student WHERE userID = 1 LIMIT 1");*/
			/*
			while (res.next()) {
				String name = res.getString("name");
				System.out.print("RESULT NAME: " + name);
				String userID = res.getString("userID");
				System.out.print(" accID: " + userID);
				String password = res.getString("password");
				System.out.print(" pass: " + password);
				String permission = res.getString("permission");
				System.out.println(" permission: " + permission);
				int regNumber = res.getInt("regNumber");
				System.out.println(" regNumber: " + regNumber);
			}*/
			/*
			ResultSet resD = stmt.executeQuery("SELECT * FROM Degree WHERE depID = 'COM'");
			resD.next();
			String nameD = resD.getString("name");
			System.out.println("nameDDD: " + nameD);*/
			//stmt.executeUpdate("DROP TABLE IF EXISTS PeriodOfStudy");


					
//			ResultSet res = stmt.executeQuery("SELECT * FROM Account");
//			while (res.next()) {
//				String name = res.getString("name");
//				System.out.print("RESULT name: " + name);
//				String userID = res.getString("userID");
//				System.out.print(" userID: " + userID);
//				String password = res.getString("password");
//				System.out.print(" pass: " + password);
//				String permission = res.getString("permission");
//				System.out.println(" permission: " + permission);
//			}
////		
//			ResultSet res2 = stmt.executeQuery("SELECT * FROM Student");
//			while (res2.next()) {
//				int regNumber = res2.getInt("regNumber");
//				System.out.print("RESULT regNumber: " + regNumber);
//				String email = res2.getString("email");
//				System.out.print(" email: " + email);
//				String tutor = res2.getString("tutor");
//				System.out.print(" tutor: " + tutor);
//				int userID = res2.getInt("userID");
//				System.out.println(" userID: " + userID);
//			}
			
//			ResultSet res3 = stmt.executeQuery("SELECT * FROM PeriodOfStudy");
//			while (res3.next()) {
//				String id  = res3.getString("periodID");
//				System.out.print("RESULT id: " + id);
//			}
			
//			ResultSet res4 = stmt.executeQuery("SELECT * FROM Degree");
//			while (res4.next()) {
//				String id  = res4.getString("degID");
//				System.out.print("RESULT id: " + id);
//			}
			
			ResultSet res5 = stmt.executeQuery("SELECT * FROM Grade");
			while (res5.next()) {
				int gradeID  = res5.getInt("gradeID");
				System.out.print("RESULT gradeID: " + gradeID);
				Double percent = res5.getDouble("initialGrade");
				System.out.print(" gradePercent: " + percent);
				String modID  = res5.getString("modID");
				System.out.print(" modID: " + modID);
				String periodID  = res5.getString("periodID");
				System.out.println(" periodID: " + periodID);
			}
			
//			ResultSet res6 = stmt.executeQuery("SELECT * FROM Module");
//			while (res6.next()) {
//				String modID  = res6.getString("modID");
//				System.out.print("RESULT modID: " + modID);
//				String name  = res6.getString("name");
//				System.out.print(" name: " + name);
//				int credits  = res6.getInt("credits");
//				System.out.print(" credits: " + credits);
//				String taught  = res6.getString("taught");
//				System.out.print(" taught: " + taught);
//				boolean obligatory  = res6.getBoolean("obligatory");
//				System.out.print(" obligatory: " + obligatory);
//				String level  = res6.getString("level");
//				System.out.print(" level: " + level);
//				String degID  = res6.getString("degID");
//				System.out.println(" degID: " + degID);
//			}
			
//			ResultSet res7 = stmt.executeQuery("SELECT * FROM Student_Module");
//			while (res7.next()) {
//				String regNumber  = res7.getString("regNumber");
//				System.out.print("RESULT regNumber: " + regNumber);
//				String modID  = res7.getString("modID");
//				System.out.println(" modID: " + modID);
//			}
				
	
//			stmt.executeUpdate("DELETE FROM Account");
//			stmt.executeUpdate("DELETE FROM Student");
//			stmt.executeUpdate("DELETE FROM Teacher");
//			stmt.executeUpdate("DELETE FROM Grade");
//			stmt.executeUpdate("DELETE FROM PeriodOfStudy");
//			stmt.executeUpdate("DELETE FROM Student_Module");
//			stmt.executeUpdate("DELETE FROM Module");
//			stmt.executeUpdate("DELETE FROM Department");
//			stmt.executeUpdate("DELETE FROM Degree");
			
			/* 
			 * BASE VALUES TEST
			 * Already been executed
			 */
			
			
//			stmt.executeUpdate("UPDATE PeriodOfStudy SET level = '4' WHERE regNumber = 9");
			
//			stmt.executeUpdate("INSERT INTO Account VALUES (0, 'Mr Thomas Wright', 'password', 'S')");
//			stmt.executeUpdate("INSERT INTO Account VALUES (0,'Mrs Teacher', 'password', 'T')");
//			stmt.executeUpdate("INSERT INTO Department VALUES ('COM', 'Computer Science')");
//			stmt.executeUpdate("INSERT INTO Degree VALUES ('COMU01', 'BSc Computer Science', '3', 'COM')");
//			stmt.executeUpdate("INSERT INTO Student VALUES (987654321, 'twright@sheffield.ac.uk', 'Mr Tue Toor', 'COMU01', '16')");
//			stmt.executeUpdate("INSERT INTO Module VALUES ('COM2008', 'Systems Design and Security', '20', 'Autumn', True, '2', 'COMU01')");
//			stmt.executeUpdate("INSERT INTO Module VALUES ('COM2108', 'Functional Programming', '20', 'Autumn', True, '2', 'COMU01')");
//			stmt.executeUpdate("INSERT INTO Module VALUES ('COM1002', 'Java Programming', '20', 'Year', True, '1', 'COMU01')");
//			stmt.executeUpdate("INSERT INTO Module VALUES ('COM3001', 'Year 3 Stuff WOW', '20', 'Autumn', True, '3', 'COMU01')");
//			stmt.executeUpdate("INSERT INTO Module VALUES ('COM2001', 'Year 2 big module', '80', 'Autumn', True, '2', 'COMU01')");
//			stmt.executeUpdate("INSERT INTO Student_Module VALUES (987654321, 'COM2008')");
//			stmt.executeUpdate("INSERT INTO Student_Module VALUES (987654321, 'COM2108')");
//			stmt.executeUpdate("INSERT INTO Student_Module VALUES (987654321, 'COM1002')"); NO LONGER IN DATABASE
//			stmt.executeUpdate("INSERT INTO Student_Module VALUES (987654321, 'COM3001')"); NO LONGER IN DATABASE
//			stmt.executeUpdate("INSERT INTO Student_Module VALUES (987654321, 'COM2001')");
//			stmt.executeUpdate("INSERT INTO Teacher VALUES (0,'COM2108', 987654321)");
//			stmt.executeUpdate("INSERT INTO Grade VALUES (0, 70.0, 'COM2008', 987654321)");
//			stmt.executeUpdate("INSERT INTO Grade VALUES (0, 69.0, 'COM2108', 987654321)");
//			stmt.executeUpdate("INSERT INTO Grade VALUES (0, 82.0, 'COM1002', 987654321)");
//			stmt.executeUpdate("INSERT INTO Grade VALUES (0, 100.0, 'COM3001', 987654321)");
//			stmt.executeUpdate("INSERT INTO Grade VALUES (0, 88.0, 'COM2001', 987654321)");
			
//			stmt.executeUpdate("INSERT INTO Account VALUES (0, 'Mr Bob Bobson', 'password', 'S')");
//			stmt.executeUpdate("INSERT INTO Degree VALUES ('COMP01', 'MSc Computer Science', '1', 0, 'COM')");
//			stmt.executeUpdate("INSERT INTO Student VALUES (0, 'twright@sheffield.ac.uk', 'Mr Tue Toor', 'COMP01', '10')");
//			stmt.executeUpdate("INSERT INTO Module VALUES ('COM1101', 'Mod1', '20', 'Autumn', True, '1', 'COMP01'),"
//														+"('COM1102', 'Mod2', '20', 'Autumn', True, '1', 'COMP01'),"
//														+"('COM1103', 'Mod3', '20', 'Autumn', True, '1', 'COMP01'),"
//														+"('COM1104', 'Mod4', '20', 'Autumn', True, '1', 'COMP01'),"
//														+"('COM1105', 'Mod5', '20', 'Autumn', True, '1', 'COMP01'),"
//														+"('COM1106', 'Mod6', '20', 'Autumn', True, '1', 'COMP01'),"
//														+"('COM1107', 'Mod7', '20', 'Autumn', True, '1', 'COMP01'),"
//														+"('COM1108', 'Mod8', '20', 'Autumn', True, '1', 'COMP01'),"
//														+"('COM1109', 'Dissertation', '20', 'Autumn', True, '1', 'COMP01')");
//			stmt.executeUpdate("INSERT INTO Student_Module VALUES (9, 'COM1101'),"
//																+"(9, 'COM1102'),"
//																+"(9, 'COM1103'),"
//																+"(9, 'COM1104'),"
//																+"(9, 'COM1105'),"
//																+"(9, 'COM1106'),"
//																+"(9, 'COM1107'),"
//																+"(9, 'COM1108'),"
//																+"(9, 'COM1109')");
//			stmt.executeUpdate("INSERT INTO PeriodOfStudy VALUES (0, 'A', '2018-09-17', '2019-06-06', '1', 9)");
//			stmt.executeUpdate("INSERT INTO Grade VALUES (0, 80.0, -1, 'COM1101', 0),"
//													   +"(0, 92.0, -1, 'COM1102', 0),"
//													   +"(0, 71.0, -1, 'COM1103', 0),"
//													   +"(0, 88.0, -1, 'COM1104', 0),"
//													   +"(0, 76.0, -1, 'COM1105', 0),"
//													   +"(0, 59.0, -1, 'COM1106', 0),"
//													   +"(0, 77.0, -1, 'COM1107', 0),"
//													   +"(0, 98.0, -1, 'COM1108', 0),"
//													   +"(0, 40.0, -1, 'COM1109', 0)");
			
			
			
			
			
			stmt.close();
		}
		catch (SQLException ex) {
			System.out.println("-------Exception-------");
			System.out.println(ex.toString());
			stmt.close();
			ex.printStackTrace();
		}
	}
}