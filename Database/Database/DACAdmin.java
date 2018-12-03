package Database;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.bouncycastle.util.encoders.Hex;

public class DACAdmin extends DAC {
	/**
	 * Adds an Account to Database. userID is generated automatically (incremented).
	 * Can add Account with the same exact details, userID would be the only difference
	 * @param name 
	 * @param password
	 * @param permission
	 * @throws SQLException
	 */
	public static void addAccount(String name, String password, char permission) throws SQLException {
		openConnection();
		String query = "INSERT INTO Account (userID, name, password, permission)"
		        + " values (?, ?, ?, ?)";
		PreparedStatement pstm = connection.prepareStatement(query);
		pstm.setInt(1, 0);
		pstm.setString(2, name);
		pstm.setString(3, hashPassword(password));
		pstm.setString(4, String.valueOf(permission));
		pstm.executeUpdate();
		closeConnection();
		return;

	}
	/**
	 * removes an Account from database. If the account does not exist already,
	 * method will do nothing
	 * @param userID
	 * @throws SQLException
	 */
	public static void removeAccount(int userID) throws SQLException {
		openConnection();
		PreparedStatement pstm = connection.prepareStatement(
				"DELETE FROM Account WHERE userID = ?");
		pstm.setInt(1, userID);
		pstm.executeUpdate();
		closeConnection();
		return;

	}
	/**
	 * sets permission for provided user. 
	 * @param userID	user's ID for which the permission is changed
	 * @param permission  permission to apply to the user
	 * @throws SQLException
	 */
	public static void setPermission(int userID, char permission) throws SQLException {
		openConnection();
		String query = "UPDATE Account SET permission = ? WHERE userID= ?";
		PreparedStatement pstm = connection.prepareStatement(query);
		pstm.setString(1, String.valueOf(permission));
		pstm.setInt(2, userID);
		pstm.executeUpdate();
		closeConnection();
		return;
	}
	/**
	 * Changes the password for the user
	 * @param userID
	 * @param password
	 * @throws SQLException
	 */
	public static void setPassword(int userID, String password) throws SQLException {
		openConnection();
		String query = "UPDATE Account SET password = ? WHERE userID= ?";
		PreparedStatement pstm = connection.prepareStatement(query);
		pstm.setString(1,hashPassword(password));
		pstm.setInt(2, userID);
		pstm.executeUpdate();
		closeConnection();
		return;
	}
	/**
	 * sets the new name for the user
	 * @param userID
	 * @param name
	 * @throws SQLException
	 */
	public static void setUsername(int userID, String name) throws SQLException {
		openConnection();
		String query = "UPDATE Account SET name = ? WHERE userID= ?";
		PreparedStatement pstm = connection.prepareStatement(query);
		pstm.setString(1, name);
		pstm.setInt(2, userID);
		pstm.executeUpdate();
		closeConnection();
		return;
	}
	/**
	 * adds new department. To prevent adding department with the same ID,
	 * catch the exception in GUI and display "such department already exists"
	 * @param depID
	 * @param name
	 * @throws SQLException
	 */
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
				"DELETE FROM Department WHERE depID = ?");
		pstm.setString(1, depID);
		pstm.executeUpdate();
		closeConnection();
		return;
		
	}
		
	public static void addDegree(String degID, String name, char level, String depID) throws SQLException {
		openConnection();
		String query = "INSERT INTO Degree SET degID = ?, name = ?, level= ?, depID = ?";
		PreparedStatement pstm = connection.prepareStatement(query);
		pstm.setString(1, degID);
		pstm.setString(2, name);
		pstm.setString(3, String.valueOf(level));
		pstm.setString(4, depID);
		pstm.executeUpdate();
		closeConnection();
		return;

	}
	/**
	 * removes a Degree from database
	 * @param degID
	 * @throws SQLException
	 */
	public static void removeDegree(String degID) throws SQLException {
		openConnection();
		PreparedStatement pstm = connection.prepareStatement(
				"DELETE FROM Degree WHERE degID = ?");
		pstm.setString(1, degID);
		pstm.executeUpdate();
		closeConnection();
		return;
		
	}
	/**
	 * addsModule to the database. If it is tried to add an identical module,
	 * exception is caught in GUI 
	 * @param modID
	 * @param name
	 * @param credits
	 * @param taught
	 * @param obligatory
	 * @param level
	 * @param degID
	 * @throws SQLException
	 */
	public static void addModule(String modID, String name, int credits, String taught, int obligatory, char level, String degID) throws SQLException {
		openConnection();
		String query = "INSERT INTO Module SET modID = ?, name = ?, credits= ?, taught = ?, obligatory = ?, level = ?, degID =(SELECT degID FROM Degree WHERE degID = ?)";
		PreparedStatement pstm = connection.prepareStatement(query);
		pstm.setString(1, modID);
		pstm.setString(2, name);
		pstm.setInt(3, credits );
		pstm.setString(4, taught);
		pstm.setInt(5, obligatory);
		pstm.setString(6, String.valueOf(level));
		pstm.setString(7, degID);
		pstm.executeUpdate();
		closeConnection();
		return;

	}
	/**
	 * Removes a module from database
	 * @param modID
	 * @throws SQLException
	 */
	public static void removeModule(String modID) throws SQLException {
		openConnection();
		PreparedStatement pstm = connection.prepareStatement(
				"DELETE FROM Module WHERE modID = ?");
		pstm.setString(1, modID);
		pstm.executeUpdate();
		closeConnection();
		return;
	}
	/**
	 * hashes a password. Used when creating an Account and when logging into
	 * the system
	 * @param user password
	 * @return hashed password (String)
	 */
	public static String hashPassword(String p) {
	    String password = p;
	    SHA3.DigestSHA3 digestSHA3 = new SHA3.Digest512();
	    byte[] digest = digestSHA3.digest(password.getBytes());

	    return Hex.toHexString(digest);
	}
	
	
	//for testing
	public static void main(String[] arg) throws SQLException {
		
		/*DACAdmin.addAccount("ROKAS", "diamond", 'R');
		DACAdmin.addAccount("ROKAS", "diamond", 'R'); */
		//DACAdmin.removeAccount(19);
		//DACAdmin.setPermission(18, 'A');
		//DACAdmin.addDepartment("COM", "Computer Science");
		//DACAdmin.removeDepartment("BLA");
		//DACAdmin.addDegree("COMU01", "BSc Computer Science", '3', "COM");
		//DACAdmin.addDepartment("CBE", "Chemical and Biological Engineering");
		//DACAdmin.addDegree("CBEU65", "Chemical Engineering BSc", '2', "CBE");
	}


}
