package com.bookstore.domain;

public class CustomerDetails {
	
	public Integer memberId;
	public String name;
	public String gender;
	public Integer contact;
	public String address;
	public String shippingaddress;
	public Integer getMemberId() {
		return memberId;
	}
	public String getName() {
		return name;
	}
	public String getGender() {
		return gender;
	}
	public Integer getContact() {
		return contact;
	}
	public String getAddress() {
		return address;
	}
	public String getShippingaddress() {
		return shippingaddress;
	}
	public CustomerDetails(Integer memberId, String name, String gender, Integer contact,
			String address, String shippingaddress) {
		super();
		this.memberId = memberId;
		this.name = name;
		this.gender = gender;
		this.contact = contact;
		this.address = address;
		this.shippingaddress = shippingaddress;
	}
	

	
	
	
	
	
	
	}
