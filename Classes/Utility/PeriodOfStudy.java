package Utility;

import java.sql.Date;

public class PeriodOfStudy {
	private String periodID;
	private char label;
	private String startDate;
	private String endDate;
	private char level;
	private int regNumber;
	
	public PeriodOfStudy(String periodID, char label, String startDate, String endDate,
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
	public String getStartDate() { return startDate; }
	public String getEndDate() { return endDate; }
	public char getLevel() { return level; }
	public int getRegNumber() { return regNumber; }
	
	public String toString() {
		String all = "periodID: " + periodID + " label: " + label
				+ " stardDate: " + startDate
				+ " endDate: " + endDate + " level: " + level + " regNumber: " + regNumber + " ";
		return all;
				
	
	}
}
