package com.cdac;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/StudentInfo")
public class StudentInfo extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String Std_ID = request.getParameter("Std_ID");
        String Std_Name = request.getParameter("Std_Name");
        String emailId = request.getParameter("emailId");
        String Gender = request.getParameter("Gender");
        String Course = request.getParameter("Course");

//        response.sendRedirect(request.getContextPath() + "/welcome.html?Std_ID=" + Std_ID +
//                "&Std_Name=" + Std_Name +
//                "&emailId=" + emailId +
//                "&Gender=" + Gender +
//                "&Course=" + Course);
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        out.println("<h2>Student Info </h2>");
        out.println("<h2>Student Id: "+Std_ID+ "</h2>");
        out.println("<h2>Student Name: "+Std_Name+ "</h2>");
        out.println("<h2>Student emailId: "+emailId+ "</h2>");
        out.println("<h2>Student Gender: "+Gender+ "</h2>");
        out.println("<h2>Student Course: "+Course+ "</h2>");
//        
//        String[] Std_ID = request.getParameterValues("Std_ID");
//        String[] Std_Name = request.getParameterValues("Std_Name");
//        String[] emailId = request.getParameterValues("emailId");
//        String[] Gender = request.getParameterValues("Gender");
//        String[] Course = request.getParameterValues("Course");
//
//        out.println("<h2>Student Details Received</h2>");
//
//        for(int i = 0; i < Std_Name.length; i++) {
//        	out.println("Std_ID: " + Std_ID[i] + "<br>");
//            out.println("Std_Name: " + Std_Name[i] + "<br>");
//            out.println("emailId: " + emailId[i] + "<br>");
//            out.println("Gender: " + Gender[i] + "<br>");
//            out.println("Course: " + Course[i] + "<br><br>");
//        }
//
//        out.println("<br><a href='index.html'>Go Back</a>");
    }
}
