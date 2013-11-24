package util;

import java.sql.ResultSet;
import java.sql.Date;
import java.util.Calendar;
import util.Db;

/**
 * OLAP Commands for the olap report generation stats
 */
public class OLAPCommands {
    Db db;
    String timeframe;

    public OLAPCommands(String tframe) {
	this.db = new Db();
	this.timeframe = tframe;
    }
    /**
     * Tries to parse out a column title for the row's results
     * in the statement execution
     */
    public String getColTitle(Calendar mydate) {
	String title = "";
	if (timeframe.equals("monthly")) {
		    title = title +" Month: " + Integer.
			toString(mydate.get(Calendar.MONTH)+1);
	}
	if (timeframe.equals("weekly")) {
	    title = title + " Week: " + Integer.
		toString(mydate.get(Calendar.WEEK_OF_YEAR));
	}
	if (timeframe.equals("daily")) {
	    title = title + " Day: " + 
		Integer.toString(mydate.get(Calendar.DATE)) + " " +
		Integer.toString(mydate.get(Calendar.MONTH));
	}
	title = title+" "+Integer.toString(mydate.get(Calendar.YEAR));
	return title;
    }
    
    /**
     * Returns the total count of users registered for
     * the specified timeframe format
     */
    public String getRegUsersCount() {
	String query = "SELECT date_registered as timeframe, "+
	    "user_name from users order by " +
	    "timeframe";
	String result = "";
	String title = "";
	String oldTitle = "";
	Calendar mydate = Calendar.getInstance();
	ResultSet rset;
	int datecounter = 0;
	boolean firstRun = true;
	db.connect_db();
	try {
	    rset = db.execute_stmt(query);
	    result = result + "<table border='1'>";
	    while (rset.next()) {
		mydate.setTime(rset.getDate(1));
		title = getColTitle(mydate);
		if (!title.equals(oldTitle)) {
		    if (firstRun == true) {
			firstRun = false;
			oldTitle = title;
			result = result + "<tr><th>"+title+"</th>";
			datecounter++;
			continue;
		    }
		    else {
			oldTitle = title;
			result = result+"<td>"+
			    Integer.toString(datecounter)+"</td></tr>";
			result = result + "<tr><th>"+title+"</th>";
			datecounter = 0;
		    }
		}
		datecounter++;
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
	result = result +"<td>"+
	    Integer.toString(datecounter)+"</td></tr></table>";
	db.close_db();
	return result;
    }

    /**
     * Returns the total details of users registered 
     * for the specified timeframe format
     */
    public String getRegUsers() {
	String query = "SELECT date_registered as timeframe, "+
	    "user_name from users order by "+
	    "timeframe";
	String result = "";
	Calendar mydate = Calendar.getInstance();
	String title = "";
	String oldTitle = "";
	ResultSet rset;
	db.connect_db();
	try {
	    rset = db.execute_stmt(query);
	    result = result + "<table border='1'>";
	    while(rset.next()) {
		// Table Row Section Title Stuff Here
		mydate.setTime(rset.getDate(1));
		// Set the title stuff
		title = getColTitle(mydate);
		// Check if we should add the title in
		if (!title.equals(oldTitle)) {
		    oldTitle = title;
		    result = result+"<tr><th>"+title+"</th></tr>";
		}
		// Add in the users
		result = result +"<tr><td>"+rset.getString(2)+"</td></tr>";
	    }
	    result = result + "</table>";
	} catch (Exception e) {
	    e.printStackTrace();
	}
	db.close_db();
	return result;
    }

    /**
     * Returns the total number of photos uploaded
     * for the specific timeframe format
     */
    public String getDateUploadImages() {
	String query = "SELECT timing as timeframe, photo_id, owner_name, "+
	    "subject, place, description from images "+
	    "order by timeframe";
	String result = "";
	String title = "";
	String oldTitle = "";
	Calendar mydate = Calendar.getInstance();
	ResultSet rset;
	db.connect_db();
	try {
	    rset = db.execute_stmt(query);
	    result = result + "<table border='1'>";
	    while(rset.next()) {
		mydate.setTime(rset.getDate(1));
		title = getColTitle(mydate);
		if (!title.equals(oldTitle)) {
		    oldTitle = title;
		    result=result+"<tr><th>"+title+"</th></tr>";
		}
		// Add in the images
		result = result + "<tr><td>"+
		    "<a href=\"/c391proj/browsePicture?big"+rset.getString(2)+
		    "\">" + "<img src=\"/c391proj/browsePicture?"+
		    rset.getString(2)+"\"></a></td><td>"+
		    rset.getString(3)+"<br>"+
		    rset.getString(4)+"<br>"+
		    rset.getString(5)+"<br>"+
		    rset.getString(6)+"<br>"+
		    "</td>"+
		    "</tr>";
	    }
	    result = result + "</table>";
	} catch (Exception e) {
	    e.printStackTrace();
	}
	db.close_db();
	return result;
    }
}