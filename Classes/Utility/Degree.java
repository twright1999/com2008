package Utility;
/**
 * 
 * @author Rokas
 *
 */
public class Degree {
	
	private String degId; //unique 2 digit number. Need to make sure that it is in the same format in DB
	private String name; //depId ++ U/P ++ (Year in Industry?) ++ degId
	private int level; //1,2,3,4 (how many years degree lasts)
	private String depID;
	private boolean placement; //True - degree is with placement
	
	public Degree(String degId, String name, int level, String depID, boolean placement) {
		this.degId = degId;
		this.name = name;
		this.level = level;
		this.depID = depID;
		this.placement = placement;
	}
	
	public String getDegID() { return degId; }
	public String getName() { return name; }
	public int getLevelOfStudy() { return level ; }
	public String getDepID() { return depID ; }
	public boolean getPlacement() { return placement ; }
	
	public String toString() {
		String all = "degID: " + degId + " name: " + name +
				" level: " + level + " depID: " + depID + " placement: " + placement ;
		return all;
	}
	
}

