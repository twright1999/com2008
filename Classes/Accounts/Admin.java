package Accounts;

import java.sql.*;
public class Admin extends Account {

	public Admin(String userID, String name, String password) {
		super(userID, name, password);
		
	}

	private void addAcount(String name, String password) throws SQLException {
		
		Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team020", "team020", "aa429b86");
		Statement stmt = con.createStatement() ;
		Statement s=con.createStatement();
		s.executeUpdate("INSERT INTO `Account`(name,password) VALUE ('"+name+"','"+password+"')");
		
		con.close();
		
	}
	
	private void removeAccount(Account account) {
		
		
		
	}
	
	public static void main(String[] arg) throws SQLException {
		Admin admin = new Admin("A01", "Rokas", "gg");
		admin.addAcount("Pokas", "pp");
		
	}
	
}
