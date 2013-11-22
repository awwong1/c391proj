import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

import util.Db;
import util.Photo;

/**
 *  Extension of the simple servlet used to display photos
 *  Taken from Li-Yan Yuan's example code
 * 
 *  This servlet sends one picture stored in the table below to the client 
 *  who requested the servlet.
 *
 *   picture( photo_id: integer, title: varchar, place: varchar, 
 *            sm_image: blob,   image: blob )
 *
 *  The request must come with a query string as follows:
 *    GetOnePic?12:        sends the picture in sm_image with photo_id = 12
 *    GetOnePic?big12: sends the picture in image  with photo_id = 12
 *
 *  @author  Alexander Wong, Li-Yan Yuan
 *
 */
public class browsePicture extends HttpServlet 
    implements SingleThreadModel {
    private Db database;
    
    /**
     *    This method first gets the query string indicating PHOTO_ID,
     *    and then executes the query 
     *          select image from yuan.photos where photo_id = PHOTO_ID   
     *    Finally, it sends the picture to the client
     */

    public void doGet(HttpServletRequest request,
		      HttpServletResponse response)
	throws ServletException, IOException {
	database = new Db();
	database.connect_db();
	//  construct the query  from the client's QueryString
	String picid  = request.getQueryString();
	ResultSet rset = null;
	PrintWriter out = response.getWriter();
	if (picid.startsWith("big")) {
	    database.imageCountViewInc(picid.substring(3));
	    rset = database.getPhoto(picid.substring(3));
	} else
	    rset = database.getThumbnail(picid);
       	try {
	    if (rset.next()) {
		response.setContentType("image/gif");
		InputStream input = rset.getBinaryStream(1);	    
		int imageByte;
		while((imageByte = input.read()) != -1) {
		    out.write(imageByte);
		}
		input.close();
	    } 
	    else 
		out.println("no picture available");
	} catch( Exception ex ) {
	    out.println(ex.getMessage() );
	}
	// to close the connection
	finally {
	    database.close_db();
	    out.close();
	}
    }
}
