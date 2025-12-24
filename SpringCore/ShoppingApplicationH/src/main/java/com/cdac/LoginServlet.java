package com.cdac;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        UserDAO dao = new UserDAO();
        User user = dao.validateUser(username, password);

        if (user != null) {
            HttpSession session = req.getSession();
            session.setAttribute("username", user.getUsername());
            res.sendRedirect("products.jsp");
        } else {
            req.setAttribute("error", "Invalid Credentials");
            req.getRequestDispatcher("login.jsp").forward(req, res);
        }
    }
}
