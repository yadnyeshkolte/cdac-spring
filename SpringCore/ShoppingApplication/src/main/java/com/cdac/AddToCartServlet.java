package com.cdac;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/addToCart")
public class AddToCartServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        HttpSession session = req.getSession();

        ArrayList<String> cart = (ArrayList<String>) session.getAttribute("cart");

        if (cart == null) {
            cart = new ArrayList<>();
        }

        String product = req.getParameter("product");
        cart.add(product);

        session.setAttribute("cart", cart);
        res.sendRedirect("products.jsp");
    }
}
