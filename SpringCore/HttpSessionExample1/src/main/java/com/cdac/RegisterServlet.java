package com.cdac;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class RegisterServlet extends HttpServlet
{

	public void doPost(HttpServletRequest request , HttpServletResponse response  ) throws IOException, ServletException
	{
		          response.setContentType("text/html");
		          PrintWriter out=response.getWriter();
		          
		          HttpSession session = request.getSession(false);
		          
		          if(session == null || session.getAttribute("username") == null){
		        	  response.sendRedirect("login.html");
		              return;
		          }

		          
		          
		          
		          String uname =request.getParameter("name");
		          String course =request.getParameter("course");
		          String email =request.getParameter("email");
		          
		          
		          session.setAttribute("sname", uname);
		          session.setAttribute("scourse", course);
		          session.setAttribute("semail", email);

		          
		          
		          RequestDispatcher rd =request.getRequestDispatcher("view.jsp");
		          rd.forward(request, response);
		          
		          
		          
		          
//    	        	  out.println("<h1> Registration details ! </h1>"  );
//  	        	     out.println("<p> " + uname + "</p>");
//  	        	   out.println("<p> " + sid + "</p>");
//  	        	 out.println("<p> " + mail + "</p>");
//		        	
		         // out.println("<h1> Invalid user name or password  ! login again  </h1>"  );
		        
		          }
	
	
}
