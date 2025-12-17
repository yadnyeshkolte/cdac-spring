<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registration</title>
</head>
<body style="text-align: center;">
	<h2>Student Registration</h2>
	<form action="register" method="post">
		Student Id: <input type="number" name="id"
			placeholder="Enter Student Id"><br> Name: <input
			type="text" name="name" placeholder="Enter Student Name"><br>
		Email <input type="email" name="email" placeholder="Enter Mail id"><br>
		Course: <input type="text" name="course" placeholder="Enter Course"><br>
		<input type="submit" value="Register"
			style="background-color: blue; color: white; border-radius: 15px;">
	</form>
</body>
</html>