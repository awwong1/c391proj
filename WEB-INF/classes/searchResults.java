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
    private String keywords; 

    protected void doGet(HttpServletRequest request, 
                         HttpServletResponse response) 
        throws ServletException, IOException {

        response.setContentType("text/html");
        database = new Db();
        database.connect_db();        
        keywords = request.getParameter("query");
//        ResultSet rsetUpdate = database.updateImageIndex();
        ResultSet rset = database.getResultByKeywords(keywords);

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

            /*
             * Get query from user and displays the results
             */ 
        String pid = "";
        if (keywords != null) {
            out.println("<br>");
            out.println("Your results for: " + keywords);
            out.println("<br>");    
            if (!(keywords.equals(""))) {
                out.println("Query is " + keywords);
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
            } else {
                    out.println("<br><b>Please enter a search query</b>"); 
            }
        }
        database.close_db();
        out.print("</body>");
        out.print("</html>");
        out.close();
    }
}
