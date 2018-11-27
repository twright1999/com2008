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
			
			ResultSet res = stmt.executeQuery(
					"SELECT * FROM Account WHERE userID = 1 LIMIT 1 UNION "
				   +"SELECT * FROM Student WHERE userID = 1 LIMIT 1");
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
			}
			
			//stmt.executeUpdate("DROP TABLE IF EXISTS PeriodOfStudy");

			
			/* 
			 * BASE VALUES TEST
			 * Already been executed
			 */
			
//			stmt.executeUpdate("INSERT INTO Account VALUES (0,'Mr Bob Bobson', 'password', 'S')");
//			stmt.executeUpdate("INSERT INTO Account VALUES (0,'Mr Teacher', 'password', 'T')");
//			stmt.executeUpdate("INSERT INTO Student VALUES (123456789, 'bob@sheffield.ac.uk', 'Mr Tue Toor', '00000001')");
//			stmt.executeUpdate("INSERT INTO Department VALUES ('COM', 'Computer Science')");
//			stmt.executeUpdate("INSERT INTO Degree VALUES ('COMU01', 'BSc Computer Science', '3', 'COM')");
//			stmt.executeUpdate("INSERT INTO Module VALUES ('COM2008', 'Systems Design and Security', '20', 'Autumn', True, 'COMU01')");
//			stmt.executeUpdate("INSERT INTO Student_Module VALUES (123456789, 'COM2008')");
//			stmt.executeUpdate("INSERT INTO Teacher VALUES (99999999, 'COM', 00000002)");
//			stmt.executeUpdate("INSERT INTO PeriodOfStudy VALUES (00000001, 'A', '2017-09-25', '2020-06-06', 3, 123456789)");
//			stmt.executeUpdate("INSERT INTO Grade VALUES (00000001, 70.0, 'COM2008', 123456789)");
			
			
			
			
			
			stmt.close();
		}
		catch (SQLException ex) {
			System.out.println("-------Exception-------");
			stmt.close();
			ex.printStackTrace();
		}
	}
}