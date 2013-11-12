<!--
Webpage for registering a new user. Only displays if not logged in.
-->

<!DOCTYPE html>

<%
  String error = "";
  try {
    error = (String) session.getAttribute("err");
  } catch (NullPointerException e) {
    error = "";
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
	      <input type="text" name="pass" size="20" required="required" />
	    </td>
	  </tr>
	  <tr>
	    <td>Confirm Password:</td>
	    <td> 
	      <input type="text" name="pass2" size="20" required="required" />
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
