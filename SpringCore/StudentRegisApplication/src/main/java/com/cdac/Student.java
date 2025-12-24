package com.cdac;

public class Student {
	int Std_ID;
	String Std_Name;
	String emailId;
	
	public Student(int std_ID, String std_Name, String emailId) {
		Std_ID = std_ID;
		Std_Name = std_Name;
		this.emailId = emailId;
	}

	public int getStd_ID() {
		return Std_ID;
	}

	public void setStd_ID(int std_ID) {
		Std_ID = std_ID;
	}

	public String getStd_Name() {
		return Std_Name;
	}

	public void setStd_Name(String std_Name) {
		Std_Name = std_Name;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
}
