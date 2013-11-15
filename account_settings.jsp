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
   // cannot access this page unless logged in
   if (username != null) {
      response.sendRedirect("/c391proj/index.jsp");
   }
   // prepopulate the fields with user information if none exist
   try {
      email = (String) session.getAttribute("email");
   } catch (NullPointerException e) {

   }
%>

<html>
  <head>
    <title>Account Settings</title>
    <jsp:include page="includes/header.jsp"/>
  </head>
  <body>
    <form name="updateAccount" action="updateAccount" method="POST">
    </form>
  </body>
</head>
