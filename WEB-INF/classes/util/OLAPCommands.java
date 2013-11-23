package util;

import util.Db;
import util.Timeframe;

/**
 * OLAP Commands for the olap report generation stats
 */
public class OLAPCommands {
    Db db;
    Timeframe status;

    public OLAPCommands(Timeframe timeframe) {
	this.db = new Db();
	this.status = timeframe;
    }

    /**
     * Returns the total numer of users for the specified timeframe
     */
    public int getNumberUsers() {
	int numUsers;
	db.connect_db();
	db.close_db();
	return 0;
    }
}