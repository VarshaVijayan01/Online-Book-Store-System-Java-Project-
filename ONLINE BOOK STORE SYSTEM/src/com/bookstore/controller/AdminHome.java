package com.bookstore.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;

import com.bookstore.domain.Books;
import com.bookstore.domain.CustomerDetails;
import com.mysql.jdbc.PreparedStatement;

import application.Connector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class AdminHome extends LoginPageLoader implements Initializable {
	@FXML
	public TableView<Books> tableView1;
	@FXML
	public TableColumn<?, ?> bookId;
	@FXML
	public TableColumn<?, ?> bookName;
	@FXML
	public TableColumn<?, ?> bookAuthor;
	@FXML
	public TableColumn<?, ?> qty;
	@FXML
	public TableColumn<?, ?> price;
	@FXML
	public TableColumn<?, ?> genre;
	@FXML
	public TextField txtbookId;
	@FXML
	public TextField txtbookName;
	@FXML
	public TextField txtbookAuthor;
	@FXML
	public TextField txtprice;
	@FXML
	public TextField txtqty;
	@FXML
	public TextField txtgenre;
	@FXML
	public TextField txtBookId;
	@FXML
	public TextField txtBookName;
	@FXML
	public TextField txtAuthor;
	@FXML
	public TextField txtQuantity;
	@FXML
	public TextField txtPrice;
	@FXML
	public TextField txtGenre;
	@FXML
	public Button btnAdd;
	@FXML
	public Button btnUpdate; 
	@FXML
	public Button btnLoadCustDetatils;

	ObservableList<Books> books1;
	ObservableList<CustomerDetails> custdet1;
	PreparedStatement preparedstatement = null;
	ResultSet rs = null;
	Connector myConnector = new Connector();
	Connection myConn;
	Statement myStmt;
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// Connector myConnector = new Connector();

		// setCellTable1();
		books1 = FXCollections.observableArrayList();
		setCellTable();
		// custdet1=FXCollections.observableArrayList();

	}

	public void setCellTable() {

		bookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
		bookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
		bookAuthor.setCellValueFactory(new PropertyValueFactory<>("bookAuthor"));
		price.setCellValueFactory(new PropertyValueFactory<>("price"));
		qty.setCellValueFactory(new PropertyValueFactory<>("qty"));
		genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
	}
//clearing of records
	public void ClearRecords() {
		txtbookId.setText("");
		txtbookName.setText("");
		txtbookAuthor.setText("");
		txtprice.setText("");
		txtqty.setText("");
		txtgenre.setText("");
		tableView1.getItems().clear();
	}
