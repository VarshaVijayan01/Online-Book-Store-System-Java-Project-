package com.bookstore.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JOptionPane;

import com.mysql.jdbc.PreparedStatement;

import application.Connector;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MyController extends LoginPageLoader {
	@FXML
	private Pane pane1;

	@FXML
	public RadioButton rd1;
	@FXML
	public RadioButton rd2;
	@FXML
	public RadioButton rd3;

	
	@FXML
	public TextField txt2;
	@FXML
	public TextField txt4;
	@FXML
	public PasswordField pwd1;
	@FXML
	public PasswordField pwd2;
	@FXML
	public Button btn1;
	@FXML
	public Button btn2;
	@FXML
	public Button btn3;
	@FXML
	public Button reg;

	Connector myConnector = new Connector();
	Connection myConn;
	Statement myStmt;
	ResultSet rs = null;
//initialization
	public void initialize() {
		
		txt2.setEditable(false);
		pwd1.setEditable(false);
		
		txt4.setEditable(false);
		pwd2.setEditable(false);
		
	}
//disabling and setting textfields to be un editable
	public void ClickRadioButtons(ActionEvent event) {
		if (rd1.isSelected()) {
			
			txt2.setEditable(true);
			pwd1.setEditable(true);
			btn1.setDisable(false);
		} else if (rd2.isSelected()) {

			
			txt4.setEditable(true);
			pwd2.setEditable(true);
			btn2.setDisable(false);

		} else {
			JOptionPane.showMessageDialog(null,
					"Click on the above buttons to login");
		}
	}
//Login for admin
	public void btn1LoginAction(ActionEvent event) throws IOException {

		boolean flag = false;

		Alert alert = new Alert(AlertType.INFORMATION);
		if (!txt2.getText().equals("") && !pwd1.getText().equals("")){
			try {
				myConn = myConnector.databaseConnection();
				Statement myStmt = myConn.createStatement();
				String sql = ("Select * from user_vij where username='" + txt2.getText() + "'");
				PreparedStatement prepstatement = (PreparedStatement) myConn.prepareStatement(sql);

				rs = prepstatement.executeQuery();
				while (rs.next()) {
					if (rs.getString(1).equals(txt2.getText()) && rs.getString(2).equals(pwd1.getText())) {
						alert.setTitle("Successfully logged in!");
						alert.setHeaderText("LOGIN");
						alert.setContentText("You have successfully logged in!");
						alert.showAndWait();
						FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/bookstore/views/AdminHome.fxml"));
						Parent root1 = fxmlLoader.load();
						Stage stage1 = new Stage();
						stage1.setTitle("Admin Home!");
						stage1.setScene(new Scene(root1));
						closeLoginWindow(btn1);
						stage1.show();
						flag = true;
						break;
					}
				}
				if (!flag) {
					alert.setTitle("LOGIN ERROR!");
					alert.setHeaderText("LOGIN ERROR");
					alert.setContentText("Invalid username and password!");
					alert.showAndWait();
				}
			} catch (Exception e) {

				e.printStackTrace();
			}
		} else {
			alert.setTitle("LOGIN ERROR!");
			alert.setHeaderText("LOGIN ERROR");
			alert.setContentText("Invalid username and password!");
			alert.showAndWait();
		}
	}
//login for member
	public void btn2LoginAction(ActionEvent event) throws IOException {
		

		boolean flag = false;
		Alert alert = new Alert(AlertType.INFORMATION);
		if (!pwd2.getText().equals("") && !txt4.getText().equals("")&& !txt4.getText().equals("admin")) {
		
			myConn = myConnector.databaseConnection();
			try { // myConn= myConnector.databaseConnection();
				Statement myStmt = myConn.createStatement();
				String sql = ("Select * from user_vij where username='" + txt4.getText() + "'");
				PreparedStatement prepstatement = (PreparedStatement) myConn.prepareStatement(sql);

				rs = prepstatement.executeQuery();
				while (rs.next()) {
					if (rs.getString(1).equals(txt4.getText()) && rs.getString(2).equals(pwd2.getText())) {
						alert.setTitle("Successfully logged in!");
						alert.setHeaderText("LOGIN");
						alert.setContentText("You have successfully logged in!");
						alert.showAndWait();

						FXMLLoader fxmlLoader1 = new FXMLLoader(
								getClass().getResource("/com/bookstore/views/MainPageWindow.fxml"));
						Parent root2 = fxmlLoader1.load();
						Stage stage1 = new Stage();
						stage1.setTitle("Books-Main Page");
						stage1.setScene(new Scene(root2));
						closeLoginWindow(btn2);
						stage1.show();
						flag = true;
						break;
					}

				}
				if (!flag) {
					alert.setTitle("LOGIN ERROR!");
					alert.setHeaderText("LOGIN ERROR");
					alert.setContentText("Invalid email and password!");
					alert.showAndWait();
				}
				flag = false;

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			alert.setTitle("LOGIN ERROR!");
			alert.setHeaderText("LOGIN ERROR");
			alert.setContentText("Invalid email and password!");
			alert.showAndWait();
		}
	}
//opening of Registeration window
	public void btn3Action(ActionEvent event) {
		try {
			System.out.println("test");
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/bookstore/views/RegistrationWindow.fxml"));
			Parent root1 = fxmlLoader.load();
			Stage stage1 = new Stage();
			stage1.setTitle("Register");
			stage1.setScene(new Scene(root1));
			closeLoginWindow(btn3);
			stage1.show();

		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error while loading!");
		}

	}
	
	

}
