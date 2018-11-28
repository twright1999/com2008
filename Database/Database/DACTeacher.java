package Database;
import Accounts.*;
import Utility.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

public final class DACTeacher {
	
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

		public static void addGrade(float gradePercent, String modID, int regNumber) throws SQLException {
			openConnection();
			String query = "INSERT INTO Grade (gradeID, gradePercent, modID, regNumber)" 
					+ " values (?, ?, ?, ?)";
			PreparedStatement pstm = connection.prepareStatement(query);
			pstm.setInt(1, 0);
			pstm.setFloat(2, gradePercent);
			pstm.setString(3, modID);
			pstm.setInt(4, regNumber);
			pstm.executeUpdate();	
			closeConnection();
		}
		
		public static void removeGrade(int gradeID) throws SQLException {
			openConnection();
			PreparedStatement pstm = connection.prepareStatement(
					"DELETE * FROM Grade WHERE gradeID = ?");
			pstm.setInt(1, gradeID);
			pstm.executeUpdate();
			closeConnection();

		}
		
		public static boolean calcPeriod(int regNumber, int periodID) throws SQLException {
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
		
		public static void main(String[] arg) throws SQLException {
			System.out.println(calcPeriod(987654321,4));
		}
			
		

}
