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
	int groupId = 1;

	res.setContentType("text/html");
	database = new Db();
	database.connect_db();
		
	PrintWriter out = res.getWriter();
	/* write out images */
	out.println("<!DOCTYPE html>");
	out.println("<html>");
	out.println("<head>");
	out.println("<title> Browse Public Photos </title>");
	try {
	    session = request.getSession(true);
	    username = (String) session.getAttribute("username");
	    groupId = Integer.parseInt(request.getParameter("group"));
	    request.getRequestDispatcher("includes/header.jsp").
		include(request, res);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	ArrayList<Photo> all_photos = database.
	    getAllPermissionPhotos(groupId, username);

	out.println("</head>");
	out.println("<body><table border=\"1\">"); 
	for (Photo photo: all_photos) {
	    out.println("<tr>");
	    out.println("<td>");
	    // specify the servlet for the image
	    out.println("<a href=\"/c391proj/browsePicture?big"
			+ photo.getPhotoId() + "\">");
	    // specify the servlet for the thumbnail
	    out.println("<img src=\"/c391proj/browsePicture?"
			+ photo.getPhotoId() +
			"\"></a>");
	    out.println("</td><td>");
	    out.println("PhotoID: " + photo.getPhotoId() + "<br>");
	    out.println("Owned by: " + photo.getOwnerName() + "<br>");
	    out.println("Date Uploaded: " + photo.getDate() + "<br>");
	    out.println("Location: " + photo.getLocation() + "<br>");
	    out.println("Subject: " + photo.getSubject() + "<br>");
	    out.println("Description: " + photo.getDescription() + "<br>");
	    out.println("Image View Count: " +
			database.imageCountView(photo.getPhotoIdString())+
			"<br>");
	    if (photo.getOwnerName().equals(username) || 
		username.equals("admin")) {
		out.println("<a href=\"/c391proj/imageDesc.jsp?"
			    + photo.getPhotoId()
			    + "\">Edit Image Description</a>");
	    }
	    out.println("</td></tr>");
	}
	database.close_db();

	out.println("</body>");
	out.println("</html>");
	out.close();
    }
}



