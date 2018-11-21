package Database;
import java.sql.*;
public class OpenConnection {
	public static void main(String[] arg) throws SQLException {
		Statement stmt = null;
		try (Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team020", "team020", "aa429b86")) {
			stmt = con.createStatement();

// code thats been executed, don't run again
// this class can be used to create tables
//
//			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Account("+
//							   "accountID integer NOT NULL PRIMARY KEY UNIQUE,"+
//							   "name text NOT NULL,"+
//							   "password text NOT NULL)");
			stmt.close();
		}
		catch (SQLException ex) {
			stmt.close();
			ex.printStackTrace();
		}
	}
}