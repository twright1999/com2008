package Database;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
* DACTeacher.java
*
* Data Access Controller for Teacher
*
* @author Team 20
*/

public class DACRegistrar extends DAC {
		/**
		 * Adds student to the Student table. Not linked to PeriodOfStudy
		 * or any of the modules yet, because the same student may advance
		 * to different years and there is no need to "re add" the same person
		 * every year
		 * @param tutor
		 * @param degID
		 * @param userID
		 * @throws SQLException
		 */
		public static void addStudent(String tutor, String degID, int userID) throws SQLException {
			openConnection();
			//check if account for this Student exists
			PreparedStatement pstmt0 = connection.prepareStatement(
					"SELECT * FROM Account WHERE userID = ? AND permission = 'S' LIMIT 1");
			pstmt0.setInt(1, userID);
			ResultSet acc = pstmt0.executeQuery();
			//if there is such account, then add student
			if (acc.next()) {
				String name = acc.getString("name");
				
				//add student to Student table
				String query = "INSERT INTO Student SET regNumber = ?, email = ?, tutor = ?, degID = ?, userID = ?";
				PreparedStatement pstm = connection.prepareStatement(query);
				
				pstm.setInt(1, 0);
				pstm.setString(2, DAC.generateEmail(name));
				pstm.setString(3, tutor);
				pstm.setString(4, degID);
				pstm.setInt(5, userID);
				pstm.executeUpdate();				
			}
			closeConnection();
		}
			
		
		/**
		* removeStudent
		* 
		* takes a regNumber and removes the student from the database
		* cascade allows all linked tables instances to be deleted too
		* 
		* @param regNumber unique identifier for a student
		*/
		
		public static void removeStudent(int regNumber) throws SQLException {
			openConnection();
			PreparedStatement pstm = connection.prepareStatement(
					"DELETE FROM Student WHERE regNumber = ?");
			pstm.setInt(1, regNumber);
			pstm.executeUpdate();
			closeConnection();
			return;

		}
		
		/**
		* addModule
		* 
		* takes a regNumber and a module id and creates a new record in Student_Module
		* Used when a student chooses non obligatory module
		* 
		* @param regNumber unique identifier for a student
		* @param modID unique identifier for a module
		*/

		public static void addStudentModule(int regNumber, String modID) throws SQLException {
			openConnection();
			String query = "INSERT INTO Student_Module SET regNumber = ?, modID = ?";
			PreparedStatement pstm = connection.prepareStatement(query);
			pstm.setInt(1, regNumber);
			pstm.setString(2, modID);
			pstm.executeUpdate();
			closeConnection();
			return;
		}
		
		/**
		* dropModule
		* 
		* takes a regNumber and a module id and removes the record in Student_Module
		* 
		* @param regNumber unique identifier for a student
		* @param modID unique identifier for a module
		*/
		
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

		/**
		 * Adds student to the PeriodOfStudy table 
		 * and auto links to obligatory modules
		 * @param label
		 * @param startDate
		 * @param endDate
		 * @param level
		 * @param regNumber
		 * @throws SQLException
		 */

		public static void registerStudent(String label, String startDate, String endDate, String level, int regNumber) throws SQLException {
			openConnection();
			String query = "INSERT INTO PeriodOfStudy SET periodID = ?, label = ?, startDate = ?, endDate = ?, level = ?, regNumber = ?";
			PreparedStatement pstm = connection.prepareStatement(query);
			String periodID = regNumber + label;
			
			pstm.setString(1, periodID);
			pstm.setString(2, label);
			pstm.setString(3, startDate);
			pstm.setString(4, endDate);
			pstm.setString(5, level);
			pstm.setInt(6, regNumber);
			pstm.executeUpdate();
			
			Statement stmt = connection.createStatement();
			
			ResultSet studentQuery = stmt.executeQuery("SELECT degID FROM Student WHERE regNumber = " + regNumber);
			
			studentQuery.next();
			String degID = studentQuery.getString("degID");
			
			ResultSet moduleQuery = stmt.executeQuery("SELECT modID FROM Module WHERE degID = '" + degID + "' AND level = " + level + " AND obligatory = true");
			
			while(moduleQuery.next()) {
				query = "INSERT INTO Student_Module SET regNumber = ?, modID = ?";
				pstm = connection.prepareStatement(query);
				pstm.setInt(1, regNumber);
				pstm.setString(2, moduleQuery.getString("modID"));
				pstm.executeUpdate();
			}
			
			closeConnection();
		}
		
