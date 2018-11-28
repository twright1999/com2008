package Database;
import Accounts.*;
import Utility.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

		public static void addStudent(int regNumber, String email, String tutor, int userID) throws SQLException {
			openConnection();
			String query = "INSERT INTO Student SET regNumber = ?, email = ?, tutor = ?, userID = (SELECT userID FROM Account WHERE userID = ?)";
			PreparedStatement pstm = connection.prepareStatement(query);
			pstm.setInt(1, regNumber);
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
					"DELETE FROM Student WHERE userID = ?");
			pstm.setInt(1, userID);
			pstm.executeUpdate();
			closeConnection();
			return;

		}
		
		public static void addModule(int regNumber, String modID) throws SQLException {
			openConnection();
			String query = "INSERT INTO Student_Module SET regNumber = (SELECT regNumber FROM Student WHERE regNumber = ?), modID = (SELECT modID FROM Module WHERE modID = ?)";
			PreparedStatement pstm = connection.prepareStatement(query);
			pstm.setInt(1, regNumber);
			pstm.setString(2, modID);
			pstm.executeUpdate();
			closeConnection();
			return;
		}
		
		public static void dropModule(int regNumber, String modID) throws SQLException {
			openConnection();
			PreparedStatement pstm = connection.prepareStatement(
					"DELETE FROM Student_Module WHERE regNumber = ? AND modID = ?");
			pstm.setInt(1, regNumber);
			pstm.setString(2, modID);
			pstm.executeUpdate();
			closeConnection();
			return;
			
		}
		
		public static Boolean checkRegistered(int userID) throws SQLException {
			openConnection();
			PreparedStatement pstm = connection.prepareStatement(
					"SELECT regNumber FROM Student WHERE userID = ?");
			pstm.setInt(1, userID);
			pstm.executeUpdate();
			ResultSet res = pstm.executeQuery();
			Boolean x = !res.wasNull();
			closeConnection();
			return x;
			
		}
		
		public static boolean checkCredits(int regNumber, int periodID) throws SQLException {
			openConnection();
			Statement stmt = connection.createStatement();
			
			ResultSet periodQuery = stmt.executeQuery("SELECT * FROM PeriodOfStudy WHERE periodID = " + periodID);
			
			periodQuery.next();
			String level = periodQuery.getString("level");
			
			ResultSet moduleQuery = stmt.executeQuery("SELECT Module.credits, Module.degID FROM Module INNER JOIN Student_Module "+
					"ON Module.modID = Student_Module.modID WHERE Module.level = " + level + " && regNumber = " + regNumber);
			
			float creditsTotal = 0;
			String degID = "    ";
			while (moduleQuery.next()) {
				creditsTotal += moduleQuery.getFloat("credits");
				degID = moduleQuery.getString("degID");
			}
			
			closeConnection();
			System.out.println(degID.charAt(3));
			System.out.println(creditsTotal);
			if (degID.charAt(3) == 'U' && creditsTotal == 120.0 || degID.charAt(3) == 'P' && creditsTotal == 180.0) {
				return true;
			}
			else {
				return false;
			}
		}
		
		//for testing
		public static void main(String[] arg) throws SQLException {
			DACRegistrar.dropModule(69420, "BAD69");
			
		}
		

}
