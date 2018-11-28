package Database;
import Accounts.*;
import Utility.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class DACRegistrar {
	
	private static Connection connection;

	private static void openConnection() throws SQLException {
		
		try {
			System.out.println("try openning conenction");
			//if (connection == null) {
				connection = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team020", "team020", "aa429b86");
				System.out.println("Connection value is assigned");
			//}
			//if connection is already opened, do nothing
		}
		catch (NullPointerException nex) {
			System.out.println("connection is null");
			closeConnection();
		}
		catch (SQLException ex) {
			System.out.println("catch openConn: " + ex.toString());
			closeConnection();
		}
		
	}

	private static void closeConnection() throws SQLException {
		connection.close();
		System.out.println("Conenction is closed");
	}

		public static void addStudent(String regNumber, String email, String tutor, int userID) throws SQLException {
			openConnection();
			String query = "INSERT INTO Student (regNumber, email, tutor, userID)"
			        + " values (?, ?, ?, ?)";
			PreparedStatement pstm = connection.prepareStatement(query);
			pstm.setString(1, regNumber);
			pstm.setString(2, email);
			pstm.setString(3, tutor);
			pstm.setInt(4, userID);
			pstm.executeUpdate();
			closeConnection();
			return;

		}
		
		public static void removeStudent(int userID) throws SQLException {
			openConnection();
			PreparedStatement pstm = connection.prepareStatement(
					"DELETE * FROM Student WHERE userID = ?");
			pstm.setInt(1, userID);
			pstm.executeUpdate();
			closeConnection();
			return;

		}
		
		public static void addModule(String regNumber, String modID) throws SQLException {
			openConnection();
			String query = "INSERT INTO Student_Module (regNumber, modID)"
			        + " values (?, ?)";
			PreparedStatement pstm = connection.prepareStatement(query);
			pstm.executeUpdate();
			closeConnection();
			return;
		}
		
		public static void dropModule(String regNumber, String modID) throws SQLException {
			openConnection();
			PreparedStatement pstm = connection.prepareStatement(
					"DELETE * FROM Student_Module WHERE regNumber = ? AND modID = ?");
			pstm.setString(1, regNumber);
			pstm.setString(2, modID);
			pstm.executeUpdate();
			closeConnection();
			return;
			
		}
		

}