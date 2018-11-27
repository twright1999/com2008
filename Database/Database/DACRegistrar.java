package Database;
import Accounts.*;
import Utility.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DACRegistrar {
	
	private Connection connection;

	public DACRegistrar() throws SQLException {
		connection = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team020", "team020", "aa429b86");
	}

		private void openConnection() throws SQLException {
			try(Connection connection = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team020", "team020", "aa429b86")) {
				
			}
			catch (SQLException ex) {
				closeConnection();
			}
		}

		private void closeConnection() throws SQLException {
			connection.close();
		}

		public void addStudent(String regNumber, String email, String tutor, String userID) throws SQLException {
			String query = "INSERT INTO Student (regNumber, email, tutor, userID)"
			        + " values (?, ?, ?, ?)";
			PreparedStatement pstm = connection.prepareStatement(query);
			pstm.setString(1, regNumber);
			pstm.setString(2, email);
			pstm.setString(3, tutor);
			pstm.setString(4, userID);
			pstm.executeUpdate();
			closeConnection();
			return;

		}
		
		public void removeStudent(String userID) throws SQLException {
			openConnection();
			PreparedStatement pstm = connection.prepareStatement(
					"DELETE * FROM Account WHERE userID = ?");
			pstm.setString(1, userID);
			pstm.executeUpdate();
			closeConnection();
			return;

		}
		
		public void addModule(String regNumber, String modID) throws SQLException {
			String query = "INSERT INTO Student_Module (regNumber, modID)"
			        + " values (?, ?)";
			PreparedStatement pstm = connection.prepareStatement(query);
			pstm.executeUpdate();
			closeConnection();
			return;
		}
		
		public void dropModule(String regNumber, String modID) throws SQLException {
			PreparedStatement pstm = connection.prepareStatement(
					"DELETE * FROM Student_Module WHERE regNumber = ? AND modID = ?");
			pstm.setString(1, regNumber);
			pstm.setString(2, modID);
			pstm.executeUpdate();
			closeConnection();
			return;
			
		}
		

}
