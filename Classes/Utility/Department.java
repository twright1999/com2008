package Utility;
/**
 * 
 * @author Rokas Bagdonas
 * 
 */
public class Department {
	
	private String depID; //Three letter code (Like COM)
	private String name;  //Full name (like Computer Science)
	
	public Department(String depId, String name) {
		this.depID = depId;
		this.name = name;
	}
	
	public String getDepId() { return depID ; }
	public String getName() { return name ; }
	
	public String toString() {
		String all = "depID: " + depID + " dep name: " + name + " " ;
		return all;
	}
	
}
