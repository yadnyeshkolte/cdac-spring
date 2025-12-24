package com.cdac;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/AddBookServlet")
public class AddBookServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String title = request.getParameter("title");
		String author = request.getParameter("author");
		double price = Double.parseDouble(request.getParameter("price"));
		
		response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        Books book = new Books(title,author,price);
        
        int status = 0;
		try {
			BookDAO dao = new BookDAO();
			status = dao.addBook(book);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        if (status > 0) {
            request.setAttribute("book",book);
            request.getRequestDispatcher("success.jsp").forward(request, response);
        } else {
            response.getWriter().println("Insert Failed!");
        }
	}

}
