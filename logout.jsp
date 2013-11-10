<!DOCTYPE html>

<!--
Handles loging out by destroying the session variable.
-->

<%@ page import="javax.servlet.http.*" %>

<html>
  <head>
    <title>Logout</title>
    <jsp:include page="includes/header.jsp"/>
  </head>
  <body>
    <%
      if (session.getAttribute("username") != null) {
        session.invalidate();
	out.println("Logged out");
      } else {
      	out.println("No session to log out");
      }
    %>
  </body>
</html>
