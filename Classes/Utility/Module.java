package Utility;
/**
 * 
 * @author Rokas
 *
 */
public class Module {
	
	private String moduleId; //department's code  + 4 digit number
	private String name; //Full name (Functional programming)
	private int credits;
	private String taught; 
	private Boolean obligatory;
	private String degId;
	
	public Module(String moduleId, String name, int credits, String taught,
			Boolean obligatory, String degId) {
		this.moduleId = moduleId;
		this.name = name;
		this.credits = credits;
		this.taught = taught;
		this.obligatory = obligatory;
		this.degId = degId;
	}
	
	public String getModuleId() { return moduleId; }
	public String getName() { return name; }
	public int getCredits() { return credits; }
	public String getTaught() { return taught; }
	public Boolean getObligatory() { return obligatory; }
	public String getDegId() { return degId; }
}
