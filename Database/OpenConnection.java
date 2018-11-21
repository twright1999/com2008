package Database;
import java.sql.*;
public class OpenConnection {
	public static void main(String[] arg) throws SQLException {
		try (Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team020", "team020", "aa429b86")) {
			 // use the open connection
			 // for one or more queries
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
}