<!DOCTYPE html>
<html>
<head>
    <title>Welcome</title>
</head>
<body>

 <h2>EMAIL <%= session.getAttribute("emailId") %></h2>
<h2>PASSWORD <%= session.getAttribute("password") %></h2>  

<%-- <%
	String email = (String)request.getAttribute("email");
	String password = (String)request.getAttribute("password");	
%> 


<h2>EMAIL: <%= email %></h2>
<h2>PASSWORD: <%= password %></h2> --%>





</body>
</html>
