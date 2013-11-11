import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import util.User;
import util.Db;
import util.Group;

public class manage_Groups extends HttpServlet {
    Db database;
    HttpSession session;
    Connection conn;
    Statement stmt;

    public void init(ServletConfig config) throws ServletException { 
	super.init(config); 
	database = new Db();
	database.connect_db();
    }

    protected void doGet(HttpServletRequest request, 
			 HttpServletResponse response)
	throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
	session = request.getSession(true);

	PrintWriter out = response.getWriter();
	out.println("<html><head>");
	out.println("<title>Manage Groups Page</title>");

	request.getRequestDispatcher("includes/header.jsp").
	    include(request, response);

	out.println("</head><form method='get' action='manage_Groups'>");
	out.println("<body><h2>My Groups: </h2>");

        /* get user from session variables
         for now, user is mnaylor
	*/
	ArrayList<Group> group_list = database.get_groups("mnaylor");
	//out.println("size of group_list: " + group_list.size());
	
	for (Group group: group_list) {
	    out.println(group.getName());
	    for (String friend: group.getFriends()) {
		out.println("<p><input type='checkbox' name='friends' "
			    + "value='" + friend + "'>" + friend + "</p>");
	    }
	}

	out.println("<input type='text' value='New Group' size='20'/>");
	out.println("<input type='submit' value='Submit'/>");
	out.println("</body></html>");

	database.close_db();
    }	
}