package application;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;


	public class Connector {
		public Connection databaseConnection() {
			//Creating object for connection.
			Connection myConn=null;
			
			try {
				Driver myDriver=new com.mysql.jdbc.Driver();
				DriverManager.registerDriver(myDriver);
				
			}
			
			catch(SQLException e) {
				System.out.println("Cant load mysql jdbc driver"+e);
			}
			
			String url;
			//url="jdbc:mysql://127.0.0.1:3306/?user=root";
		    //String username="root",pwd="root";
			url="jdbc:mysql://www.papademas.net:3306/fp510?autoReconnect=true&useSSL=false";
					String username="fpuser",pwd="510";
				//Get a database connection
				try{
					myConn=DriverManager.getConnection(url,username,pwd);
				System.out.println("Database Connected! \n");
				}
			catch(Exception e) {
				System.out.println("Error while Connecting!");	
			}
				 	
				//Returning the connection object.
				return myConn;
			}}