//inserting new books to the list
	public void AddNewBooks(ActionEvent event) throws SQLException {
		myConn = myConnector.databaseConnection();

		try {
			Alert alert = new Alert(AlertType.INFORMATION);
			if(!txtbookId.getText().equals("") && !txtbookName.getText().equals("")
					&& !txtbookAuthor.getText().equals("") && !txtprice.getText().equals("") 
					&& !txtqty.getText().equals("") && !txtgenre.getText().equals("")){
			// myConn= myConnector.databaseConnection();
			Statement myStmt = myConn.createStatement();
			String sql = ("INSERT INTO Allbooks(bookId,bookName,bookAuthor,price,qty,genre)VALUES(?,?,?,?,?,?)");
			PreparedStatement prepstatement = (PreparedStatement) myConn.prepareStatement(sql);
			// myConn = DriverManager.getConnection(url, username, pwd);

			prepstatement.setInt(1, Integer.parseInt(txtbookId.getText()));
			prepstatement.setString(2, txtbookName.getText());
			prepstatement.setString(3, txtbookAuthor.getText());
			prepstatement.setDouble(4, Double.parseDouble(txtprice.getText()));
			prepstatement.setInt(5, Integer.parseInt(txtqty.getText()));
			prepstatement.setString(6, txtgenre.getText());
			prepstatement.addBatch();
			prepstatement.executeBatch();
			JOptionPane.showMessageDialog(null, "Record Successfully Inserted!");
			// tableView1.getColumns();
			prepstatement.close();
			// rs.close();
			// myConn.close();
			ClearRecords();
			reloadTableData();
			}else{
				alert.setTitle("Add New Book Error!");
				alert.setHeaderText("Add New Book Error");
				alert.setContentText("Invalid data for book!");
				alert.showAndWait();
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//Viewing all books in the list
	public void ViewAllBooks(ActionEvent event) {
		getAllBooks();
	}
//Getting all books frm the list
	private void getAllBooks() {
		myConn = myConnector.databaseConnection();
		tableView1.getItems().clear();
		try { // myConn= myConnector.databaseConnection();
			Statement myStmt = myConn.createStatement();
			String sql = ("Select * from Allbooks");
			PreparedStatement prepstatement = (PreparedStatement) myConn.prepareStatement(sql);

			rs = prepstatement.executeQuery();
			while (rs.next()) {

				books1.add(new Books(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getDouble(5),
						rs.getString(6), null, sql, sql, null, 0, sql));

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (myStmt != null)
					myStmt.close();
			} catch (SQLException se2) {
			}
			try {
				if (myConn != null)
					myConn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}

			tableView1.setItems(books1);
		}
	}
//Enter in textfields
	public void fillTextbox(ActionEvent event) {
		Books booktable2 = tableView1.getSelectionModel().getSelectedItem();
		try {
			
			enableDisableUpdateFields(true);
			
			txtBookId.setText("" + booktable2.getBookId());
			txtBookName.setText(booktable2.getBookName());
			txtAuthor.setText(booktable2.getBookAuthor());
			txtQuantity.setText("" + booktable2.getQty());
			txtPrice.setText("" + booktable2.getPrice());
			txtGenre.setText("" + booktable2.getGenre());
		} catch (Exception e) {
			e.printStackTrace();
		} 

	}

	private void enableDisableUpdateFields(boolean isDisable) {
		txtBookId.setEditable(isDisable);
		txtBookName.setEditable(isDisable);
		txtAuthor.setEditable(isDisable);
		txtQuantity.setEditable(isDisable);
		txtPrice.setEditable(isDisable);
		txtGenre.setEditable(isDisable);
		btnUpdate.setDisable(!isDisable);
		
	}
	
	public void clearUpdateTxtbox() {
		
			txtBookId.setText("");
			txtBookName.setText("");
			txtAuthor.setText("");
			txtQuantity.setText("");
			txtPrice.setText("");
			txtGenre.setText("");

	}
//Updation of book list
	public void updateRecors(ActionEvent event) {
		Books booktable2 = new Books();
		try {
			Alert alert = new Alert(AlertType.INFORMATION);
			if (!txtBookName.getText().equals("") && !txtAuthor.getText().equals("")
					&& !txtGenre.getText().equals("") && !txtQuantity.getText().equals("") 
					&& !txtPrice.getText().equals("") && !txtBookId.getText().equals("")) {
			int bkId = Integer.parseInt(txtBookId.getText());
			// txtBookName.setText(booktable2.getBookName());
			// txtAuthor.setText(booktable2.getBookAuthor());
			int bQty = Integer.parseInt(txtQuantity.getText());
			double bPrice = Double.parseDouble(txtPrice.getText());
			System.out.println(bPrice);
			// txtGenre.setText(""+booktable2.getGenre());
			myConn = myConnector.databaseConnection();
			Statement myStmt = myConn.createStatement();
			String sql = "UPDATE Allbooks SET bookName = ?, bookAuthor = ?, price= ?, qty=?, genre=?  WHERE bookId = ?";
			PreparedStatement prepstatement = (PreparedStatement) myConn.prepareStatement(sql);
			prepstatement.setString(1, txtBookName.getText());
			prepstatement.setString(2, txtAuthor.getText());
			prepstatement.setDouble(3, bPrice);
			prepstatement.setInt(4, bQty);
			prepstatement.setString(5, txtGenre.getText());
			prepstatement.setInt(6, bkId);
			prepstatement.addBatch();
			prepstatement.executeUpdate();
			prepstatement.close();
			clearUpdateTxtbox();
			reloadTableData();
			enableDisableUpdateFields(false);
			} else {
				alert.setTitle("Add New Book Error!");
				alert.setHeaderText("Add New Book Error");
				alert.setContentText("Invalid data for book!");
				alert.showAndWait();

			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	private void reloadTableData() {
		tableView1.getItems().clear();
		getAllBooks();
	}

	public void RemoveBooks(ActionEvent event) {
		myConn = myConnector.databaseConnection();

		try {

			// myConn= myConnector.databaseConnection();
			Statement myStmt = myConn.createStatement();

			Books booktable = tableView1.getSelectionModel().getSelectedItem();
			String sql = ("DELETE from Allbooks WHERE bookName=?");
			PreparedStatement prepstatement = (PreparedStatement) myConn.prepareStatement(sql);
			// myConn = DriverManager.getConnection(url, username, pwd);
			prepstatement.setString(1, booktable.getBookName());
			prepstatement.executeUpdate();

			JOptionPane.showMessageDialog(null, "This records is Successfully removed!");
			rs.close();
			myConn.close();
			reloadTableData();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// viewRecords();
		finally {
			// finally block used to close resources
			try {
				if (myStmt != null)
					myStmt.close();
			} catch (SQLException se2) {
			}
			try {
				if (myConn != null)
					myConn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}
//Customer list Window
	public void btnCustDetails(ActionEvent event) {

		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/bookstore/views/customerDetailsPage.fxml"));
			Parent root1 = fxmlLoader.load();
			Stage stage1 = new Stage();
			stage1.setTitle("Customer Details");
			stage1.setScene(new Scene(root1));
			stage1.show();
			closeLoginWindow(btnAdd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//Employee list Window
	public void btnEmpDetails(ActionEvent event) {

		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/bookstore/views/EmployeeDetails.fxml"));
			Parent root1 = fxmlLoader.load();
			Stage stage1 = new Stage();
			stage1.setTitle("Employee Details");
			stage1.setScene(new Scene(root1));
			stage1.show();
			closeLoginWindow(btnAdd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void logout(ActionEvent event) {
		loadLoginPage();
		closeLoginWindow(btnAdd);
	}

}
