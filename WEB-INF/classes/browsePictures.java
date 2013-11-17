import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import oracle.jdbc.driver.*;
import java.text.*;
import java.net.*;

import util.Db;

/**
 * Extension of the simple servlet used to browse thumbnails
 * Taken from Li-Yan Yuan's example code
 * @author Alexander Wong, Li-Yan Yuan
 */
public class browsePictures extends HttpServlet implements SingleThreadModel {
    Db database;
    String p_id;

    public void doGet(HttpServletRequest request,
		      HttpServletResponse res)
	throws ServletException, IOException {
	res.setContentType("text/html");
	database = new Db();
	database.connect_db();
	ResultSet rset = database.getAllPhotoIds();

	PrintWriter out = res.getWriter();
	/* write out images */
	out.println("<!DOCTYPE html>");
	out.println("<html>");
	out.println("<head>");
	out.println("<title> Browse Photos </title>");
	try {
	    request.getRequestDispatcher("includes/header.jsp").
		include(request, res);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	out.println("</head>");
	out.println("<body>");
	try {
	    while (rset.next()) {
		out.println("<hr>");
		p_id = (rset.getObject(1)).toString();
		// specify the servlet for the image
		out.println("<a href=\"/c391proj/browsePicture?big"+p_id+"\">");
		// specify the servlet for the thumbnail
		out.println("<img src=\"/c391proj/browsePicture?"+p_id +
			    "\"></a>");
	    }	
	    database.close_db();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	out.println("</body>");
	out.println("</html>");
	out.close();
    }
}




