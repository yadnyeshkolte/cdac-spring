package com.cdac;

public class Student {
	String Std_ID;
	String Std_Name;
	String emailId;
	
	public Student(String std_ID, String std_Name, String emailId) {
		
		this.Std_ID = std_ID;
		this.Std_Name = std_Name;
		this.emailId = emailId;
	}

	public String getStd_ID() {
		return Std_ID;
	}

	public void setStd_ID(String std_ID) {
		this.Std_ID = std_ID;
	}

	public String getStd_Name() {
		return Std_Name;
	}

	public void setStd_Name(String std_Name) {
		this.Std_Name = std_Name;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
	
	
	
}
