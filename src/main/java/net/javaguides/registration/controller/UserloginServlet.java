package net.javaguides.registration.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.javaguides.registration.dao.UserDao;
import net.javaguides.registration.model.User;

import java.io.IOException;

@WebServlet("/UserloginServlet")
public class UserloginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UserDao userDao = new UserDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve form parameters
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Validate user credentials
        UserDao userDao = new UserDao();
        boolean isValidUser = userDao.loginUser(email, password);

        if (isValidUser) {
            // Successful login, redirect to voting page
            response.sendRedirect(request.getContextPath() + "/voting.html");
        } else {
            // Invalid credentials, redirect with error
            response.sendRedirect(request.getContextPath() + "/userlogin.html?error=true");
        }
    }
}
