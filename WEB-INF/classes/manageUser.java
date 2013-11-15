import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import util.Db;

/**
 * Class for handling data within the persons table, such as:
 * username, firstname, lastname, address, email, phone, and
 * changing password.
 * @author alexanderwwong
 */

public class manageUser extends HttpServlet {
    private HttpSession session;
    private String eUsername;
    private String ePassword;
    private String eFirstname;
    private String eLastname;
    private String eAddress;
    private String eEmail;
    private String ePhone;

    protected void service(HttpServletRequest request,
			   HttpServletResponse response)
	throws ServletException, IOException {
	response.setContentType("text/html");
	session = request.getSession(true);
	
	// Check if the user is logged in
	eUsername = session.getAttribute("username");
	if (eUsername == null) {
	    String errormsg = "Please login before accessing account info";
	    session.setAttribute("err", errormsg);
	    response.sendRedirect("/c391proj/login.jsp");
	    return;
	}
    }
}