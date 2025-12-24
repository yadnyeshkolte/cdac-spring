<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Success</title>
</head>
<body>

<h2>Book Added Successfully!</h2>

<p><strong>Title:</strong> ${book.title}</p>
<p><strong>Author:</strong> ${book.author}</p>
<p><strong>Price:</strong> ${book.price}</p>

<br><br>

<a href="addBook.jsp">Add Another Book</a><br>
<a href="ViewBookServlet">View All Books</a>

</body>
</html>
