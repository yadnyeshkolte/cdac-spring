package com.cdac;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String emailId = request.getParameter("emailId");
        String password = request.getParameter("password");

        // Simple hardcoded validation
        if ("ayush@gmail.com".equalsIgnoreCase(emailId) &&
            "ayush".equalsIgnoreCase(password)) {
        	
//        	request.setAttribute(emailId,"emailId");
//        	request.setAttribute(password, "password");

            HttpSession session = request.getSession();
            session.setAttribute("emailId", emailId);   
            session.setAttribute("password", password);  

            request.getRequestDispatcher("welcome.jsp").forward(request, response);
        } else {
            response.getWriter().println("<h3>Invalid Credentials</h3>");
        }
    }
}
