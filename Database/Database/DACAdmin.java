package Database;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.bouncycastle.util.encoders.Hex;

public class DACAdmin extends DAC {

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
	
	public static void removeAccount(int userID) throws SQLException {
		openConnection();
		PreparedStatement pstm = connection.prepareStatement(
				"DELETE FROM Account WHERE userID = ?");
		pstm.setInt(1, userID);
		pstm.executeUpdate();
		closeConnection();
		return;

	}
	
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
		String query = "INSERT INTO Degree SET degID = ?, name = ?, level= ?, depID =(SELECT depID FROM Department WHERE depID = ?)";
		PreparedStatement pstm = connection.prepareStatement(query);
		pstm.setString(1, degID);
		pstm.setString(2, name);
		pstm.setString(3, String.valueOf(level));
		pstm.setString(4, depID);
		pstm.executeUpdate();
		closeConnection();
		return;

	}
	
	public static void removeDegree(String degID) throws SQLException {
		openConnection();
		PreparedStatement pstm = connection.prepareStatement(
				"DELETE FROM Degree WHERE degID = ?");
		pstm.setString(1, degID);
		pstm.executeUpdate();
		closeConnection();
		return;
		
	}
	
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
	
	public static void removeModule(String modID) throws SQLException {
		openConnection();
		PreparedStatement pstm = connection.prepareStatement(
				"DELETE FROM Module WHERE modID = ?");
		pstm.setString(1, modID);
		pstm.executeUpdate();
		closeConnection();
		return;
	}

	public static String hashPassword(String p) {
	    String password = p;
	    
	    SHA3.DigestSHA3 digestSHA3 = new SHA3.Digest512();
	    byte[] digest = digestSHA3.digest(password.getBytes());

	    return Hex.toHexString(digest);
	}
	
	
	//for testing
	public static void main(String[] arg) throws SQLException {
		String hash1 = hashPassword("password");
		String hash2 = hashPassword("password");
		System.out.println(hash1);
		System.out.println(hash2);
		System.out.println(hash1.equals(hash2));
		//DACAdmin.removeModule("BAD69");
		//DACAdmin.addModule("BAD69", "BADMODULE2", 10, "AUTUMN", 1, '1', "BAD696");
	}


}
