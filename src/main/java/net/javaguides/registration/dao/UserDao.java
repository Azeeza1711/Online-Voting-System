package net.javaguides.registration.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import net.javaguides.registration.model.User; // Import the User model class
//import net.javaguides.registration.model.Vote; // Import the Vote model class

public class UserDao {

    // Method to register a user
    public int registerUser(User user) throws ClassNotFoundException {
        String INSERT_USERS_SQL = "INSERT INTO signup (username, email, password) VALUES (?, ?, ?);";
        int result = 0;

        // Register MySQL JDBC driver
        Class.forName("com.mysql.jdbc.Driver");

        // Update the database URL, username, and password
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/votingsystem?useSSL=false", "root", "Subbiah007@");
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {

            // Set the parameters for the PreparedStatement
            preparedStatement.setString(1, user.getUsername()); // Set the username
            preparedStatement.setString(2, user.getEmail());    // Set the email
            preparedStatement.setString(3, user.getPassword()); // Set the password

            System.out.println(preparedStatement);
            // Execute the query or update query
            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Method to get a user by email
    public User getUserByEmail(String email) throws ClassNotFoundException {
        String SELECT_USER_SQL = "SELECT * FROM signup WHERE email = ?";
        User user = null;

        Class.forName("com.mysql.jdbc.Driver");

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/votingsystem?useSSL=false", "root", "Subbiah007@");
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_SQL)) {

            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    // Method to validate user login
    public boolean loginUser(String email, String password) {
        User user = null;

        try {
            user = getUserByEmail(email);
            if (user != null && user.getPassword().equals(password)) {
                return true; // Successful login
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return false; // Login failed
    }

    // Method to cast a vote
//    public int castVote(Vote vote) throws ClassNotFoundException {
//        String INSERT_VOTE_SQL = "INSERT INTO voting (user_id, candidate_name) VALUES (?, ?);";
//        int result = 0;
//
//        Class.forName("com.mysql.jdbc.Driver");
//
//        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/votingsystem?useSSL=false", "root", "Subbiah007@");
//             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_VOTE_SQL)) {
//
//            // Set the parameters for the PreparedStatement
//            preparedStatement.setInt(1, vote.getUserId()); // Set the user ID
//            preparedStatement.setString(2, vote.getCandidateName()); // Set the candidate name
//
//            // Execute the query or update query
//            result = preparedStatement.executeUpdate();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return result;
//    }

    // Method to retrieve votes for a specific candidate
//    public int getVotesByCandidate(String candidateName) throws ClassNotFoundException {
//        String COUNT_VOTES_SQL = "SELECT COUNT(*) FROM voting WHERE candidate_name = ?";
//        int voteCount = 0;
//
//        Class.forName("com.mysql.jdbc.Driver");
//
//        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/votingsystem?useSSL=false", "root", "Subbiah007@");
//             PreparedStatement preparedStatement = connection.prepareStatement(COUNT_VOTES_SQL)) {
//
//            preparedStatement.setString(1, candidateName);
//            ResultSet rs = preparedStatement.executeQuery();
//
//            if (rs.next()) {
//                voteCount = rs.getInt(1); // Get the count of votes for the candidate
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return voteCount;
//    }
    
    

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
