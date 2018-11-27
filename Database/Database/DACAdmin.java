package Database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Accounts.*;
import dataConversion.QueryToObject;

public final class DACAdmin {

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

	public static void addAccount(int userID, String name, String password, char permission) throws SQLException {
		openConnection();
		String query = "INSERT INTO Account (userID, name, password, permission)"
		        + " values (?, ?, ?, ?)";
		PreparedStatement pstm = connection.prepareStatement(query);
		pstm.setInt(1, userID);
		pstm.setString(2, name);
		pstm.setString(3, password);
		pstm.setString(4, String.valueOf(permission));
		pstm.executeUpdate();
		closeConnection();
		return;

	}
	
	public static void removeAccount(int userID) throws SQLException {
		openConnection();
		PreparedStatement pstm = connection.prepareStatement(
				"DELETE * FROM Account WHERE userID = ?");
		pstm.setInt(1, userID);
		pstm.executeUpdate();
		closeConnection();
		return;

	}
	
	public static void setPermission(int userID, char permission) throws SQLException {
		openConnection();
		String query = "UPDATE Account SET permission="+permission+" WHERE userID="+userID;
		PreparedStatement pstm = connection.prepareStatement(query);
		pstm.executeUpdate();
		closeConnection();
		return;
	}
	
	public static void setUserID(int oldUserID, int newUserID) throws SQLException {
		openConnection();
		String query = "UPDATE Account SET userID="+newUserID+" WHERE userID="+oldUserID;
		PreparedStatement pstm = connection.prepareStatement(query);
		pstm.executeUpdate();
		closeConnection();
		return;
	}
	
	public static void setPassword(int userID, String password) throws SQLException {
		openConnection();
		String query = "UPDATE Account SET password="+password+" WHERE userID="+userID;
		PreparedStatement pstm = connection.prepareStatement(query);
		pstm.executeUpdate();
		closeConnection();
		return;
	}
	
	public static void setUsername(int userID, String name) throws SQLException {
		openConnection();
		String query = "UPDATE Account SET name="+name+" WHERE userID="+userID;
		PreparedStatement pstm = connection.prepareStatement(query);
		pstm.executeUpdate();
		closeConnection();
		return;
	}
	
	public static void addDepartment(String depID, String name) throws SQLException {
		openConnection();
		String query = "INSERT INTO Department (depID, name)"
		        + " values (?, ?)";
		PreparedStatement pstm = connection.prepareStatement(query);
		pstm.setString(1, depID);
		pstm.setString(2, name);
		pstm.executeUpdate();
		closeConnection();
		return;

	}

	public static void removeDepartment(String depID) throws SQLException {
		openConnection();
		PreparedStatement pstm = connection.prepareStatement(
				"DELETE * FROM Department WHERE depID = ?");
		pstm.setString(1, depID);
		pstm.executeUpdate();
		closeConnection();
		return;
		
	}
	
	public static void addDegree(String degID, String name, String level, String depID) throws SQLException {
		openConnection();
		String query = "INSERT INTO Department (depID, name)"
		        + " values (?, ?)";
		PreparedStatement pstm = connection.prepareStatement(query);
		pstm.setString(1, degID);
		pstm.setString(2, name);
		pstm.setString(3, level);
		pstm.setString(4, depID);
		pstm.executeUpdate();
		closeConnection();
		return;

	}
	
	public static void removeDegree(String degID) throws SQLException {
		openConnection();
		PreparedStatement pstm = connection.prepareStatement(
				"DELETE * FROM Degree WHERE degID = ?");
		pstm.setString(1, degID);
		pstm.executeUpdate();
		closeConnection();
		return;
		
	}
	
	public static void addModule(String modID, String name, int credits, String taught, String obligatory, String degCode) throws SQLException {
		openConnection();
		String query = "INSERT INTO Department (modID, name, credits, taught, obligatory, degCode)"
		        + " values (?, ?, ?, ?, ?, ?)";
		PreparedStatement pstm = connection.prepareStatement(query);
		pstm.setString(1, modID);
		pstm.setString(2, name);
		pstm.setInt(3, credits );
		pstm.setString(4, taught);
		pstm.setString(5, obligatory);
		pstm.setString(6, degCode);
		pstm.executeUpdate();
		closeConnection();
		return;

	}
	
	public static void removeModule(String modID) throws SQLException {
		openConnection();
		PreparedStatement pstm = connection.prepareStatement(
				"DELETE * FROM Module WHERE modID = ?");
		pstm.setString(1, modID);
		pstm.executeUpdate();
		closeConnection();
		return;
	}
	
	
	//for testing
	public static void main(String[] arg) throws SQLException {
		
	}


}
