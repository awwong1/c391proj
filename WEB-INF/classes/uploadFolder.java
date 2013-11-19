import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;
import oracle.sql.*;
import oracle.jdbc.*;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import util.User;
import util.Db;

/**
 * Servlet to upload a folder of images
 *
*/

import org.apache.commons.fileupload.*;

public class uploadFolder extends HttpServlet {
    private Db database;
    private HttpSession session;
    private String owner;
    public String response_message;
    private String TEMPDIR = "/tmp/";
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {

	String date = "";
	String location = "";
	String subject = "";
	String description = "";
	int security = 2; // default is private
	
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
		}
	    }
	} catch (Exception e) {
	    response_message = e.getMessage();
	}
	
	File[] all_jpg = get_files();
	

	InputStream instream = null;
	for (File img: all_jpg) {
	    
	}

	    
	//Output response to the client
	response.sendRedirect("index.jsp");
    }
    
    /**
     * Gets all .png files in the TEMPDIR directory
     * @returns File[]
     */
    private File[] get_files() {
	File[] all_jpg = [];
	for (File file : TEMPDIR.listFiles()) {
	    if (fileEntry.isFile() && file.getName().endsWith(".jpg")) {
		all_jpg.add(file);
	    }
	}
	return all_jpg;
    }

    /*
     * Strink function
     * http://www.java-tips.org/java-se-tips/java.awt.image/shrinking-an-image-by-skipping-pixels.html
     */
    public static BufferedImage shrink(BufferedImage image, int n) {
	
	int w = image.getWidth() / n;
	int h = image.getHeight() / n;
	
	BufferedImage shrunkImage = new BufferedImage(w, h, image.getType());
	
	for (int y=0; y < h; ++y)
	    for (int x=0; x < w; ++x)
		shrunkImage.setRGB(x, y, image.getRGB(x*n, y*n));
	
	return shrunkImage;
    }
}
