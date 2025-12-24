<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Book</title>
</head>
<body>

<h2>Add New Book</h2>

<form action="AddBookServlet" method="post">
    Title: <input type="text" name="title" required><br><br>
    Author: <input type="text" name="author" required><br><br>
    Price: <input type="number" name="price" step="0.01" required><br><br>

    <button type="submit">Add Book</button>
</form>

<br>
<a href="ViewBookServlet">View All Books</a>

</body>
</html>
