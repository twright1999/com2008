package Utility;
/**
 * 
 * @author Rokas
 *
 */
public class Module {
	enum Period { Autum, Spring, Full_Year };
	
	private String moduleId; //department's code  + 4 digit number
	private String name; //Full name (Functional programming)
	private int credits;
	private Enum period; 
	private Boolean obligatory;
	private String degId;
	private Department[] departments;
	
	public Module(String moduleId, String name, int credits, Period period,
			Boolean obligatory, String degId, Department[] departments) {
		this.moduleId = moduleId;
		this.name = name;
		this.credits = credits;
		this.period = period;
		this.obligatory = obligatory;
		this.degId = degId;
		this.departments = departments;
	}
	
	public String getModuleId() { return moduleId; }
	public String getName() { return name; }
	public int getCredits() { return credits; }
	public Enum getPeriod() { return period; }
	public Boolean getObligatory() { return obligatory; }
	public String getDegId() { return degId; }
	public Department[] getDepartments() { return departments; }
}
