import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import util.Db;

/**
 * Dummy servlet for registering a new user.
 * @author alexanderwwong
 */

public class registerservlet extends HttpServlet {
    private HttpSession session;
    private Db database;
    private String eUsername;
    private String ePassword;
    private String ePassword2;
    private String errorMsg;

    protected void service(HttpServletRequest request,
			   HttpServletResponse response) 
	throws ServletException, IOException {
	response.setContentType("text/html");
	PrintWriter out = response.getWriter();
	session = request.getSession(true);
	database = new Db();
	database.connect_db();
	
	
	// Grab the variables from the request
	try {
	    eUsername = request.getParameter("user");
	    ePassword = request.getParameter("pass");
	    ePassword2 = request.getParameter("pass2");
	} catch (Exception e) {
	    returnClose(e.getMessage());
	    response.sendRedirect("/c391proj/register.jsp");
	    return;
	}

	// Check if a user exists with the same username
	if (database.userExists(eUsername)) {
	    errorMsg = "Username already exists";
	    returnClose(errorMsg);
	    response.sendRedirect("/c391proj/register.jsp");
	    return;
	}
	// Check if the passwords match
	if (!ePassword.equals(ePassword2)) {
	    errorMsg = "Passwords do not match";
	    returnClose(errorMsg);
	    response.sendRedirect("/c391proj/register.jsp");
	    return;
	}
	// Add the new user to the database, notify that user is added
	database.addUser(eUsername, ePassword);
	errorMsg = "Sucessfully registered, please log in.";
	returnClose(errorMsg);
	response.sendRedirect("/c391proj/register.jsp");
	return;
    }

    private void returnClose(String emsg) {
	database.close_db();
	session.setAttribute("err", emsg);
	return;
    }
}