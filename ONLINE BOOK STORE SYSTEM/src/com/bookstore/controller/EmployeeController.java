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
import com.bookstore.domain.Employee;
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

public class EmployeeController extends LoginPageLoader implements Initializable {

	@FXML
	public TableView<Employee> empTable;
	@FXML
	public TableColumn<?, ?> empId;
	@FXML
	public TableColumn<?, ?> empName;
	@FXML
	public TableColumn<?, ?> empEmail;
	@FXML
	public TableColumn<?, ?> empPhone;
	@FXML
	public TableColumn<?, ?> empAddress;

	@FXML
	public TextField txtempId;
	@FXML
	public TextField txtempName;
	@FXML
	public TextField txtempEmail;
	@FXML
	public TextField txtempPhone;
	@FXML
	public TextField txtempAddress;
	@FXML
	public Button btnAdd;
	@FXML
	public Button btnAddNewEmp;
	@FXML
	public Button btnUpdate;
	@FXML
	public Button btnUpdateEmp;
	@FXML
	public Button btnDelete;
	@FXML
	public Button btnCancel;

	ObservableList<Employee> employees;

	PreparedStatement preparedstatement = null;
	ResultSet rs = null;
	Connector myConnector = new Connector();
	Connection myConn;
	Statement myStmt;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		employees = FXCollections.observableArrayList();
		getAllEmp();
	}

	public void setCellTable() {
		empId.setCellValueFactory(new PropertyValueFactory<>("empId"));
		empName.setCellValueFactory(new PropertyValueFactory<>("empName"));
		empEmail.setCellValueFactory(new PropertyValueFactory<>("empEmail"));
		empPhone.setCellValueFactory(new PropertyValueFactory<>("empContact"));
		empAddress.setCellValueFactory(new PropertyValueFactory<>("empAddress"));
	}

	private void getAllEmp() {

		myConn = myConnector.databaseConnection();
		try { // myConn= myConnector.databaseConnection();
			Statement myStmt = myConn.createStatement();
			String sql = ("Select * from employee_vij");
			PreparedStatement prepstatement = (PreparedStatement) myConn.prepareStatement(sql);

			rs = prepstatement.executeQuery();
			while (rs.next()) {
				employees.add(
						new Employee(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
			}
			System.out.println("Testasdf" + Arrays.asList(employees));
			setCellTable();
			empTable.setItems(employees);
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
		}
	}

	public void AddEmp(ActionEvent event) throws SQLException {
		btnAddNewEmp.setDisable(false);
		// empId.setEditable(true);
		enableDisableUpdateFields(true);

	}

	public void AddNewEmp(ActionEvent event) throws SQLException {
		myConn = myConnector.databaseConnection();

		try {

			Alert alert = new Alert(AlertType.INFORMATION);
			if (!txtempName.getText().equals("") && !txtempEmail.getText().equals("")
					&& !txtempPhone.getText().equals("") && !txtempAddress.getText().equals("")) {
				// myConn= myConnector.databaseConnection();
				Statement myStmt = myConn.createStatement();
				String sql = ("INSERT INTO employee_vij(empName,empEmail,empContact,empAddress)VALUES(?,?,?,?)");
				PreparedStatement prepstatement = (PreparedStatement) myConn.prepareStatement(sql);
				// myConn = DriverManager.getConnection(url, username, pwd);
				System.out.println("empName.getText()" + txtempId.getText() + txtempEmail.getText()
						+ txtempPhone.getText() + txtempAddress.getText());
				prepstatement.setString(1, txtempName.getText());
				prepstatement.setString(2, txtempEmail.getText());
				prepstatement.setString(3, txtempPhone.getText());
				prepstatement.setString(4, txtempAddress.getText());
				prepstatement.addBatch();
				prepstatement.executeBatch();
				JOptionPane.showMessageDialog(null, "Employee Record Successfully Inserted!");
				// tableView1.getColumns();
				prepstatement.close();
				// rs.close();
				// myConn.close();
				ClearRecords();
				reloadTableData();
				enableDisableUpdateFields(false);
				btnAddNewEmp.setDisable(true);
				empId.setEditable(false);
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

	public void updateRecors(ActionEvent event) {
		Books booktable2 = new Books();
		
		try {
			Alert alert = new Alert(AlertType.INFORMATION);
			if (!txtempName.getText().equals("") && !txtempEmail.getText().equals("")
					&& !txtempPhone.getText().equals("") && !txtempAddress.getText().equals("")) {
				int empId = Integer.parseInt(txtempId.getText());
				myConn = myConnector.databaseConnection();
				Statement myStmt = myConn.createStatement();
				String sql = "UPDATE employee_vij SET empName = ?, empEmail = ?, empContact= ?, empAddress=?  WHERE empId = ?";
				PreparedStatement prepstatement = (PreparedStatement) myConn.prepareStatement(sql);

				prepstatement.setString(1, txtempName.getText());
				prepstatement.setString(2, txtempEmail.getText());
				prepstatement.setString(3, txtempPhone.getText());
				prepstatement.setString(4, txtempAddress.getText());
				prepstatement.setInt(5, empId);
				prepstatement.addBatch();
				prepstatement.executeUpdate();
				prepstatement.close();
				ClearRecords();
				reloadTableData();
				enableDisableUpdateFields(false);
				btnUpdate.setDisable(true);
			} else {
				alert.setTitle("Update Employee Error!");
				alert.setHeaderText("Update Employee Error");
				alert.setContentText("Invalid data for Employee!");
				alert.showAndWait();

			}
		} catch (Exception e) {
			e.printStackTrace();
		} // txtArea1.setText("BookId :+ " bookId +"Book Name :"+ bookName+
			// "Book Author :" +bookAuthor +"Price :"+ price );

	}

	public void fillTextbox(ActionEvent event) {
		Employee emp = empTable.getSelectionModel().getSelectedItem();
		try {

			enableDisableUpdateFields(true);
			btnUpdate.setDisable(false);
			txtempId.setText("" + emp.getEmpId());
			txtempName.setText(emp.getEmpName());
			txtempEmail.setText(emp.getEmpEmail());
			txtempPhone.setText(emp.getEmpContact());
			txtempAddress.setText(emp.getEmpAddress());
		} catch (Exception e) {
			e.printStackTrace();
		} // txtArea1.setText("BookId :+ " bookId +"Book Name :"+ bookName+
			// "Book Author :" +bookAuthor +"Price :"+ price );

	}

	public void btnCancel(ActionEvent event) {
		txtempId.setText("");
		txtempName.setText("");
		txtempEmail.setText("");
		txtempPhone.setText("");
		txtempAddress.setText("");
	}
	public void ClearRecords() {

		txtempId.setText("");
		txtempName.setText("");
		txtempEmail.setText("");
		txtempPhone.setText("");
		txtempAddress.setText("");
		empTable.getItems().clear();
	}

	private void enableDisableUpdateFields(boolean isDisable) {
		txtempName.setEditable(isDisable);
		txtempEmail.setEditable(isDisable);
		txtempPhone.setEditable(isDisable);
		txtempAddress.setEditable(isDisable);
		btnCancel.setDisable(!isDisable);
	}

	public void RemoveBooks(ActionEvent event) {
		myConn = myConnector.databaseConnection();

		try {

			// myConn= myConnector.databaseConnection();
			Statement myStmt = myConn.createStatement();

			Employee booktable = empTable.getSelectionModel().getSelectedItem();
			String sql = ("DELETE from employee_vij WHERE empId=?");
			PreparedStatement prepstatement = (PreparedStatement) myConn.prepareStatement(sql);
			// myConn = DriverManager.getConnection(url, username, pwd);
			prepstatement.setInt(1, booktable.getEmpId());
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
	
	private void reloadTableData() {
		empTable.getItems().clear();
		getAllEmp();
	}
	
	public void logout(ActionEvent event) {
		loadLoginPage();
		closeLoginWindow(btnCancel);
	}
	
	public void homePage(ActionEvent event) {
		loadHomeWindow();
		closeLoginWindow(btnCancel);
		
	}
}
