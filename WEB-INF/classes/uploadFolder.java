import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;
import oracle.sql.*;
import oracle.jdbc.*;
import util.User;
import util.Db;
import util.Photo;
import util.ImageUploader;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;

/**
 * Servlet to upload a folder of images
 *
*/

public class uploadFolder extends HttpServlet {
    private Db database;
    private HttpSession session;
    private String owner;
    public String response_message;
    
    public void doUpload(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {

	String date = "";
	String location = "";
	String subject = "";
	String description = "";
	int security = 2; // default is private
	
	ArrayList<FileItem> all_files = new ArrayList<FileItem>();
	DiskFileUpload fu = new DiskFileUpload();

	try {
	    /*
	     * Check if user is logged in, if not redirect
	     */
	    response.setContentType("text/html");
	    session = request.getSession(true);
	    owner = (String) session.getAttribute("username");
	    if(owner == null) {
		response.sendRedirect("login.jsp");
	    }

	    /*
	     * Parse the HTTP request to get the image stream
	     */
	    List<FileItem> FileItems = fu.parseRequest(request);

	    /*
	     * Process the uploaded items, assuming 1 image file uploaded
	     */
	    for (FileItem item : FileItems) {
		if (item.isFormField()) {
		    String fieldname = item.getFieldName();

		    if (fieldname.equals("date")) {
			date = item.getString();
		    }
		    else if (fieldname.equals("location")) {
			location = item.getString();
		    }
		    else if (fieldname.equals("subject")) {
			subject = item.getString();
		    }
		    else if (fieldname.equals("description")) {
			description = item.getString();
		    }
		    else if (fieldname.equals("security")) {
			String sec = item.getString();
			System.out.println("security = " + sec);
			security = Integer.parseInt(sec);
		    }
		} else {
		    all_files.add(item);
		}	     
	    }
	} catch (Exception e) {
	    response_message = e.getMessage();
	}

	Photo image = new Photo(0, owner, date, location, subject, 
				description, security);
	
	for (FileItem item : all_files) {
	    ImageUploader iu = new ImageUploader(image, item);
	    response_message = iu.upload_image();
	}

	//Output response to the client
	response.sendRedirect("index.jsp");
    }
    
}
