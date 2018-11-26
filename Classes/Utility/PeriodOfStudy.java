package Utility;

import java.sql.Date;

public class PeriodOfStudy {
	private int periodID;
	private char label;
	private Date startDate;
	private Date endDate;
	private char level;
	private int regNumber;
	
	public PeriodOfStudy(int periodID, char label, Date startDate, Date endDate,
			char level, int regNumber) {
		this.periodID = periodID;
		this.label = label;
		this.startDate = startDate;
		this.endDate = endDate;
		this.level = level;
		this.regNumber = regNumber;
	}
	
	//get methods
	public int getPeriodID() { return periodID ; }
	public char getLabel() { return label; }
	public Date startDate() { return startDate; }
	public Date endDate() { return endDate; }
	public char level() { return level; }
	public int regNumber() { return regNumber; }
	
}
