import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Dummy servlet for loging in.
 * @author alexanderwwong
 */

public class loginservlet extends HttpServlet {
    private Connection conn = null;
    private String driverName = "oracle.jdbc.driver.OracleDriver";
    private String dbString = 
	"jdbc:oracle:thin:@gwynne.cs.ualberta.ca:1521:CRS";
    private String eUsername;
    private String ePassword;
    private String tPassword;

    protected void service(HttpServletRequest request, 
			   HttpServletResponse response) 
	throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            eUsername=request.getParameter("user");
            ePassword=request.getParameter("pass");

	} catch(Exception ex) {
	    out.println("<hr>" + ex.getMessage() + "<hr>");
	}
	
	//load and register the driver
	try{
	    Class drvClass = Class.forName(driverName); 
	    DriverManager.registerDriver((Driver) drvClass.newInstance());
	}
	catch(Exception ex){
	    out.println("<hr>" + ex.getMessage() + "<hr>");
	}
	
	// Establish the connection to the database... badly
	try{
	    conn = DriverManager.getConnection(dbString,"awwong1",
					       "crimsonh34rt");
	    conn.setAutoCommit(false);
	}
	catch(Exception ex){
	    out.println("<hr>" + ex.getMessage() + "<hr>");
	}

	Statement stmt = null;
	ResultSet rset = null;
	String sql = "select password from users where  user_name='" + 
	    eUsername + "'";
	out.println(sql);
	try{
	    stmt = conn.createStatement();
	    rset = stmt.executeQuery(sql);
	    while(rset != null && rset.next()) {
		tPassword = (rset.getString(1)).trim();
	    }
	}
	catch(Exception ex){
	    out.println("<hr>" + ex.getMessage() + "<hr>");
	}	     
	try{
	    conn.close();
	}
	catch(Exception ex){
	    out.println("<hr>" + ex.getMessage() + "<hr>");
	}
	if(ePassword.equals(tPassword)) {
	    out.println("<p><b>Login successful.</b></p>");
	    HttpSession session = request.getSession(true);
	    session.setAttribute("username", eUsername);
	    response.sendRedirect("/c391proj/index.jsp");
	} else {
	    out.println("<p><b>Username or Password invalid.</b></p>");
	    response.sendRedirect("/c391proj/login.jsp");
	} 
	out.close();
    }
}