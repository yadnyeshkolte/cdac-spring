<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
		<%@ page session="true" %>
<%
    if (session.getAttribute("username") == null) {
        response.sendRedirect("login.jsp");
    }
%>

<h2>Welcome, ${sessionScope.username}</h2>

<h3>Products</h3>

<form action="addToCart" method="post">
    <input type="hidden" name="product" value="Laptop">
    <input type="submit" value="Add Laptop">
</form>

<form action="addToCart" method="post">
    <input type="hidden" name="product" value="Mobile">
    <input type="submit" value="Add Mobile">
</form>

<br>
<a href="cart.jsp">View Cart</a> |
<a href="logout">Logout</a>
		
</body>
</html>