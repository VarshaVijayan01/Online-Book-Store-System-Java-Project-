package com.bookstore.domain;

public class Employee {

	private int empId;
	private String empName;
	private String empAddress;
	private String empEmail;
	private String empContact;

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpAddress() {
		return empAddress;
	}

	public void setEmpAddress(String empAddress) {
		this.empAddress = empAddress;
	}

	public String getEmpEmail() {
		return empEmail;
	}

	public void setEmpEmail(String empEmail) {
		this.empEmail = empEmail;
	}

	public String getEmpContact() {
		return empContact;
	}

	public void setEmpContact(String empContact) {
		this.empContact = empContact;
	}

	public Employee(int empId,String empName,  String empEmail, String empContact, String empAddress) {
		super();
		this.empId=empId;
		this.empName = empName;
		this.empEmail = empEmail;
		this.empContact = empContact;
		this.empAddress = empAddress;
	}
	
	
}
