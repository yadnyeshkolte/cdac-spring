package com.cdac;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/ViewBookServlet")
public class ViewBookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            List<Books> list = BookDAO.viewBook();  

            request.setAttribute("list", list);     

            request.getRequestDispatcher("listBook.jsp").forward(request, response);
        } 
        catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Unable to retrieve books.");
        }
    }
}
