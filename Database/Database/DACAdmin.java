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
	
	/**
	 * removes a department. If the account does not exist already,
	 * method will do nothing
	 * @param depID
	 * @throws SQLException
	 */

	public static void removeDepartment(String depID) throws SQLException {
		openConnection();
		PreparedStatement pstm = connection.prepareStatement(
				"DELETE FROM Department WHERE depID = ?");
		pstm.setString(1, depID);
		pstm.executeUpdate();
		closeConnection();
		return;
		
	}
	
	/**
	 * adds new degree. To prevent adding degree with the same ID,
	 * catch the exception in GUI and display "such degree already exists"
	 * @param degID
	 * @param name
	 * @param level
	 * @param depID
	 * @throws SQLException
	 */
	public static void addDegree(String degID, String name, char level, String depID, boolean placement) throws SQLException {
		openConnection();
		String query = "INSERT INTO Degree SET degID = ?, name = ?, level= ?, depID = ?, placement = ?";
		PreparedStatement pstm = connection.prepareStatement(query);
		pstm.setString(1, degID);
		pstm.setString(2, name);
		pstm.setString(3, String.valueOf(level));
		pstm.setString(4, depID);
		pstm.setBoolean(5, placement);
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
	public static void addModule(String modID, String name, int credits, String taught, boolean obligatory, char level, String degID) throws SQLException {
		openConnection();
		String query = "INSERT INTO Module SET modID = ?, name = ?, credits= ?, taught = ?, obligatory = ?, level = ?, degID =(SELECT degID FROM Degree WHERE degID = ?)";
		PreparedStatement pstm = connection.prepareStatement(query);
		pstm.setString(1, modID);
		pstm.setString(2, name);
		pstm.setInt(3, credits );
		pstm.setString(4, taught);
		pstm.setBoolean(5, obligatory);
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
		DACAdmin.addAccount("admin", "admin", 'A');
		/*DACAdmin.addAccount("ROKAS", "diamond", 'R');
		DACAdmin.addAccount("ROKAS", "diamond", 'R'); */
		//DACAdmin.removeAccount(19);
		//DACAdmin.setPermission(18, 'A');
		//DACAdmin.addDepartment("COM", "Computer Science");
		//DACAdmin.removeDepartment("BLA");
		//DACAdmin.addDegree("COMU01", "BSc Computer Science", '3', "COM");
		//addDepartment("CBE", "Chemical and Biological Engineering");
		//addDegree("CBEU65", "Chemical Engineering BSc", '2', "CBE", false);
		//addModule("CBE2007", "Process Control", 20, "Autumn", true, '2', "CBEU65");
	}


}
