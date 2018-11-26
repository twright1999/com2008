package Database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Accounts.*;
import dataConversion.QueryToObject;

public class DACAdmin {

private Connection connection;

public DACAdmin() throws SQLException {
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

	public void addAccount(String userID, String name, String password, char permission) throws SQLException {
		String query = "INSERT INTO Account (userID, name, password, permission)"
		        + " values (?, ?, ?, ?)";
		PreparedStatement pstm = connection.prepareStatement(query);
		pstm.setString(1, userID);
		pstm.setString(2, name);
		pstm.setString(3, password);
		pstm.setString(4, String.valueOf(permission));
		pstm.executeUpdate();
		closeConnection();
		return;

	}
	
	public void removeAccount(String userID) throws SQLException {
		openConnection();
		PreparedStatement pstm = connection.prepareStatement(
				"DELETE * FROM Account WHERE userID = ?");
		pstm.setString(1, userID);
		pstm.executeUpdate();
		closeConnection();
		return;

	}
	
	public void setPermission(String userID, char permission) throws SQLException {
		String query = "UPDATE Account SET permission="+permission+" WHERE userID="+userID;
		PreparedStatement pstm = connection.prepareStatement(query);
		pstm.executeUpdate();
		closeConnection();
		return;
	}
	
	public void setUserID(String oldUserID, String newUserID) throws SQLException {
		String query = "UPDATE Account SET userID="+newUserID+" WHERE userID="+oldUserID;
		PreparedStatement pstm = connection.prepareStatement(query);
		pstm.executeUpdate();
		closeConnection();
		return;
	}
	
	public void setPassword(String userID, String password) throws SQLException {
		String query = "UPDATE Account SET password="+password+" WHERE userID="+userID;
		PreparedStatement pstm = connection.prepareStatement(query);
		pstm.executeUpdate();
		closeConnection();
		return;
	}
	
	public void setUsername(String userID, String name) throws SQLException {
		String query = "UPDATE Account SET name="+name+" WHERE userID="+userID;
		PreparedStatement pstm = connection.prepareStatement(query);
		pstm.executeUpdate();
		closeConnection();
		return;
	}
	
	public void addDepartment(String depID, String name) throws SQLException {
		String query = "INSERT INTO Department (depID, name)"
		        + " values (?, ?)";
		PreparedStatement pstm = connection.prepareStatement(query);
		pstm.setString(1, depID);
		pstm.setString(2, name);
		pstm.executeUpdate();
		closeConnection();
		return;

	}

	public void removeDepartment(String depID) throws SQLException {
		openConnection();
		PreparedStatement pstm = connection.prepareStatement(
				"DELETE * FROM Department WHERE depID = ?");
		pstm.setString(1, depID);
		pstm.executeUpdate();
		closeConnection();
		return;
		
	}
	
	public void addDegree(String degID, String name, String level, String depID) throws SQLException {
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
	
	public void removeDegree(String degID) throws SQLException {
		openConnection();
		PreparedStatement pstm = connection.prepareStatement(
				"DELETE * FROM Degree WHERE degID = ?");
		pstm.setString(1, degID);
		pstm.executeUpdate();
		closeConnection();
		return;
		
	}
	
	public void addModule(String modID, String name, int credits, String period, String obligatory, String degCode) throws SQLException {
		String query = "INSERT INTO Department (modID, name, credits, period, obligatory, degCode)"
		        + " values (?, ?, ?, ?, ?, ?)";
		PreparedStatement pstm = connection.prepareStatement(query);
		pstm.setString(1, modID);
		pstm.setString(2, name);
		pstm.setInt(3, credits );
		pstm.setString(4, period);
		pstm.setString(5, obligatory);
		pstm.setString(6, degCode);
		pstm.executeUpdate();
		closeConnection();
		return;

	}
	
	public void removeModule(String modID) throws SQLException {
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
		
		DACAdmin admin = new DACAdmin();
		admin.addAccount("222", "John Smith", "123", 's');
	}


}
