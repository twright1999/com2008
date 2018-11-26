package Utility;

public class PeriodOfStudy {
	private char label;
	private char levelOfStudy;
	private String startDate;
	private String endDate;
	
	public PeriodOfStudy(char label, char levelOfStudy, String startDate,
			String endDate) {
		this.label = label;
		this.levelOfStudy = levelOfStudy;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	//get methods
	public char getLabel() { return label; }
	public char getLevelOfStudy() { return levelOfStudy; }
	public String startDate() { return startDate; }
	public String endDate() { return endDate; }
	
}
