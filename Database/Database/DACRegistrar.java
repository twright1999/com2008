package Database;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DACRegistrar extends DAC {
	
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
			/*
			 * check that all modules match the level
			 * AND
			 * check that the Student has core modules
			 * AND
			 * check that credits = 120
			 */
			PreparedStatement pstmt0 = connection.prepareStatement(
					"SELECT level FROM PeriodOfStudy WHERE regNumber = ?");
			pstmt0.setInt(1, userID);
			ResultSet res0 = pstmt0.executeQuery();
			res0.next();
			char studentLevel = res0.getString("level").charAt(0);
			System.out.println("student lvl: " + studentLevel);
			//need to retrieve all the levels from the Modules that the student has chosen
			PreparedStatement pstmt1 = connection.prepareStatement(
					"");
			/*
			PreparedStatement pstmt1 = connection.prepareStatement(
					" SELECT level FROM Module"
					+ " LEFT JOIN (SELECT modID FROM Student_Module WHERE regNumber = ?) "
					+ " ON Module.modID = Student_Module.modID ");
					*/
			pstmt1.setInt(1, userID);
			
			ResultSet res1 = pstmt1.executeQuery();
			
			while (res1.next()) {
				System.out.println(res1.getString("level"));
			}
			/*
			PreparedStatement pstm = connection.prepareStatement(
					"SELECT regNumber FROM Student WHERE userID = ?");
			pstm.setInt(1, userID);
			pstm.executeUpdate();
			ResultSet res = pstm.executeQuery();
			Boolean x = !res.wasNull();*/
			closeConnection();
			return true;
			
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
			//DACRegistrar.dropModule(69420, "BAD69");
			DACRegistrar.checkRegistered(987654321);
		}
		

}
