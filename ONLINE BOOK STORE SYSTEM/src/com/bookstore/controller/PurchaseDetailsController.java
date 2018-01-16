package com.bookstore.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.ResourceBundle;

import com.bookstore.domain.CustomerDetails;
import com.bookstore.domain.PurchaseDetails;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class PurchaseDetailsController extends LoginPageLoader implements Initializable {
	@FXML
	TableView<PurchaseDetails> purachseTable;
	ObservableList<PurchaseDetails> purachseData;
	@FXML
	public TableColumn<?, ?> orderId;
	@FXML
	public TableColumn<?, ?> noOfBooks;
	@FXML
	public TableColumn<?, ?> total;
	@FXML
	public Button btnHome;
	@FXML
	public Button btnLogout;
	ResultSet rs = null;
	Connector myConnector = new Connector();
	Connection myConn;
	Statement myStmt;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		purachseData = FXCollections.observableArrayList();
		getAllCusts();
	}

	public void setCellTable() {

		orderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
		noOfBooks.setCellValueFactory(new PropertyValueFactory<>("noOfBooks"));
		total.setCellValueFactory(new PropertyValueFactory<>("total"));
	}
// to get all customer details 
	private void getAllCusts() {
		myConn = myConnector.databaseConnection();
		try {
			Statement myStmt = myConn.createStatement();
			String sql = ("Select * from order_vij");
			PreparedStatement prepstatement = (PreparedStatement) myConn.prepareStatement(sql);
			rs = prepstatement.executeQuery();
			while (rs.next()) {
				purachseData.add(new PurchaseDetails(rs.getInt(1), rs.getInt(2), rs.getInt(3)));
			}
			System.out.println("Data:" + Arrays.asList(purachseData));
			setCellTable();
			purachseTable.setItems(purachseData);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//logout function
	public void logout(ActionEvent event) {
		loadLoginPage();
		closeLoginWindow(btnHome);
	}
//opening home page window
	public void homePage(ActionEvent event) {
		try {
			FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource("/com/bookstore/views/MainPageWindow.fxml"));
			Parent root2 = fxmlLoader1.load();
			Stage stage1 = new Stage();
			stage1.setTitle("Books-Main Page");
			stage1.setScene(new Scene(root2));
			stage1.show();
			closeLoginWindow(btnHome);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
