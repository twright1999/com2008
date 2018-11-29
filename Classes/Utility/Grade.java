package Utility;

public class Grade {
	private int gradeID;
	private float grade; //grade in percents
	private String modID; //FK to module
	private int regNumber; //FK to Student
	
	public Grade(int gradeID, float grade, String modID, int regNumber) {
		this.gradeID = gradeID;
		this.grade = grade;
		this.modID = modID;
		this.regNumber = regNumber;
	}
	
	//get methods
	public int getGradeID() { return gradeID ; }
	public float getGrade() { return grade ; }
	public String getModID() { return modID ; }
	public int regNumber() { return regNumber ;}
	
	//toString method
	public String toString() {
		String all = "gradeID: " + gradeID + " grade: " + grade
				+ " modID: " + modID + " regNumber: " + regNumber + " ";
		return all;
	}
	
}
