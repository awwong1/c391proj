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
    String user = "";
    ArrayList<Group> group_list;

    public void init(ServletConfig config) throws ServletException { 
	super.init(config); 
    }

    protected void doGet(HttpServletRequest request, 
			 HttpServletResponse response)
	throws ServletException, IOException {
	HttpSession session;
	String invalid_friend = "";

	database = new Db();
	database.connect_db();

        response.setContentType("text/html;charset=UTF-8");
	session = request.getSession(true);
	user = (String) session.getAttribute("username");
	
	/* if no user logged in, redirect to login page*/
	if (user == null) {
	    response.sendRedirect("login.jsp");
	}

	group_list = database.get_groups(user);
	
	check_new_group(request);
	
	/* if invalid_friend, user tried to add a friend that is not
	and actual user
	*/  
	invalid_friend = check_friend_changes(request, group_list);

	/* update group_list after changes */
	group_list = database.get_groups(user);

	database.close_db();
	handle_write(request, response, group_list, invalid_friend);
    }	

    public void check_new_group(HttpServletRequest request) {
	String new_group = "";
	/* check new group value */
	try {
	    new_group = request.getParameter("new_group");
	} catch (Exception e) {
	    e.printStackTrace();
	}
	
	/* add to new group to database */
	if (new_group != null && !new_group.isEmpty()) {
	    database.add_group(user, new_group);
	}
    }

    public String check_friend_changes(HttpServletRequest request,
				       ArrayList<Group> group_list) {
	String new_friend = "";
	String invalid_friend = "";
	/* check checkboxes in each group */
	for (Group group: group_list) {
	    String[] delete_friends = request.getParameterValues(group.getName());
	    if (delete_friends != null) {
		for (String friend : delete_friends) {
		    database.delete_friend(group.getId(), friend);
		}
	    }

	    /* check for new friend */
	    try {
		new_friend = request.getParameter(group.getName() + "new_friend");
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	    if (new_friend != null) {
		Integer success = database.add_friend(group.getId(), new_friend);
		if (success == 0) {
		    invalid_friend = new_friend;
		}
	    }
	}
	return invalid_friend;
    }

    public void handle_write(HttpServletRequest request, 
			     HttpServletResponse response,
			     ArrayList<Group> group_list,
			     String invalid_friend) {

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
	    out.println("<p>&nbsp;&nbsp;&nbsp;<input type='text' name = " 
			+ group.getName() 
			+ "new_friend placeholder='New Friend'/>"
			+ "</blockquote></p>");
	}
	
	out.println("<input type='text' name = 'new_group' "
		    + "placeholder='New Group' size='20'/>");
	out.println("<p><input type='submit' value='Submit'/></p>");
	if (!invalid_friend.isEmpty()) {
	    out.println("<p>Unable to add friend " + invalid_friend
			+ ". Username not found");
	}
	out.println("</body></html>");
	out.close();
    }
}

