package Utility;
/**
 * 
 * @author Rokas Bagdonas
 * 
 */
public class Department {
	
	private String depId; //Three letter code (Like COM)
	private String name;  //Full name (like Computer Science)
	private Degree[] degrees; //Degrees available in this department
	
	public Department(String depId, String name, Degree[] degrees) {
		this.depId = depId;
		this.name = name;
		this.degrees = degrees;
	}
	
	public String getDepId() { return depId ; }
	public String getName() { return name ; }
	public Degree[] getDegree() { return degrees; }
	
	public String toString() {
		String all = "depID: " + depId + " dep name: " + name + " degrees: " + degrees.toString() + " " ;
		return all;
	}
	
}
