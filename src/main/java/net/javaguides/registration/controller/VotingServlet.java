package net.javaguides.registration.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/VotingServlet")
public class VotingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private DataSource datasource;

    @Override
    public void init() throws ServletException {
        try {
            // Perform JNDI lookup to initialize the DataSource
            InitialContext ctx = new InitialContext();
            datasource = (DataSource) ctx.lookup("java:comp/env/jdbc/votingsystem");
        } catch (NamingException e) {
            throw new ServletException("Cannot retrieve java:comp/env/jdbc/votingsystem", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("candidates");

        try (Connection connection = datasource.getConnection()) {
            // Update votes for the selected candidate
            PreparedStatement preparedstatement = connection.prepareStatement("UPDATE voting SET Votes = Votes + 1 WHERE Candidates = ?");
            preparedstatement.setString(1, name);
            preparedstatement.execute();

            // Redirect to success page
            response.sendRedirect(request.getContextPath() + "/success.html");
        } catch (SQLException e) {
            e.printStackTrace();
            
            // Respond with an alert box on failure
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<script type=\"text/javascript\">");
            out.println("alert('An error occurred while processing your vote. Please try again.');");
            out.println("window.location.href = 'index.html';"); // Redirect back to index page or any other page
            out.println("</script>");
        }
    }
}
