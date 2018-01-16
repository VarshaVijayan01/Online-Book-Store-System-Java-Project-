package com.bookstore.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class CustomerDetailsController extends LoginPageLoader implements Initializable {
	@FXML
	TableView<CustomerDetails> tableView3;
	ObservableList<CustomerDetails> custdet1;

	@FXML
	public TableColumn<?, ?> memberId;
	@FXML
	public TableColumn<?, ?> name;
	@FXML
	public TableColumn<?, ?> gender;
	@FXML
	public TableColumn<?, ?> contact;
	@FXML
	public TableColumn<?, ?> address;
	@FXML
	public TableColumn<?, ?> shippingaddress;
	@FXML
	public TextField txtMemberId;
	@FXML
	public TextField txtName;
	@FXML
	public TextField txtContact;
	@FXML
	public TextField txtGender;
	@FXML
	public TextField txtAddress;
	@FXML
	public TextField txtShipAddress;

	@FXML
	public Button btnDelete;
	@FXML
	public Button btnUpdateRecord;
	@FXML
	public Button btnUpdate;

	PreparedStatement prepstatement = null;
	ResultSet rs = null;
	Connector myConnector = new Connector();
	Connection myConn;
	Statement myStmt;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		custdet1 = FXCollections.observableArrayList();
		System.out.println("Data:");
		custdetails();
	}

	public void setCellTable() {

		memberId.setCellValueFactory(new PropertyValueFactory<>("memberId"));
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
		contact.setCellValueFactory(new PropertyValueFactory<>("contact"));
		address.setCellValueFactory(new PropertyValueFactory<>("address"));
		shippingaddress.setCellValueFactory(new PropertyValueFactory<>("shippingaddress"));
	}

	public void custdetails() {
		getAllCusts();

	}

	private void getAllCusts() {
		myConn = myConnector.databaseConnection();
		try { // myConn= myConnector.databaseConnection();
			Statement myStmt = myConn.createStatement();
			String sql = ("Select * from bookstorecustomerlist");
			PreparedStatement prepstatement = (PreparedStatement) myConn.prepareStatement(sql);

			rs = prepstatement.executeQuery();
			while (rs.next()) {

				custdet1.add(new CustomerDetails(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4),
						rs.getString(5), rs.getString(6)));
			}
			System.out.println("Data:" + Arrays.asList(custdet1));
			setCellTable();
			tableView3.setItems(custdet1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void fillTextboxs(ActionEvent event) {
		CustomerDetails booktable2 = tableView3.getSelectionModel().getSelectedItem();
		try {

			enableDisableUpdateFields(true);

			txtMemberId.setText("" + booktable2.getMemberId());
			txtName.setText(booktable2.getName());
			txtGender.setText(booktable2.getGender());
			txtContact.setText("" + booktable2.getContact());
			txtAddress.setText(booktable2.getAddress());
			txtShipAddress.setText(booktable2.getShippingaddress());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clearTextboxs() {
		try {
			txtMemberId.setText("");
			txtName.setText("");
			txtGender.setText("");
			txtContact.setText("");
			txtAddress.setText("");
			txtShipAddress.setText("");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void enableDisableUpdateFields(boolean isDisable) {

		txtName.setEditable(isDisable);
		txtGender.setEditable(isDisable);
		txtContact.setEditable(isDisable);
		txtAddress.setEditable(isDisable);
		txtShipAddress.setEditable(isDisable);
		btnUpdate.setDisable(!isDisable);

	}

	public void removeCustomer(ActionEvent event) {
		myConn = myConnector.databaseConnection();

		try {

			// myConn= myConnector.databaseConnection();
			Statement myStmt = myConn.createStatement();

			CustomerDetails custTable = tableView3.getSelectionModel().getSelectedItem();
			String sql = ("DELETE from bookstorecustomerlist WHERE memberid=?");
			PreparedStatement prepstatement = (PreparedStatement) myConn.prepareStatement(sql);
			System.out.println("Data custTable.getMemberId()" + custTable.getMemberId());
			// myConn = DriverManager.getConnection(url, username, pwd);
			prepstatement.setInt(1, custTable.getMemberId());
			prepstatement.executeUpdate();

			JOptionPane.showMessageDialog(null, "This Customer record is Successfully removed!");
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

	public void updateRecords(ActionEvent event) {
		Books booktable2 = new Books();
		try {
			Alert alert = new Alert(AlertType.INFORMATION);
			if (!txtName.getText().equals("") && !txtGender.getText().equals("") && !txtContact.getText().equals("")
					&& !txtAddress.getText().equals("") && !txtShipAddress.getText().equals("")) {
				int memberId = Integer.parseInt(txtMemberId.getText());
				int contact = Integer.parseInt(txtContact.getText());
				myConn = myConnector.databaseConnection();
				Statement myStmt = myConn.createStatement();
				String sql = "UPDATE bookstorecustomerlist SET  Name = ?, Gender= ?, Contact=?, Address=?, ShippingAddress=?  WHERE MemberId = ?";
				PreparedStatement prepstatement = (PreparedStatement) myConn.prepareStatement(sql);

				prepstatement.setString(1, txtName.getText());
				prepstatement.setString(2, txtGender.getText());
				prepstatement.setDouble(3, contact);
				prepstatement.setString(4, txtAddress.getText());
				prepstatement.setString(5, txtShipAddress.getText());
				prepstatement.setInt(6, memberId);
				prepstatement.addBatch();
				prepstatement.executeUpdate();
				prepstatement.close();
				reloadTableData();
				JOptionPane.showMessageDialog(null, "Record Successfully Updated!");
				clearTextboxs();
				enableDisableUpdateFields(false);
			} else {
				alert.setTitle("Update Customer Error!");
				alert.setHeaderText("Update Customer Error");
				alert.setContentText("Invalid data for Customer!");
				alert.showAndWait();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	private void reloadTableData() {
		tableView3.getItems().clear();
		getAllCusts();
	}
	
	public void homePage(ActionEvent event) {
		loadHomeWindow();
		closeLoginWindow(btnUpdate);
		
	}

}
