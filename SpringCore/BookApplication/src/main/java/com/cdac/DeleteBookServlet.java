package com.cdac;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/DeleteBookServlet")
public class DeleteBookServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = 0;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (Exception e) {
            response.getWriter().println("Invalid ID!");
            return;
        }

        int status = 0;
        try {
            status = BookDAO.deleteBook(id);  
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (status > 0) {
            response.sendRedirect("ViewBookServlet");
        } else {
            response.getWriter().println("Failed to Delete Book");
        }
    }
}

