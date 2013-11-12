<!-- 
Webpage for logging in, displays form names and servlet error response messages
-->

<!DOCTYPE html>

<%  
   String error="";  
   try{  
     error=(String) session.getAttribute("err");  
   } catch(NullPointerException e) {
     error="";
   }        
%>  

<html>
  <head>
    <title>Login Page</title>
    <jsp:include page="includes/header.jsp"/>
  </head>
  <body>
    <form name="login" action="loginservlet" method="POST">
      <table>               
	<tbody>  
          <tr >  
            <td colspan="2"align="center">Returning user login:</td>  
          </tr>   
          <tr>  
            <td>Username:</td>  
            <td><input type="text" name="user" size="20" required="required" /></td>  
          </tr>  
          <tr>  
            <td>Password:</td>  
            <td><input type="password" name="pass" size="20" required="required" /></td>  
          </tr>  
          <tr >                          
            <td colspan="2" align="center"><input type="submit" value="Login" />  
              <input type="reset" value="Clear" /></td>  
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
