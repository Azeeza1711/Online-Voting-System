package net.javaguides.registration.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.javaguides.registration.dao.UserDao;
import net.javaguides.registration.model.User;

import java.io.IOException;

@WebServlet("/UsersignupServlet")
public class UsersignupServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UserDao userDao = new UserDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve form parameters
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Create a new User object
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);

        // Register the user
        try {
            userDao.registerUser(user);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Redirect to login page after successful registration
        response.sendRedirect(request.getContextPath() + "/userlogin.html");
    }
}
