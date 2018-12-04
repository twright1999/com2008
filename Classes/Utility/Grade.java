package Utility;

public class Grade {
	private int gradeID;
	private float initialGrade; //initial grade in percents
	private float resitGrade; //resit grade, can be null
	private String modID; //FK to module
	private int regNumber; //FK to Student
	
	public Grade(int gradeID, float initialGrade, float resitGrade, String modID, int regNumber) {
		this.gradeID = gradeID;
		this.initialGrade = initialGrade;
		this.resitGrade = resitGrade;
		this.modID = modID;
		this.regNumber = regNumber;
	}
	
	//get methods
	public int getGradeID() { return gradeID ; }
	public float getInitialGrade() { return initialGrade ; }
	public float getResitGrade() { return resitGrade ; }
	public String getModID() { return modID ; }
	public int getRegNumber() { return regNumber ;}
	
	//toString method
	public String toString() {
		String all = "gradeID: " + gradeID + " grade: " + initialGrade + " resitGrade: " + resitGrade
				+ " modID: " + modID + " regNumber: " + regNumber + " ";
		return all;
	}
	
}
