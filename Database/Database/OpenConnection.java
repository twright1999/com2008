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
			
			ResultSet res = stmt.executeQuery("SELECT * FROM Account WHERE userID = 1");
			while (res.next()) {
				String name = res.getString("name");
				System.out.print("RESULT NAME: " + name);
				String accountID = res.getString("userID");
				System.out.print(" accID: " + accountID);
				String password = res.getString("password");
				System.out.println(" pass: " + password);
				String permission = res.getString("permission");
				System.out.println(" permission: " + permission);
			}
			
			ResultSet res2 = stmt.executeQuery("SELECT * FROM Student WHERE userID = 1");
			while (res2.next()) {
				String regNumber = res2.getString("regNumber");
				System.out.print("RESULT regNumber: " + regNumber);
				String email = res2.getString("email");
				System.out.print(" email: " + email);
				String tutor = res2.getString("tutor");
				System.out.println(" tutor: " + tutor);
				String userID = res2.getString("userID");
				System.out.println(" userID: " + userID);
				//Account acc = new Account(accountID, name, password);
				//System.out.println(acc.getName());
			}
			
			stmt.close();
		}
		catch (SQLException ex) {
			System.out.println("-------Exception-------");
			stmt.close();
			ex.printStackTrace();
		}
	}
}