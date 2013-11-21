<!-- 
Webpage for updating image descriptions
-->
<!DOCTYPE html>
<%@ page import="util.Db,util.Group,util.Photo,java.util.ArrayList"%>
<%
	String error = "";
        String username = "";
        ArrayList<Group> all_groups = new ArrayList<Group>();
	try{
		error = (String) session.getAttribute("err");
                session.setAttribute("err", "");
                username = (String) session.getAttribute("username");
	} catch (NullPointerException e) {
		e.printStackTrace();
	}

        Db database = new Db();
        database.connect_db();
        all_groups = database.get_groups(username);
        database.close_db();
%>

<html>
  <head>
    <title>Image Desciptions</title>
      <jsp:include page="includes/header.jsp"/>
  </head>
  <body>
    <form name="uploadimage" action="imageDesc" enctype="multipart/form-data" 
	  method="POST">
      <table>
	<% if (!error.equals("")) {
	   out.println("<tr>" + error + "</tr>");
	   }
	   %>
        <tr>
           <th>Date: </th>
             <td>
                <input name="date" type="date" placeholder="DD-MMM-YY"></input>
             </td>
        </tr>

        <tr>
           <th>Location: </th>
             <td>
                <input name="location" type="text" placeholder="location"></input>
             </td>
        </tr>

        <tr>
           <th>Subject: </th>
             <td>
                <input name="subject" type="text" placeholder="subject"></input>
             </td>
        </tr>

        <tr>
           <th>Description: </th>
             <td>
		<textarea name="description" cols="25" rows="5" 
			  placeholder="Description"></textarea>
             </td>
        </tr>

        <tr>
           <th>Security: </th>
             <td>
		<p><input type="radio" name="security" value="2">
		Private</input></p>
		<p><input type="radio" name="security" value="1">Public</input></p>
		<% for (Group group: all_groups) {
		     out.println("<p><input type='radio' name='security' value='" 
		   + group.getId()
		   + "'>" + group.getName() + "</input></p>");
		   }
		   %>
             </td>
        </tr>
	<tr>
	  <td ALIGN=CENTER COLSPAN="2"><input type="submit" name=".submit"
		value="Upload">
	  </td>
	</tr>
      </table>
    </form>
  </body>
</html>
