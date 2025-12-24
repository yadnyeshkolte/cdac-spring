package com.cdac;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ViewStudentsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

    	 StudentDAO sd = new StudentDAO();
    	
    	 List<Student> list=null;
	
			list = sd.getAllStudents();
		

        req.setAttribute("studentList", list);
        
        RequestDispatcher rd = req.getRequestDispatcher("viewStudents.jsp");
        rd.forward(req, resp);
    }
}

