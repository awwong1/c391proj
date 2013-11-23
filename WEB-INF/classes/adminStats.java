import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import util.OLAPCommands;
/**
 * Handles the data cube stuff, all the stats about our photo things here
 */
public class adminStats extends HttpServlet {
    private OLAPCommands olap;
    
    public void doGet(HttpServletRequest request,
		      HttpServletResponse response)
	throws ServletException, IOException {
	HttpSession session;
	PrintWriter out = response.getWriter();
	out.println("<!DOCTYPE html><html><head><title>"+
		    "Admin OLAP Stats</title>");
	try {
	    session = request.getSession(true);
	    String username = (String) session.getAttribute("username");
	    if (!username.equals("admin")) {
		String msg = "You are not allowed to access admin stats.";
		session.setAttribute("err", msg);
		response.sendRedirect("/c391proj/index.jsp");
		return;
	    }
	    request.getRequestDispatcher("includes/header.jsp").
		include(request, response);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	out.println("</head></html>");
    }
}