package edu.umkc.team6.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCConnection {

	private Connection connection;
	private Statement statement;

	public JDBCConnection() {
		connect();
	}
	public void connect(){

		try {

			Class.forName("org.postgresql.Driver");

		} catch (ClassNotFoundException e1) {

			System.out.println("Driver not found");
			e1.printStackTrace();

		}

		connection = null;

		try {

			connection = DriverManager.getConnection(
					"jdbc:postgresql://localhost:5432/CarRental", "postgres",
					"123456");
			statement = connection.createStatement();

		} catch (SQLException e) {

			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();

		}

		if (connection != null) {
			System.out.println("Successfully connected!");
		} else {
			System.out.println("Failed to make connection!");
		}
		
	}
	public void disconnect(){
		try {		
			connection.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	public Statement getStatement(){
		return statement;
	}
	
}
