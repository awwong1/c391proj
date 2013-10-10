package userManagement;

import java.io.Console;

public class User {

	public String get_username() {
		Console console = System.console();
		String username = console.readLine("Enter username: ");
		return username;
	}
	
	public char[] get_password() {
		Console console = System.console();
		char[] password = console.readPassword("Enter password: ");
		return password;
	}
}
