package com.cdac;

import java.io.IOException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UpdateStudentServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private StudentDAO dao = new StudentDAO();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int id = Integer.parseInt(request.getParameter("id"));
        Student s = dao.getStudent(id);   
        request.setAttribute("student", s);
        request.getRequestDispatcher("update.jsp").forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String mobile = request.getParameter("mobile");

        Student s = new Student(id, name, email, mobile);

        try {
            StudentDAO.updateStudent(s);   
        } 
        catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("viewStudents.jsp");
		
	}

}
