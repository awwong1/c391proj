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
 * 
 *
 */


public class searchResults extends HttpServlet implements SingleThreadModel {
    private Db database;
    
    
    protected void doGet(HttpServletRequest request, 
                         HttpServletResponse response) 
        throws ServletException, IOException {

        response.setContentType("text/html");
        database = new Db();
        database.connect_db();        
        ResultSet rset = database.getResultByKeywords(request.getParameter("query"));

        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Search Results</title>");

        /* 
         * Display header 
         */    
        try {
            request.getRequestDispatcher("includes/header.jsp").include(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        out.println("</head>");
        out.println("<body>");

        try {
            /*
             * Get query from user and displays the results
             * TODO index is apparently not indexed
             */ 
            String pid = "";
            if (request.getParameter("query") != null) {
                out.println("<br>");
                out.println("Your results for: " + request.getParameter("query"));
                out.println("<br>");    
                if (!(request.getParameter("query").equals(""))) {
                    out.println("Query is " + request.getParameter("query"));
                    while(rset.next()){
                        pid = (rset.getObject(1)).toString();
                        // specify the servlet for the image
                        out.println("<a href=\"/c391proj/browsePicture?big" + pid + "\">");
                        // specify the servlet for the thumbnail
                        out.println("<img src=\"/c391proj/browsePicture?" + pid + "\"></a>");
                    }
                } else {
                   out.println("<br><b>Please enter a search query</b>"); 
                }
            }
            database.close_db();
        } catch (Exception e) {
            e.getStackTrace();
        }        
        out.print("</body");
        out.print("</html");
        out.close();
    }
}
