<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="com.cdac.Student" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Student Info</title>
</head>
<body>

<%
    // Fetch student object from request
    Student s = (Student) request.getAttribute("Stud");
%>

<h2>Student Information</h2>

<p><strong>ID:</strong> <%= s.getStd_ID() %></p>
<p><strong>Name:</strong> <%= s.getStd_Name() %></p>
<p><strong>Email:</strong> <%= s.getEmailId() %></p>

</body>
</html>
