package Utility;
/**
 * 
 * @author Rokas
 *
 */
public class Degree {
	enum LevelOfStudy { One, Two, Three, Four, P };
	
	private String degId; //unique 2 digit number. Need to make sure that it is in the same format in DB
	private String name; //depId ++ U/P ++ (Year in Industry?) ++ degId
	private LevelOfStudy levelOfStudy;
	
	public Degree(String degId, String name, LevelOfStudy levelOfStudy) {
		this.degId = degId;
		this.name = name;
		this.levelOfStudy = levelOfStudy;
	}
	
	public String getDegId() { return degId; }
	public String getName() { return name; }
	public LevelOfStudy getLevelOfStudy() { return levelOfStudy ; }
	
}

