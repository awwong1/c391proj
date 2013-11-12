import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import util.Db;

/**
 * Dummy servlet for loging in.
 * @author alexanderwwong
 */

public class loginservlet extends HttpServlet {
    private HttpSession session;
    private String eUsername;
    private String ePassword;
    private String tPassword;

    protected void service(HttpServletRequest request, 
			   HttpServletResponse response) 
	throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
	session = request.getSession(true);

        try {
            eUsername=request.getParameter("user");
            ePassword=request.getParameter("pass");

	} catch(Exception ex) {
	    out.println("<hr>" + ex.getMessage() + "<hr>");
	}

	Db database = new Db();
	database.connect_db();
	tPassword = database.get_password(eUsername);
	database.close_db();
	/**
	Connection conn = (Connection) session.getAttribute("conn");
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
	}*/

	if(ePassword.equals(tPassword)) {
	    out.println("<p><b>Login successful.</b></p>");
	    session.setAttribute("username", eUsername);
	    response.sendRedirect("/c391proj/index.jsp");
	} else {
	    String errormsg = "Username or Password invalid.";
	    session.setAttribute("err", errormsg);
	    response.sendRedirect("/c391proj/login.jsp");
	} 
	out.close();
    }
}