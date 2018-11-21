package Database;
import java.sql.*;
public class OpenConnection {
	public static void main(String[] arg) {
		Connection con = null; // a Connection object
		try {
			con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team020", "team020", "aa429b86");
					
				// use the open connection
				// for several queries
					
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		finally {
			if (con != null) con.close();
		}
	}
}