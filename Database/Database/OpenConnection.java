package Database;
import java.sql.*;
import Accounts.*;
public class OpenConnection {
	public static void main(String[] arg) throws SQLException {
		Statement stmt = null;
		try (Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team020", "team020", "aa429b86")) {
			stmt = con.createStatement();

// code thats been executed, don't run again
// this class can be used to create tables
			/*
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Account("+
							   "accountID INT (6) NOT NULL PRIMARY KEY UNIQUE,"+
							   "name text NOT NULL,"+
							   "password text NOT NULL)");*/
			//stmt.executeUpdate("INSERT INTO Account VALUES (122, 'John', '123')");
			//stmt.executeQuery("SELECT * FROM Account WHERE accountID = 122");
			/*
			PreparedStatement values = 
					con.prepareStatement("SELECT name FROM Account WHERE accountID "
							+ "=?");
			values.setString(1, "122");*/
			
			ResultSet res = stmt.executeQuery("SELECT * FROM Account WHERE accountID = 122");
			while (res.next()) {
				String name = res.getString("name");
				System.out.print("RESULT NAME: " + name);
				String accountID = res.getString("accountID");
				System.out.print(" accID: " + accountID);
				String password = res.getString("password");
				System.out.println(" pass: " + password);
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