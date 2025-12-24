<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>Edit Book</title>
</head>
<body>

<h2>Edit Book</h2>

<%
    com.cdac.Books b = (com.cdac.Books) request.getAttribute("book");
    if (b == null) {
        out.println("Book not found!");
        return;
    }
%>

<form action="UpdateBookServlet" method="post">

    <input type="hidden" name="id" value="<%= b.getId() %>">

    Title: <input type="text" name="title" value="<%= b.getTitle() %>" required><br><br>
    Author: <input type="text" name="author" value="<%= b.getAuthor() %>" required><br><br>
    Price: <input type="number" step="0.01" name="price" value="<%= b.getPrice() %>" required><br><br>

    <button type="submit">Update Book</button>
</form>

<br>
<a href="ViewBookServlet">Back to Book List</a>

</body>
</html>
