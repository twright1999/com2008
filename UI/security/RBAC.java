package security;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Database.DAC;
import Database.DACAdmin;
/**
 * 
 * @author Rokas
 * Role-Based Access Controller. For determining appropriate user access
 */
public class RBAC extends DAC {
	
	/**
	 * 
	 * @param userID
	 * @param password
	 * @return Bool or null  True if userID and password match ones in database
	 * 						 False if they do not much or such user does not exsist
	 * 						 Null if SQLException has occured
	 * @throws SQLException
	 */
	public static Boolean verifyLogin(int userID, String password) throws SQLException {
		try{
			openConnection();
			PreparedStatement stmt = connection.prepareStatement(
					"SELECT userID, password FROM Account WHERE userID = ? AND password = ? LIMIT 1");
			stmt.setInt(1, userID);
			String hashed = DACAdmin.hashPassword(password);
			stmt.setString(2, hashed);
			ResultSet res = stmt.executeQuery();
			if (res.next()) return true;
			else return false;
		}
		catch(SQLException ex) {
			System.out.println("In verifyLogin");
			System.out.println(ex.toString());
			//for error screen message in GUI "cannot connect to DB/ error in DB syntax "
			return null;
		}
		finally {
			closeConnection();
		}
	}
	
	//testing
	public static void main(String[] arg) throws SQLException {
		System.out.println(verifyLogin(17, "password"));
		System.out.println(verifyLogin(17, "not password"));
	}
	
}


