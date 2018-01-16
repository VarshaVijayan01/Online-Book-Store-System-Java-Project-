package com.bookstore.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LoginPageLoader {

	public void loadLoginPage() {
		try {

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/bookstore/views/LoginWindow.fxml"));
			Parent root1 = fxmlLoader.load();
			Stage stage1 = new Stage();
			stage1.setTitle("LOGIN PAGE ONLINE BOOK STORE SYSTEM");
			stage1.setScene(new Scene(root1));
			stage1.show();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void closeLoginWindow(Button btn) {
		Stage stage = (Stage) btn.getScene().getWindow();
		stage.close();

	}

	public void loadHomeWindow() {

		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/bookstore/views/AdminHome.fxml"));
			Parent root1 = fxmlLoader.load();
			Stage stage1 = new Stage();
			stage1.setTitle("Admin Home!");
			stage1.setScene(new Scene(root1));
			stage1.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
