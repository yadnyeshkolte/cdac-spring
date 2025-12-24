<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="true" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Shopping Application</title>
</head>
<body>

<%
    // Check if user already logged in
    if (session.getAttribute("username") != null) {
        response.sendRedirect("products.jsp");
    } else {
        response.sendRedirect("login.jsp");
    }
%>

</body>
</html>
