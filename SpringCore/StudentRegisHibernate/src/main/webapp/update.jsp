<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Update Student InFo</title>
</head>
<body>
	<form action="UpdateStudentServlet" method="post">
    ID: <input type="text" name="id" value="${student.id}" readonly><br>
    Name: <input type="text" name="name" value="${student.name}"><br>
    Email: <input type="text" name="email" value="${student.email}"><br>
    Mobile: <input type="text" name="mobile" value="${student.mobile}"><br>

    <button type="submit">Update</button>
</form>
	
	<br>
<a href="view">Back to Student List</a>
</body>
</html>