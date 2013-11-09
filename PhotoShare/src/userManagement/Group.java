package userManagement;

import java.util.ArrayList;

public class Group {
	String name;
	int id;
	ArrayList<User> friends;
	
	public Group(String name, int id, ArrayList<User> friends) {
		this.name = name;
		this.id = id;
		this.friends = friends;
	}

	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	public int getId() {return id;}
	public void setId(int id) {this.id = id;}
	public ArrayList<User> getFriends() {return friends;}
	public void setFriends(ArrayList<User> friends) {this.friends = friends;}
		
}
