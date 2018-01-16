package com.bookstore.controller;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.PreparedStatement;

import application.Connector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class WindowsRegister_Controller extends LoginPageLoader {
	@FXML
	public TextField txtUser;
	@FXML
	public TextField txtPass;
	@FXML
	public TextField txtName;
	@FXML
	public TextField txtContact;
	@FXML
	public TextField txtAddress;
	@FXML
	public TextField txtShipAddress;

	@FXML
	public RadioButton female;
	@FXML
	public RadioButton male;

	@FXML
	public Button btnCreateAct;
	@FXML
	public Button btnCancel;
	String gender;

	Connector myConnector = new Connector();
	Connection myConn;
	String url = "jdbc:mysql://www.papademas.net:3306/fp510?autoReconnect=true&useSSL=false";
	String username = "fpuser", pwd = "510";

	
//Registeration details
	@FXML
	void createAccount(ActionEvent event) throws SQLException {
		myConn = myConnector.databaseConnection();
		String sql;
		PreparedStatement prepstatement;
		try {
			
			if (!txtName.getText().equals("") && !txtContact.getText().equals("") && !txtAddress.getText().equals("")
					&& !txtShipAddress.getText().equals("")) {
				sql = "INSERT INTO bookstorecustomerlist(memberId,name,gender,contact,address,shippingaddress)VALUES(?,?,?,?,?,?)";
				prepstatement = (PreparedStatement) myConn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				myConn = DriverManager.getConnection(url, username, pwd);

				if (male.isSelected()) {
					gender = "M";
				} else if (female.isSelected()) {
					gender = "F";
				}

				prepstatement.setInt(1, (int) (Math.random() * 50 + 1));
				prepstatement.setString(2, txtName.getText());
				prepstatement.setString(3, gender);
				prepstatement.setString(4, txtContact.getText());
				prepstatement.setString(5, txtAddress.getText());
				prepstatement.setString(6, txtShipAddress.getText());
				prepstatement.addBatch();
				prepstatement.executeBatch();
			}
			if (!txtUser.getText().equals("") && !txtPass.getText().equals("")) {
				sql = "INSERT INTO USER_VIJ(username,password) values(?,?)";
				prepstatement = (PreparedStatement) myConn.prepareStatement(sql);
				prepstatement.setString(1, txtUser.getText());
				prepstatement.setString(2, txtPass.getText());
				prepstatement.addBatch();
				prepstatement.executeBatch();
			}
			loadLoginPage();
			closeLoginWindow(btnCancel);
		} catch (Exception exc) {
			exc.printStackTrace();
		}

		
	}
//get out from the existing window and to go to the login page
	public void cancelBtn(ActionEvent event) {
		Stage stage=(Stage) btnCancel.getScene().getWindow();
		loadLoginPage();
		stage.close();

	}
}
