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
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
	
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
		if (!item.isFormField()) {
		    String filename = item.getName().toLowerCase();
		    String ext = filename.substring(filename.length() - 3);
		    if (ext.equals("jpg") || ext.equals("gif")) {
			all_files.add(item);
		    }
		}	     
	    }
	} catch (Exception e) {
	    response_message = e.getMessage();
	}

	if (all_files.size() == 0) {
	    response_message = "You must have at least one file to upload.";
	    session.setAttribute("err", response_message);
	    response.sendRedirect("uploadFolder.jsp");
	}
	session.setAttribute("folderPhotos", all_files);
    }
    
}
