package database;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestDatabase {
	Db db;
	
	protected void setUp() {
		this.db = new Db();
	}
	
	protected void tearDown() {
		db.close_db();
	}
	
	@Test
	public void test_connect() {
		db.connect_db("user", "pwd");
		assertNotSame(db.conn, null);
		assertNotSame(db.stmt, null);
	}
	
	public void test_close() {
		db.close_db();
		assertSame(db.conn, null);
		assertSame(db.conn, null);
	}

}
