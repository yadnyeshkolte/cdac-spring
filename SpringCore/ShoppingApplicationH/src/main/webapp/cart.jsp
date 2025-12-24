<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%@ page import="java.util.*" %>
<%
    ArrayList<String> cart = (ArrayList<String>) session.getAttribute("cart");
%>

<h2>Your Cart</h2>

<%
    if (cart == null || cart.isEmpty()) {
%>
        <p>Cart is empty</p>
<%
    } else {
        for (String item : cart) {
%>
            <p><%= item %></p>
<%
        }
    }
%>

<br>
<a href="products.jsp">Back to Products</a> |
<a href="logout">Logout</a>
	
</body>
</html>