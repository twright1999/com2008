package Utility;

import java.sql.Date;

public class PeriodOfStudy {
	private String periodID;
	private char label;
	private Date startDate;
	private Date endDate;
	private char level;
	private int regNumber;
	
	public PeriodOfStudy(String periodID, char label, Date startDate, Date endDate,
			char level, int regNumber) {
		this.periodID = periodID;
		this.label = label;
		this.startDate = startDate;
		this.endDate = endDate;
		this.level = level;
		this.regNumber = regNumber;
	}
	
	//get methods
	public String getPeriodID() { return periodID ; }
	public char getLabel() { return label; }
	public Date getStartDate() { return startDate; }
	public Date getEndDate() { return endDate; }
	public char getLevel() { return level; }
	public int getRegNumber() { return regNumber; }
	
	public String toString() {
		String all = "periodID: " + periodID + " label: " + label
				+ " stardDate: " + startDate.toString() 
				+ " endDate: " + endDate.toString() + " level: " + level + " regNumber: " + regNumber + " ";
		return all;
				
	
	}
}
