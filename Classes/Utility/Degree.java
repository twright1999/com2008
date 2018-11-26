package Utility;
/**
 * 
 * @author Rokas
 *
 */
public class Degree {
	
	private String degId; //unique 2 digit number. Need to make sure that it is in the same format in DB
	private String name; //depId ++ U/P ++ (Year in Industry?) ++ degId
	private char levelOfStudy; //1,2,3,4,P
	
	public Degree(String degId, String name, char levelOfStudy) {
		this.degId = degId;
		this.name = name;
		this.levelOfStudy = levelOfStudy;
	}
	
	public String getDegId() { return degId; }
	public String getName() { return name; }
	public char getLevelOfStudy() { return levelOfStudy ; }
	
}

