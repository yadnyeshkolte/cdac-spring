<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>View</title>
</head>
<body>
	<c:forEach var="s" items="${Slist}">
		<h2>Id: ${s.getId()}</h2>
		<h2>Name: ${s.getName()}</h2>
		<h2>Email: ${s.getEmail()}</h2>
		<h2>Course: ${s.getCourse()}</h2>
		<hr />
	</c:forEach>
	<a href="register">Back To Register</a>
</body>
</html>