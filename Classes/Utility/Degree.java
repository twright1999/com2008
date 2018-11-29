package Utility;
/**
 * 
 * @author Rokas
 *
 */
public class Degree {
	
	private String degId; //unique 2 digit number. Need to make sure that it is in the same format in DB
	private String name; //depId ++ U/P ++ (Year in Industry?) ++ degId
	private char level; //1,2,3,4,P
	private String depID;
	
	public Degree(String degId, String name, char level, String depID) {
		this.degId = degId;
		this.name = name;
		this.level = level;
		this.depID = depID;
	}
	
	public String getDegID() { return degId; }
	public String getName() { return name; }
	public char getLevelOfStudy() { return level ; }
	public String getDepID() { return depID ; }
	
	public String toString() {
		String all = "degID: " + degId + " name: " + name +
				" level: " + level + " depID: " + depID;
		return all;
	}
	
}

