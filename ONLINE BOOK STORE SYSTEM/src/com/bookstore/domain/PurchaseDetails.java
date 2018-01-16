package com.bookstore.domain;

public class PurchaseDetails {

	private int orderId;
	private int noOfBooks;
	private int total;

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getNoOfBooks() {
		return noOfBooks;
	}

	public void setNoOfBooks(int noOfBooks) {
		this.noOfBooks = noOfBooks;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public PurchaseDetails(int orderId, int noOfBooks, int total) {
		super();
		this.orderId = orderId;
		this.noOfBooks = noOfBooks;
		this.total = total;
	}

	
}
