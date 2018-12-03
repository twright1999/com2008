package Utility;

public class Student_Module {
	private int regID; //student registration number
	private String modID;
	
	public Student_Module(int regID, String modID) {
		this.regID = regID;
		this.modID = modID;
	}
	
	public String toString() {
		return "regID: " + regID + " modID: " + modID;
	}
}
