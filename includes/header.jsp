<!--
Assume necessary html definitions are already made
Author: Alexander Wong
-->
<%@ page import="javax.servlet.http.*" %>
<!--<%@ include file="dbconn.jsp" %>-->
<% 
   String username = (String) session.getAttribute("username");
   if (username != null) {
      out.println("Welcome, " + username);
   }
%>
<p>
<a href="/c391proj/index.jsp">home</a> | 
<%
    if (username == null) {
        out.println("<a href=\"/c391proj/login.jsp\">login</a>");
    } else {
        out.println("<a href=\"/c391proj/logout.jsp\">logout</a>"); 
    }
%>
</p>