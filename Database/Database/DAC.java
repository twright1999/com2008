package Database;
import Accounts.*;
import dataConversion.QueryToObject;

import java.sql.*;

/**
 * 
 * @author Rokas
 * Main DataAccessController
 */
public class DAC {
	private Connection connection;
	
	private void openConnection() throws SQLException {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team020", "team020", "aa429b86");
		}
		catch (SQLException ex) {
			closeConnection();
		}
	}
	
	private void closeConnection() throws SQLException {
		connection.close();
	}
	
	public Student getStudent(String accountId) throws SQLException {
		openConnection();
		PreparedStatement pstm = connection.prepareStatement(
				"SELECT * FROM Student WHERE accountId = ?");
		pstm.setString(1, accountId);
		ResultSet res = pstm.executeQuery();
		Student student = QueryToObject.rowToStudent(res);
		closeConnection();
		return student;
		
		
	}
	
	
	
	//for testing
	public static void main(String[] arg) {
		
	}
}
