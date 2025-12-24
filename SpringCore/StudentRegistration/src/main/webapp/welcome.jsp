<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registration Page</title>
</head>
<body>
<%
String Std_id = (String)request.getAttribute("Std_ID");
String Std_name = (String)request.getAttribute("Std_Name");	
String emailid = (String)request.getAttribute("emailId");
String Gender = (String)request.getAttribute("Gender");	
String Course = (String)request.getAttribute("Course");	
%> 


<h2>STUDENT ID : <%= Std_id %></h2>
<h2>STUDENT NAME : <%= Std_name %></h2>
<h2>EMAIL : <%= emailid %></h2>
<h2>GENDER : <%= Gender %></h2>
<h2>COURSE : <%= Course %></h2>

</body>
</html>