<!-- 
Webpage for uploading images
-->
<!DOCTYPE html>

<%
	String error = "";
        String username;
        String getURL = "";
	try{
		error = (String) session.getAttribute("err");
                session.setAttribute("err", "");
                username = (String) session.getAttribute("username");
                getURL=request.getRequestURL().toString();
	} catch (NullPointerException e) {
		e.printStackTrace();
	}

        int index = getURL.lastIndexOf("/");
        getURL = getURL.substring(0, index) + "/uploadFolder";
%>

<html>
  <head>
    <title>Upload Page</title>
      <jsp:include page="includes/header.jsp"/>
  </head>
  <body>
    <form name="uploadimage" action="folderDesc" enctype="multipart/form-data" 
	  method="POST">
      <% if (!error.equals("")) {
	 out.println(error + "<br>");
	 }
	 %>

      <applet code="applet-basic_files/wjhk.JUploadApplet" name="JUpload" 
	      archive="applet-basic_files/wjhk.jar" mayscript="" 
	      height="300" width="640">
	<param name="CODE" value="wjhk.jupload2.JUploadApplet">
	<param name="ARCHIVE" value="wjhk.jupload.jar">
	<param name="type" value="application/x-java-applet;version=1.4">
	<param name="scriptable" value="false">    
	<param name="postURL"
	       value=<%=getURL%>>
	<param name="nbFilesPerRequest" value="2">    
	Java 1.4 or higher plugin required.
      </applet>

      <table>
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
		<p><input type="radio" name="security" value="2" checked="checked">
		    Private</input></p>
		<p><input type="radio" name="security" value="1">Public</input></p>
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
