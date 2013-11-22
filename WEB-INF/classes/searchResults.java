import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import oracle.jdbc.driver.*;
import java.text.*;
import java.net.*;
import util.Db;

/**
 * This class displays the results of the indexd search by querying the
 * database which is including keyword and/or date search.
 */

public class searchResults extends HttpServlet implements SingleThreadModel {
    private Db database;
    private String keywords;
    private String fromDate;
    private String toDate; 
    private ResultSet rset;
    private String pid;

    protected void doGet(HttpServletRequest request, 
                         HttpServletResponse response) 
        throws ServletException, IOException {
	
        response.setContentType("text/html");
        database = new Db();
        database.connect_db();        
        keywords = request.getParameter("query");
        fromDate = request.getParameter("fromdate");
        toDate = request.getParameter("todate");
        pid = "";

        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Search Results</title>");

        /* 
         * Display header 
         */    
        try {
            request.getRequestDispatcher("includes/header.jsp").include( 
                                        request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        out.println("</head>");
        out.println("<body>");
        out.println("<br>");	
        /*
         * The user has to input from and to dates otherwise
         * only keyword search to get resultset of query
         */

        if (!(keywords.equals(""))) {
            if((fromDate.equals("")) || (toDate.equals(""))) {
                rset = database.getResultByKeywords(keywords);
                out.println("Your results for: '" + keywords + "'");
            } else {
                rset = database.getResultsByDateAndKeywords(fromDate, toDate,
                                                            keywords);
                out.println("Your results for: '" + keywords + "' Between: "
                            + fromDate + " and " + toDate);
            }
        } else if (!((fromDate.equals("")) || (toDate.equals("")))) {
            rset = database.getResultsByDate(fromDate, toDate);
            out.println("Your results for dates between: " + fromDate + " and "
                            + toDate);
        } else {
            out.println("<b>Please enter a search query</b>");
        }
        out.println("<br>");

        /*
         * Displays the results
         */
        try {
            while(rset.next()){
                pid = (rset.getObject(1)).toString();
                // specify the servlet for the image
                out.println("<a href=\"/c391proj/browsePicture?big" 
                            + pid + "\">");
                // specify the servlet for the thumbnail
                out.println("<img src=\"/c391proj/browsePicture?" 
                            + pid + "\"></a>");
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        database.close_db();
        out.print("</body>");
        out.print("</html>");
        out.close();
    }
}
