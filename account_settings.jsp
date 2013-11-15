<!--
Webpage for handling account information in the persons table, such as
username, firstname, lastname, address, email, phone and password changing
-->

<!DOCTYPE html>
<%
   String error = null;
   String username = null;
   String password = null;
   String email = null;
   String firstname = null;
   String lastname = null;
   String address = null;
   String phone = null;

   try {
      error = (String) session.getAttribute("err");
      username = (String) session.getAttribute("username");
   } catch (NullPointerException e) {}
   // cannot access this page unless logged in
   if (username != null) {
      response.sendRedirect("/c391proj/index.jsp");
   }
   // prepopulate the fields with user information, else populate
   try {
      email = (String) session.getAttribute("email");
      firstname = (String) session.getAttribute("fname");
      lastname = (String) session.getAttribute("lname");
      address = (String) session.getAttribute("address");
      phone = (String) session.getAttribute("phone");
      password = (String) session.getAttribute("password");
   } catch (NullPointerException e) {
      response.sendRedirect("/c391proj/manageUser");
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
