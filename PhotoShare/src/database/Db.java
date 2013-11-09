package database;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import userManagement.Group;
import userManagement.User;

public class Db {
	// JDBC driver name and database URL
	static final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";  
	static final String DB_URL = 
						"jdbc:oracle:thin:@gwynne.cs.ualberta.ca:1521:CRS";
	
	public Connection conn = null;
	public Statement stmt = null;
	
	/**
	 * 
	 * @param USERNAME
	 * @param PASSWORD
	 */
	public void connect_db(String USERNAME, String PASSWORD) {
		try {
			Class drvClass = Class.forName(DRIVER_NAME); 
			DriverManager.registerDriver((Driver) drvClass.newInstance());

		    System.out.println("Connecting to database...");
		    
		    this.conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
		    this.stmt = conn.createStatement();
		    System.out.println("Connected.");
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
	
	/**
	 * 
	 */
	public void close_db() {
		try {
			this.stmt.close();
			this.conn.close();
			System.out.println("Disconnected from database.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param query
	 * @return
	 */
	public Integer execute_update(String query) {
			try {
				return this.stmt.executeUpdate(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
	}
	
	
	public ArrayList<Group> get_groups(User user) {
		ResultSet rs;
		String query = "SELECT group_id, group_name "
				       + "FROM groups"
				       + "WHERE user_name = "
				       + user.getUsername();
		rs = execute_stmt(query);
		return group_from_resultset(rs);
	}
	
	/**
	 * Gets groups (and users in groups) from a resultset
	 * @param rs
	 * @return
	 */
	public ArrayList<Group> group_from_resultset(ResultSet rs) {
		ArrayList<Group> groups;
		groups = new ArrayList<Group>();
		Group temp_group;
		
		try {
			while (rs.next()) {
				String group_name = rs.getString("group_name");
				int group_id = rs.getInt("group_id");
				temp_group = new Group(group_name, group_id);
				groups.add(temp_group);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		for (Group group: groups) {
			ArrayList<User> users;
			users = get_users_from_group(group);
			group.setFriends(users);
		}
		return groups;
	}
	
	public ArrayList<User> get_users_from_group(Group group) {
		ResultSet rs;
		String query = "SELECT friend_id "
				       + "FROM group_lists "
				       + "WHERE group_id = "
				       + group.getId();
		rs = execute_stmt(query);
		return user_from_resultset_group(rs);
	}
	
	/**
	 * Gets users in a group
	 * @param rs
	 * @return
	 */
	public ArrayList<User> user_from_resultset_group(ResultSet rs) {
		ArrayList<User> all_users;
		all_users = new ArrayList<User>();
		User temp_user;
		
		try {
			while (rs.next()) {
				String friend_id = rs.getString("friend_id");
				temp_user = new User(friend_id);
				all_users.add(temp_user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return all_users;
	}
	
	/**
	 * 
	 * @param query
	 * @return
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
