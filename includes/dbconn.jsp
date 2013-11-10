<!--
Refactored files to isolate database connection data
Should be included in the header data
-->

<%@ page import="java.sql.*" %>

<%
  Connection conn;
  String dbString = "jdbc:oracle:thin:@gwynne.cs.ualberta.ca:1521:CRS";
  String driverName = "oracle.jdbc.driver.OracleDriver";

  String dbUser = "awwong1";	  /* change this to your username */
  String dbPass = "crimsonh34rt"; /* change this to your password */

  //load and register the driver
  try{
    Class drvClass = Class.forName(driverName); 
    DriverManager.registerDriver((Driver) drvClass.newInstance());
  }
    catch(Exception ex){
    out.println("<hr>" + ex.getMessage() + "<hr>");
  }
  try {
    conn = DriverManager.getConnection(dbString, dbUser, dbPass);
    session.setAttribute("conn", conn);
  } catch (Exception e) {
    out.println("<hr>" + e.getMessage() + "<hr>");
  }
%>