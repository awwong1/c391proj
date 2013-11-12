<!--
Webpage for registering a new user. Only displays if not logged in.
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
    <title>Register Page</title>
    <jsp:include page="includes/header.jsp"/>
  </head>
  <body>
    <form name="register" action="registerservlet" method="POST">
      <table>
	<tbody>
	  <tr>
	    <td colspan="2" align="center"> New user registration: </td>
	  </tr>
	  <tr>
	    <td>New Username:</td>
	    <td> 
	      <input type="text" name="user" size="20" required="required" />
	    </td>
	  </tr>
	  <tr>
	    <td>New Password:</td>
	    <td> 
	      <input type="password" name="pass" size="20" required="required" />
	    </td>
	  </tr>
	  <tr>
	    <td>Confirm Password:</td>
	    <td> 
	      <input type="password" name="pass2" size="20" required="required" />
	    </td>
	  </tr>
	  <tr>
	    <td colspan="2" align="center">
	      <input type="submit" value="Register" />
	      <input type="reset" value="Clear" />
	    </td>
	  </tr>
	  <tr>
	    <td colspan="2" align="center">
	      <%
		 if (error != null) {
		   out.println(error);
		   session.setAttribute("err", null);
		 }
	      %>
	    </td>
	  </tr>
	</tbody>
      </table>
    </form>
  </body>
</html>
