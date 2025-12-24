<%@ page import="java.util.List" %>
<%@ page import="com.cdac.Books" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>Book List</title>
</head>
<body>

<h2>All Books</h2>

<a href="addBook.jsp">Add New Book</a>
<br><br>

<table border="1" cellpadding="8">
    <tr>
        <th>ID</th>
        <th>Title</th>
        <th>Author</th>
        <th>Price</th>
        <th>Actions</th>
    </tr>

    <%
        List<Books> list = (List<Books>) request.getAttribute("list");
        if (list != null) {
            for (Books b : list) {
    %>
        <tr>
            <td><%= b.getId() %></td>
            <td><%= b.getTitle() %></td>
            <td><%= b.getAuthor() %></td>
            <td><%= b.getPrice() %></td>
            <td>
                <a href="UpdateBookServlet?id=<%= b.getId() %>">Edit</a> |
                <a href="DeleteBookServlet?id=<%= b.getId() %>" 
                   onclick="return confirm('Are you sure you want to delete this book?');">
                   Delete
                </a>
            </td>
        </tr>
    <%
            }
        }
    %>
</table>

</body>
</html>
