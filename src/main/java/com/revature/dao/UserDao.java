package com.revature.dao;

import com.revature.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    public List<User> getAllUsers() throws SQLException {
        // 1. Write JDBC that will go and query the database for all users

        // Try-with-resources
        try (Connection connection = ConnectionFactory.createConnection()) {

            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM users");

            ResultSet rs = pstmt.executeQuery();

            List<User> allUsers = new ArrayList<>();

            // 2. Put each user record into a User object
            while (rs.next()) { // .next() returns a boolean if there is another record in the ResultSet
                // and .next() also iterates to the next record
                int id = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");

                User u = new User(id, firstName, lastName);

                // 3. Store each User object into an ArrayList
                allUsers.add(u);
            }

            return allUsers;
        }

    }

    public int addUser(User userToAdd) throws SQLException {
        // try-with-resources
        try (Connection connection = ConnectionFactory.createConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(
                    "INSERT INTO users (first_name, last_name) VALUES (?, ?)"
            );

            pstmt.setString(1, userToAdd.getFirstName());
            pstmt.setString(2, userToAdd.getLastName());

            int numberOfRecordsUpdated = pstmt.executeUpdate();

            return numberOfRecordsUpdated;
        }
    }

    public User getUserById(int id) throws SQLException {
        // try-with-resources
        try(Connection connection = ConnectionFactory.createConnection()) {

            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM users WHERE id = ?");

            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new User(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"));
            } else { // no record was found
                return null;
            }
        }
    }

}
