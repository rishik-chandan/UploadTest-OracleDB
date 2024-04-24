package com.upload;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Employee")
public class Employee {
	@Id
	private String Id;

	@Column(name = "Name")
	private String name;
	
	@Column(name = "Address")
	private String address;

	@Column(name = "Email")
	private String email;

	@Column(name = "PhNo")
	private double phNo;

	@Column(name = "State")
	private String state;

	@Column(name = "Place")
	private String place;

	public Employee() {
	}

	public String getId() {
		return Id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public double getPhNo() {
		return phNo;
	}

	public void setPhNo(double phNo) {
		this.phNo = phNo;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public void setId(String id) {
		Id = id;
	}

	
	
	
}