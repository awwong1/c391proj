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

public class folderDesc extends HttpServlet {
    private Db database;
    private HttpSession session;
    private String owner;
    public String response_message;
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
	String date = "";
	String location = "";
	String subject = "";
	String description = "";
	int security = 2; // default is private
	ArrayList<FileItem> all_files = null;
	try {
	    session = request.getSession(true);
	    owner = (String) session.getAttribute("username");
	    all_files = 
		(ArrayList<FileItem>) session.getAttribute("folderPhotos");
	} catch (Exception e) {
	    response_message = e.getMessage();	    
	}

	DiskFileUpload fu = new DiskFileUpload();
	try {
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
			security = Integer.parseInt(sec);
		    }
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

	response.sendRedirect("index.jsp");
    }
    
}
