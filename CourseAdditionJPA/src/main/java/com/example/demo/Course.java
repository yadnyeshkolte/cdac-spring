package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Course {

	@Id
	private int cid;
	private String name;
	private String type;
	
	public Course(){
		this.cid = 0;
		this.name = null;
		this.type = null;
	}
	
	public Course(int cid, String name, String type) {
		this.cid = cid;
		this.name = name;
		this.type = type;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
	
}
