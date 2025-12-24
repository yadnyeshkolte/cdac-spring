<%@ page import="java.util.*, com.cdac.Student" %>

<h2>All Registered Students</h2>

<table border="1" cellpadding="10">
    <tr>
        <th>ID</th><th>Name</th><th>Email</th><th>Mobile</th>
    </tr>

<%
    List<Student> list = (List<Student>) request.getAttribute("studentList");
    for (Student s : list) {
%>
    <tr>
        <td><%= s.getId() %></td>
        <td><%= s.getName() %></td>
        <td><%= s.getEmail() %></td>
        <td><%= s.getMobile() %></td>
        <td>
                <a href="UpdateStudentServlet?id=<%= s.getId() %>">Edit</a>
 
            </td>
    </tr>
<%
    }
%>
</table>

<br>
<a href="register.html">Register Another Student</a>