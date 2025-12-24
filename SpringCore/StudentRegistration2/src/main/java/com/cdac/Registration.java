package com.cdac;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/Registration")
public class Registration extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String Std_ID = request.getParameter("Std_ID");
        String Std_Name = request.getParameter("Std_Name");
        String emailId = request.getParameter("emailId");
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        Student s = new Student(Std_ID,Std_Name,emailId);

        StudentDao dao = new StudentDao();
        int status = dao.save(s);

        if (status > 0) {
            request.setAttribute("Stud", s);
            request.getRequestDispatcher("view.jsp").forward(request, response);
        } else {
            response.getWriter().println("Insert Failed!");
        }
        
	}

}
