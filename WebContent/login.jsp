<!DOCTYPE html>

<%  
    String error=" ";  
    try{  
        error=(String)request.getAttribute("err");  
    }catch(NullPointerException e){error=" ";}        
%>  

<html>
  <head>
    <title>Login Page</title>
    <jsp:include page="includes/header.jsp"/>
  </head>
  <body>
    <form name="login" action="login_Servlet" method="POST">
      <table>               
	<tbody>  
          <tr >  
            <td colspan="2"align="center"> Enter your Login Detail</td>  
          </tr>   
          <tr>  
            <td>UserName:</td>  
            <td><input type="text" name="us er" value="" size="20" required="required" /></td>  
          </tr>  
          <tr>  
            <td>Password:</td>  
            <td><input type="password" name="pass" value="" size="20" required="required" /></td>  
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
		 }
	      %>
	    </td>  
	</tbody> 
      </table>
    </form>
  </body>
</html>
