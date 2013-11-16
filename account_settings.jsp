<!--
Webpage for handling account information in the persons table, such as
username, firstname, lastname, address, email, phone
-->

<!DOCTYPE html>
<%
   String error = null;
   String username = null;
   String email = null;
   String firstname = null;
   String lastname = null;
   String address = null;
   String phone = null;
   String password = null;
   try {
      error = (String) session.getAttribute("err");
      username = (String) session.getAttribute("username");
   } catch (NullPointerException e) {
      e.printStackTrace();
   }
   // cannot access this page unless logged in
   if (username == null) {
      String errormsg = "Please log in before accessing account information";
      session.setAttribute("err", errormsg);
      response.sendRedirect("/c391proj/login.jsp");
   }
   // prepopulate the fields with user information if none exist
   email = (String) session.getAttribute("email");
   firstname = (String) session.getAttribute("firstname");
   lastname = (String) session.getAttribute("lastname");
   address = (String) session.getAttribute("address");
   phone = (String) session.getAttribute("phone");
   if (email==null) {
      session.setAttribute("isPopulated", 0);
      response.sendRedirect("/c391proj/manageUser");
   }
%>

<html>
  <head>
    <title>Account Settings</title>
    <jsp:include page="includes/header.jsp"/>
  </head>
  <body>
    <form name="manageUser" action="manageUser" method="POST" >
      <table>
	<tr>
	  <td colspan="2" align="center">Account Information</td>
	</tr>
	<tr>
	  <td>Username:</td>
	  <td><input type="text" name="username" maxlength="24" 
		     placeholder="username"
		     required="required" 
		     value=<% out.write(username);%> />
	  </td>
	</tr>
	<tr>
	  <td>Email:</td>
	  <td><input type="text" name="email" maxlength="128"
		     placeholder="email"
		     required="required" 
		     value=<% out.write(""+email);%> />
	  </td>
	</tr>
	<tr>
	  <td>First Name:</td>
	  <td><input type="text" name="firstname" maxlength="24"
		     placeholder="first name"
		     value=<% out.write(""+firstname); %>
		     required="required" />
	  </td>
	</tr>
	<tr>
	  <td>Last Name:</td>
	  <td><input type="text" name="lastname" maxlength="24"
		     placeholder="last name"
		     value=<% out.write(""+lastname); %>
		     required="required" />
	  </td>
	</tr>
	<tr>
	  <td>Address:</td>
	  <td><input type="text" name="address" maxlength="128"
		     placeholder="address"
		     value=<% out.write(""+address); %>
		     required="required" />
	  </td>
	</tr>
	<tr>
	  <td>Phone:</td>
	  <td><input type="text" name="phone" maxlength="10"
		     placeholder="phone"
		     value=<% out.write(""+phone); %>
		     required="required" />
	  </td>
	</tr>
      </table>
    </form>
  </body>
</head>
