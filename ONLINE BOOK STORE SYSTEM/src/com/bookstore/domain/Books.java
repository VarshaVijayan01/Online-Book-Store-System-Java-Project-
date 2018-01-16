package com.bookstore.domain;

public class Books {
	public Integer bookId;
	public String bookName;
	public String bookAuthor;
	public Integer qty;
	public double price;
	public String genre;
	public Integer bookId1;
	public String bookName1;
	public String bookAuthor1;
	public Integer qty1;
	public double price1;
	public String genre1;
	public Books(Integer bookId, String bookName, String bookAuthor, Integer qty, double price, String genre,Integer bookId1, String bookName1, String bookAuthor1, Integer qty1, double price1, String genre1){
		super();
		this.bookId = bookId;
		this.bookName = bookName;
		this.bookAuthor = bookAuthor;
		this.qty = qty;
		this.price = price;
		this.genre = genre;
		
		
	}
	
	public Books(){
		
	}
	public Integer getBookId() {
		return bookId;
	}
	public String getBookName() {
		return bookName;
	}
	public String getBookAuthor() {
		return bookAuthor;
	}
	public Integer getQty() {
		return qty;
	}
	public double getPrice() {
		return price;
	}
	public String getGenre() {
		return genre;
	}

	

	@Override
	public String toString() {
		return "Books [bookId=" + bookId + ", bookName=" + bookName + ", bookAuthor=" + bookAuthor + ", qty=" + qty
				+ ", price=" + price + ", genre=" + genre + "]";
	}
	}

