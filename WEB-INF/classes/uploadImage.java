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
 * Servlet to upload a single image
 *
*/

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;

public class uploadImage extends HttpServlet {
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
	    InputStream instream = null;
	    DiskFileUpload fu = new DiskFileUpload();
	    List<FileItem> FileItems = fu.parseRequest(request);

	    /*
	     * Process the uploaded items, assuming 1 image file uploaded
	     */
	    for (FileItem item : FileItems) {
		if (!item.isFormField()) {
		    instream = item.getInputStream();
		} else {
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
	    
	    /*
	     * Get Image Stream
	     */
	    BufferedImage img = ImageIO.read(instream);
	    BufferedImage thumbNail = shrink(img, 10);
			
	    /*
	     *Connect to the database and create a statement
	     */
	    database = new Db();
	    database.connect_db();
	    	    
	    if (date == null) {
		date = "sysdate";
	    }

	    /*
	     * Insert a empty blob into the table
	     */
	    int image_id = database.get_next_image_id();
	    database.addEmptyImage(owner, security, subject, location, 
				   description, image_id, date);
	    
	    /*
	     * Get Blob from database
	     */
	    Blob myImage = database.getImageById(image_id, "photo");
	    Blob myThumb = database.getImageById(image_id, "thumbnail");	
	    
	    /*
	     * Write thumbnail image into a Blob object
	     */ 
	    OutputStream outstream = myThumb.setBinaryStream(0);
	    ImageIO.write(thumbNail, "jpg", outstream);
	    /*
	     * Write image into a Blob object
	     */	
	    outstream = myImage.setBinaryStream(0);
	    byte[] buffer = new byte[2048];
	    int length = -1;
	    while ((length = instream.read(buffer)) != -1)
		outstream.write(buffer, 0, length);

	    instream.close();
	    outstream.close();
	    response_message = " File has been Uploaded!    ";
	    database.close_db();
	    
	} catch (Exception e) {
	    response_message = e.getMessage();
	}	
	
	//Output response to the client
	response.sendRedirect("index.jsp");
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
