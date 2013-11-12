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
    String user;

    public void init(ServletConfig config) throws ServletException { 
	super.init(config); 
    }

    protected void doGet(HttpServletRequest request, 
			 HttpServletResponse response)
	throws ServletException, IOException {

	String new_group = "";
	String new_friend = "";

	database = new Db();
	database.connect_db();

        response.setContentType("text/html;charset=UTF-8");
	session = request.getSession(true);
	user = (String) session.getAttribute("username");

	ArrayList<Group> group_list = database.get_groups(user);
	
	handle_write(request, response, group_list);

	// check new group value
	try {
	    new_group = request.getParameter("new_group");
	    System.out.println("new_group = " + new_group);
	} catch (Exception e) {
	    e.printStackTrace();
	}

	// add to new group to database
	if (new_group != null && !new_group.equals("New Group")) {
	    database.add_group(user, new_group);
	}
	
	/* check checkboxes in each group */
	for (Group group: group_list) {
	    String[] delete_friends = request.getParameterValues(group.getName());
	    if (delete_friends != null) {
		for (String friend : delete_friends) {
		    database.delete_friend(group.getId(), friend);
		}
	    }

	    // check for new friend
	    try {
		new_friend = request.getParameter(group.getName() + "new_friend");
		System.out.println("new friend = " + new_friend);
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	    if (new_friend != null && !new_friend.equals("New Friend")) {
		database.add_friend(group.getId(), new_friend);
	    }   
	}

	database.close_db();
    }	

    public void handle_write(HttpServletRequest request, HttpServletResponse response,
			     ArrayList<Group> group_list) {

	PrintWriter out = null;
	try {
	    out = response.getWriter();
	} catch (Exception e) {
	    e.printStackTrace();
	}

	out.println("<html><head>");
	out.println("<title>Manage Groups Page</title>");
	
	try {
	request.getRequestDispatcher("includes/header.jsp").
	    include(request, response);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	
	out.println("</head><form method='get' action='manage_Groups'>");
	out.println("<body><h2>My Groups: </h2>");
	
	for (Group group: group_list) {
	    out.println("<h3>" + group.getName() + "</h3>");
	    for (String friend: group.getFriends()) {
		out.println("<p><input type='checkbox' name='" 
			    + group.getName() + "'"
			    + "value='" + friend + "'>" + friend + "</p>");
	    }
	    out.println("<p><blockquote><input type='text' name = " + group.getName() 
			+ "new_friend value='New Friend'/></blockquote></p>");
	}
	
	out.println("<input type='text' name = 'new_group' value='New Group' size='20'/>");
	out.println("<p><input type='submit' value='Submit'/></p>");
	out.println("</body></html>");
	out.close();
    }
}

