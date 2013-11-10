
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Dummy servlet for loging in.
 * @author alexanderwwong
 */
public class login_Servlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String error;
            String uname,pass;
            String Pass_DB,Level_DB,Uname_DB;
            
            uname=request.getParameter("user");
            pass=request.getParameter("pass");
	    
	} finally {            
            out.close();
        }
    }
}