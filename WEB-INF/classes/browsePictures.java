import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import oracle.jdbc.driver.*;
import java.text.*;
import java.net.*;

import util.Db;
import util.Photo;

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
	HttpSession session;
	String username = "";

	res.setContentType("text/html");
	database = new Db();
	database.connect_db();
	ArrayList<Photo> all_photos = database.getAllPhotos();
	
	PrintWriter out = res.getWriter();
	/* write out images */
	out.println("<!DOCTYPE html>");
	out.println("<html>");
	out.println("<head>");
	out.println("<title> Browse Photos </title>");
	try {
	    session = request.getSession(true);
	    username = (String) session.getAttribute("username");
	    request.getRequestDispatcher("includes/header.jsp").
		include(request, res);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	out.println("</head>");
	out.println("<body>"); 

	for (Photo photo: all_photos) {
	    out.println("<hr>");
	    // specify the servlet for the image
	    out.println("<a href=\"/c391proj/browsePicture?big"
			+ photo.getPhotoId() + "\">");
	    // specify the servlet for the thumbnail
	    out.println("<img src=\"/c391proj/browsePicture?"
			+ photo.getPhotoId() +
			"\"></a>");
	    if (photo.getOwnerName().equals(username)) {
		out.println("<a href=\"/c391proj/imageDesc.jsp?"
			    + photo.getPhotoId()
			    + "\">Image Descriptions</a>");
	    }
	}
	database.close_db();

	out.println("</body>");
	out.println("</html>");
	out.close();
    }
}



