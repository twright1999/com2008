package Database;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

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
		
		public static Boolean checkRegistered(int regNumber) throws SQLException {
			openConnection();
			Statement stmt = connection.createStatement();
			
			ResultSet periodQuery = stmt.executeQuery("SELECT level FROM PeriodOfStudy " +
					"WHERE regNumber = " + regNumber +
					" ORDER BY startDate DESC");
			
			periodQuery.next();
			int level = periodQuery.getInt("level");
			
			ResultSet moduleQuery = stmt.executeQuery("SELECT Module.level FROM Module " +
					"INNER JOIN Student_Module ON Module.modID = Student_Module.modID " +
					"WHERE Student_Module.regNumber = " + regNumber);
			
			boolean levelMatch = true;
			while(moduleQuery.next()) {
				if (Integer.parseInt(moduleQuery.getString("level")) != level) levelMatch = false;
			}
			
			ResultSet studentQuery = stmt.executeQuery("SELECT degID FROM Student " +
					"WHERE regNumber = " + regNumber );
			
			studentQuery.next();
			String degID = studentQuery.getString("degID");
			
			moduleQuery = stmt.executeQuery("SELECT modID from Module " +
					"WHERE obligatory = true AND degID = '" + degID + "'");
			
			ArrayList<String> modIDs = new ArrayList<String>();
			while(moduleQuery.next()) {
				modIDs.add(moduleQuery.getString("modID"));
			}
			
			ResultSet studentModuleQuery = stmt.executeQuery("SELECT modID FROM Student_Module " +
					"WHERE regNumber = " + regNumber);
			
			boolean allOb = true;
			while(studentModuleQuery.next()) {
				if (!modIDs.contains(studentModuleQuery.getString("modID"))) allOb = false;
			}
			
			moduleQuery = stmt.executeQuery("SELECT Module.credits FROM Module " +
					"INNER JOIN Student_Module ON Module.modID = Student_Module.modID " +
					"WHERE Student_Module.regNumber = " + regNumber);
			
			int creditsTotal = 0;
			while(moduleQuery.next()) {
				creditsTotal += moduleQuery.getInt("credits");
			}
			
			closeConnection();
			
			if (level < 4) {
				if (levelMatch && allOb && creditsTotal == 120) return true;
				else return false;
			} else {
				if (levelMatch && allOb && creditsTotal == 180) return true;
				else return false;
			}			
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
			System.out.println(DACRegistrar.checkRegistered(987654321));
		}
		

}