		/**
		* checkRegistered
		* 
		* takes a regNumber and checks if the student is registered for the most recent period
		* check if the credits add up to the right amount and that all modules are the same level
		* 
		* @param regNumber unique identifier for a student
		* 
		* @return boolean true if the student is registered 
		*/
		
		public static Boolean checkRegistered(int regNumber) throws SQLException {
			openConnection();
			Statement stmt = connection.createStatement();
			
			ResultSet periodQuery = stmt.executeQuery("SELECT level, periodID FROM PeriodOfStudy " +
					"WHERE regNumber = " + regNumber +
					" ORDER BY startDate DESC");
			
			periodQuery.next();
			int level = periodQuery.getInt("level");
			String periodID = periodQuery.getString("periodID");
			
			ResultSet moduleQuery = stmt.executeQuery("SELECT Module.level FROM Module " +
					"INNER JOIN Student_Module ON Module.modID = Student_Module.modID " +
					"WHERE Student_Module.regNumber = " + regNumber);
			
			boolean levelMatch = true;
			while(moduleQuery.next()) {
				if (Integer.parseInt(moduleQuery.getString("level")) != level) levelMatch = false;
			}
			
			closeConnection();
			
			if (levelMatch && checkCredits(regNumber, periodID)) return true;
			else return false;
	
		}
		
		/**
		* checkCredits
		* 
		* takes a regNumber and a periodID and checks if the student has the right amount of credits 
		* 
		* @param regNumber unique identifier for a student
		* @param periodID unique identifier for a period of study
		* 
		* @return boolean true if the student has the correct amount of credits selected
		*/
		
		public static boolean checkCredits(int regNumber, String periodID) throws SQLException {
			openConnection();
			Statement stmt = connection.createStatement();
			
			ResultSet periodQuery = stmt.executeQuery("SELECT * FROM PeriodOfStudy WHERE periodID = '" + periodID + "'");
			
			periodQuery.next();
			String level = periodQuery.getString("level");
			
			ResultSet moduleQuery = stmt.executeQuery("SELECT Module.credits, Degree.name FROM Module INNER JOIN Student_Module "+
					"ON Module.modID = Student_Module.modID " +
					"INNER JOIN Degree ON Module.degID = Degree.degID " +
					"WHERE Module.level = " + level + " && regNumber = " + regNumber);
			
			float creditsTotal = 0;
			String name = "    ";
			while (moduleQuery.next()) {
				creditsTotal += moduleQuery.getFloat("credits");
				name = moduleQuery.getString("name");
			}
			
			closeConnection();
			if (name.charAt(0) == 'B' && creditsTotal == 120.0 || name.charAt(0) == 'M' && creditsTotal == 180.0) {
				return true;
			}
			else {
				return false;
			}
		}
		
		//for testing
		public static void main(String[] arg) throws SQLException {
			//DACRegistrar.dropModule(69420, "BAD69");
			//System.out.println(DACRegistrar.checkRegistered(987654321));
			//removeStudent(999999999);
			//DAC.getAccounts();
            //addStudent("TUTOR", "COMU01", 4);
			//removeStudent(4);
            //DAC.getAllStudents();
			//registerStudent("B", "2017-06-06", "2018-12-12", "2", 1);
			//DACRegistrar.addStudentModule(1, "COM3000");
			//addStudentModule(1, "COM3004");
			//addStudentModule(1, "COM3005");
			//addStudentModule(1, "COM555");
            
//            System.out.println(checkRegistered(10));
            
		}
		

}
