<!--
Webpage for handling account information in the persons table, such as
username, firstname, lastname, address, email, phone
-->

<!DOCTYPE html>
<%
   String error = null;
   String username = null;
   try {
      error = (String) session.getAttribute("err");
      username = (String) session.getAttribute("username");
   } catch (NullPointerException e) {
      e.printStackTrace();
   }
   if (username != null) {
      response.sendRedirect("/c391proj/index.jsp");
   }
%>

<html>
  <head>
    <title>Account Settings</title>
    <jsp:include page="includes/header.jsp"/>
  </head>
  <body>
  </body>
</head>
