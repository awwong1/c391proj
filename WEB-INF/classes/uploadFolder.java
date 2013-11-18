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
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {

	//Initialization for chunk management.
	boolean bLastChunk = false;
	int numChunk = 0;

	// Directory to store all the uploaded files
	String ourTempDirectory = "/tmp/";
	int ourMaxMemorySize  = 10000000;
	int ourMaxRequestSize = 2000000000;

	String date = "";
	String location = "";
	String subject = "";
	String description = "";
	int security = 2; // default is private
	
	/*
	 *Connect to the database and create a statement
	 */
	database = new Db();
	database.connect_db();

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

	    Enumeration paraNames = request.getParameterNames();
	    String pname;
	    String pvalue;
	    while (paraNames.hasMoreElements()) {
		pname = (String)paraNames.nextElement();
		pvalue = request.getParameter(pname);
		if (pname.equals("jufinal")) {
		    bLastChunk = pvalue.equals("1");
		} 
		else if (pname.equals("jupart")) {
		    numChunk = Integer.parseInt(pvalue);
		}
	    }

	    // Create a factory for disk-based file items
	    DiskFileItemFactory factory = new DiskFileItemFactory();
	
	    // Set factory constraints
	    factory.setSizeThreshold(ourMaxMemorySize);
	    factory.setRepository(new File(ourTempDirectory));
	
	    // Create a new file upload handler
	    ServletFileUpload upload = new ServletFileUpload(factory);
	
	    // Set overall request size constraint
	    upload.setSizeMax(ourMaxRequestSize);

	    /*
	     * Parse the HTTP request to get the image stream
	     */
	    List items = upload.parseRequest(request);
	    Iterator iter = items.iterator();
	    FileItem fileItem;
	    File fout;

	    while (iter.hasNext()) {
		fileItem = (FileItem) iter.next();
		
		if (fileItem.isFormField()) {
		    String fieldname = fileItem.getName();
		
		    if (fieldname.equals("date")) {
			date = fileItem.getString();
		    }
		    else if (fieldname.equals("location")) {
			location = fileItem.getString();
		    }
		    else if (fieldname.equals("subject")) {
			subject = fileItem.getString();
		    }
		    else if (fieldname.equals("description")) {
			description = fileItem.getString();
		    }
		    else if (fieldname.equals("security")) {
			String sec = fileItem.getString();
			security = Integer.parseInt(sec);
		    }
		} else {
		    String uploadedFilename = fileItem.getName()
			+ ( numChunk>0 ? ".part"+numChunk : "") ;
		    fout = new File(ourTempDirectory
				    + (new File(uploadedFilename)).getName());
		    // write the file
		    fileItem.write(fout);
		}
	    }
	} catch (Exception e) {
	    response_message = e.getMessage();
	}

	if (bLastChunk) {
	    FileInputStream instream;
	    int nbBytes;
	    byte[] byteBuff = new byte[1024];
	    String filename;
	    
	    for (int i=1; i<=numChunk; i+=1) {
		filename = fileItem.getName() + ".part" + i;
		instream = new FileInputStream(ourTempDirectory + filename);
		
		/*
		 * Get Image Stream
		 */
		BufferedImage img = ImageIO.read(instream);
		BufferedImage thumbNail = shrink(img, 10);
		
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
		outstream.close();
	    }
	    instream.close();
	}
	
	response_message = " File has been Uploaded!    ";
	database.close_db();
		
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
