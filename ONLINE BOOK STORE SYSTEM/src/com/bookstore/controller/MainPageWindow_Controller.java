package com.bookstore.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import com.bookstore.domain.Books;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class MainPageWindow_Controller extends LoginPageLoader implements Initializable {
	@FXML
	public TableView<Books> tableView2;
	@FXML
	public TableColumn<?, ?> bookId;
	@FXML
	public TableColumn<?, ?> bookName;
	@FXML
	public TableColumn<?, ?> bookAuthor;
	@FXML
	public TableColumn<?, ?> price;
	@FXML
	public TableColumn<?, ?> qty;
	@FXML
	public TableColumn<?, ?> genre;
	@FXML
	public ComboBox<String> combo1;
	@FXML
	public TableView<Books> addCartTable;
	@FXML
	public TableColumn<?, ?> cartBookId;
	@FXML
	public TableColumn<?, ?> cartBookName;
	@FXML
	public TableColumn<?, ?> cartPrice;
	@FXML
	public Button btn7;
	@FXML
	public Button addtocart;
	@FXML
	public Button removefromcart;
	@FXML
	public TextArea textArea1;
	@FXML
	public Label lblTotal;
	@FXML
	public Button btnPayment;
	@FXML
	public Button btnCheckout;

	ObservableList<String> comboList = FXCollections.observableArrayList("Fiction", "Non-Fiction", "Kids & Teens");
	ObservableList<Books> books2 = FXCollections.observableArrayList();
	ObservableList<Books> cartBooks = FXCollections.observableArrayList();
	PreparedStatement preparedstatement = null;
	ResultSet rs = null;
	Connector myConnector = new Connector();
	Connection myConn;
	Statement myStmt;
//initialize
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		myConn = myConnector.databaseConnection();
		combo1.setItems(comboList);
		combo1.getSelectionModel().selectFirst();
		bookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
		bookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
		bookAuthor.setCellValueFactory(new PropertyValueFactory<>("bookAuthor"));
		price.setCellValueFactory(new PropertyValueFactory<>("price"));
		qty.setCellValueFactory(new PropertyValueFactory<>("qty"));
		genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
		cartBookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
		cartBookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
		cartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
	}
// to make the books available based on the genre
	public void Clickbtn7(ActionEvent event) {
		System.out.println("Comboboxdata" + combo1.getValue());
		if (combo1.getValue().equals("Fiction")) {
			try {
				tableView2.getItems().clear();
				String query = "select * from Allbooks where genre='Fiction'";
				preparedstatement = (PreparedStatement) myConn.prepareStatement(query);
				rs = preparedstatement.executeQuery();
				while (rs.next()) {
					books2.add(new Books(rs.getInt("bookId"), rs.getString("bookName"), rs.getString("bookAuthor"),
							rs.getInt("qty"), rs.getDouble("price"), rs.getString("genre"), null, query, query, null, 0,
							query));
					// tableView2.setItems(books2);
				}
				preparedstatement.close();
				rs.close();
			} catch (Exception e) {
				System.out.println("Error in fiction");
				e.printStackTrace();
			}
			tableView2.setItems(books2);
		}

		else if (combo1.getValue().equals("Non-Fiction")) {
			try {
				tableView2.getItems().clear();
				String query = "select * from Allbooks where genre='Non-Fiction'";
				preparedstatement = (PreparedStatement) myConn.prepareStatement(query);
				rs = preparedstatement.executeQuery();
				while (rs.next()) {
					books2.add(new Books(rs.getInt("bookId"), rs.getString("bookName"), rs.getString("bookAuthor"),
							rs.getInt("qty"), rs.getDouble("price"), rs.getString("genre"), null, query, query, null, 0,
							query));
				
				}
				preparedstatement.close();
				rs.close();
			} catch (Exception e) {
				System.out.println("Error in Non-fiction");
				e.printStackTrace();
			}
			tableView2.setItems(books2);
		}

		else if (combo1.getValue().equals("Kids & Teens")) {
			try {
				tableView2.getItems().clear();
				String query = "select * from Allbooks where genre='Teens & Kids'";
				preparedstatement = (PreparedStatement) myConn.prepareStatement(query);
				rs = preparedstatement.executeQuery();
				while (rs.next()) {
					books2.add(new Books(rs.getInt("bookId"), rs.getString("bookName"), rs.getString("bookAuthor"),
							rs.getInt("qty"), rs.getDouble("price"), rs.getString("genre"), null, query, query, null, 0,
							query));
					// tableView2.setItems(books2);
				}
				preparedstatement.close();
				rs.close();
			} catch (Exception e) {
				System.out.println("Error in Teens & Kids");
				e.printStackTrace();
			}
			tableView2.setItems(books2);
		}
	}

	public void addtocart(ActionEvent event) {
		Books booktable2 = tableView2.getSelectionModel().getSelectedItem();
		String sql = "Select bookId,bookName,bookAuthor,price from Allbooks";
		try {
			cartBooks.add(booktable2);
			addCartTable.setItems(cartBooks);
			btnCheckout.setDisable(false);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//to remove books from cart
	public void removefromCart(ActionEvent event) {
		Books booktable2 = addCartTable.getSelectionModel().getSelectedItem();

		try {
			addCartTable.getItems().remove(booktable2);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
//after adding to cart the checkout function
	public void checkout(ActionEvent event) {
		ObservableList<Books> booktable2 = addCartTable.getItems();
		System.out.println("Error in Teens & Kids" + Arrays.asList(booktable2));
		int total = 0;
		try {
			for (Books books : booktable2) {
				total += total + books.getPrice();
			}
			
			lblTotal.setText("" + total);
			btnPayment.setDisable(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//to add to cart
	public void placeOrder(ActionEvent event) {
		ObservableList<Books> booktable2 = addCartTable.getItems();
		double total = 0;
		int count = 0;
		try {
			for (Books books : booktable2) {
				total += total + books.getPrice();
				count++;
			}
			myConn = myConnector.databaseConnection();

			Statement myStmt = myConn.createStatement();
			String sql = ("INSERT INTO order_vij(noofbooks,total)VALUES(?,?)");
			PreparedStatement prepstatement = (PreparedStatement) myConn.prepareStatement(sql);

			prepstatement.setInt(1, count);
			prepstatement.setDouble(2, total);

			prepstatement.addBatch();
			prepstatement.executeBatch();
			JOptionPane.showMessageDialog(null, "Order Placed Successfully!!!");
			prepstatement.close();
			clearCart();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//to remove books from cart
	private void clearCart() {
		addCartTable.getItems().clear();
		btnPayment.setDisable(true);
		btnCheckout.setDisable(true);
		lblTotal.setText("");
	}
//logout method
	public void logout(ActionEvent event) {
		loadLoginPage();
		closeLoginWindow(btnPayment);
	}
	//To open the purchase details
	public void purchaseDetails(ActionEvent event) {
		try {
			FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource("/com/bookstore/views/PurchaseDetails.fxml"));
			Parent root2 = fxmlLoader1.load();
			Stage stage1 = new Stage();
			stage1.setTitle("Books-Main Page");
			stage1.setScene(new Scene(root2));
			closeLoginWindow(btnPayment);
			stage1.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
