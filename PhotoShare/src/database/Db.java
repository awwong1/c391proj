package database;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Db {
	// JDBC driver name and database URL
	static final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";  
	static final String DB_URL = 
						"jdbc:oracle:thin:@gwynne.cs.ualberta.ca:1521:CRS";
	
	public Connection conn = null;
	public Statement stmt = null;
	
	/*
	 * Connects to the database
	 * Sets the conn and stmt class variables
	*/
	public void connect_db(String USERNAME, String PASSWORD) {
		try {
			Class drvClass = Class.forName(DRIVER_NAME); 
			DriverManager.registerDriver((Driver) drvClass.newInstance());

		    System.out.println("Connecting to database...");
		    
		    this.conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
		    this.stmt = conn.createStatement();
		    
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Closes class conn and stmt variables
	 */
	public void close_db() {
		try {
			this.conn.close();
			this.stmt.close();
			this.conn = null;
			this.stmt = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Executes SQL update (insert, delete, etc.) statements
	 * Returns number of updated rows, or 0 if sql return is 0
	 */
	public Integer execute_update(String query) {
			try {
				return this.stmt.executeUpdate(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
	}
	
	/*
	 * Executes sql query 
	 * Returns ResultSet
	 */
	public ResultSet execute_stmt(String query) {
		try {
			return this.stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
