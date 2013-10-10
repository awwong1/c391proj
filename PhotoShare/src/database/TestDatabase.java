/**
 * To run test, you MUST enter your Oracle username and password in the setUp method.
 */

package database;

import java.sql.ResultSet;
import java.sql.SQLException;

import junit.framework.TestCase;

import org.junit.Test;

import userManagement.User;

public class TestDatabase extends TestCase{
	private Db db;
	private User user;
	private String username;
	private String password;
	
	protected void setUp() {
		this.db = new Db();
		this.username = "";
		this.password = "";
		db.connect_db(this.username, this.password);
	}
	
	protected void tearDown() {
		db.close_db();
	}
	
	@Test
	public void test_connect() {
		assertNotSame(db.conn, null);
		assertNotSame(db.stmt, null);
	}
	
	@Test
	public void test_execute_stmt() throws SQLException {
		String query = "SELECT COUNT(*) FROM users " +
						"WHERE user_name IN ('mnaylor', 'awong', 'hhoang')";
		ResultSet rs = db.execute_stmt(query);
		rs.next();
		assertSame(rs.getInt(1), 3);
	}
	
	// Test fails with null pointer exception
	// db.close_db() works in tearDown however... curious...
	@Test
	public void test_close() {
		db.close_db();
	}
	
    public static void main(String[] args) {
        junit.textui.TestRunner.run(
            TestDatabase.class);
    }
}
