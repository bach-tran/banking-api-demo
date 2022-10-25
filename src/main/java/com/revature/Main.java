package com.revature;

import com.revature.dao.ConnectionFactory;
import com.revature.dao.UserDao;
import com.revature.model.BankAccount;
import com.revature.model.User;
import io.javalin.Javalin;
import org.postgresql.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static UserDao userDao = new UserDao();

    public static void main(String[] args) {
        Javalin app = Javalin.create();

        app.post("/users", (ctx) -> {
            User userToAdd = ctx.bodyAsClass(User.class);

            int numberOfRecordsUpdated = userDao.addUser(userToAdd);

            ctx.result(numberOfRecordsUpdated + " record(s) added");
            ctx.status(201); // 201 = CREATED
        });

        app.get("/users", (ctx) -> {
            List<User> allUsers = userDao.getAllUsers();

            ctx.json(allUsers); // allUsers is a List of User objects
        });

        app.get("/users/{user_id}", (ctx) -> { // user_id is a "path parameter"
            String userId = ctx.pathParam("user_id");

            try {
                int uId = Integer.parseInt(userId); // Converting a String into an int

                Connection connection = ConnectionFactory.createConnection();

                // ? (question marks) are special placeholders for information that we can pass into a query
                PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM users WHERE id = ?");

                pstmt.setInt(1, uId); // Set the 1st question mark in the query to the value uId

                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) { // user with that id actually exists
                    int id = rs.getInt("id");
                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");

                    ctx.json(new User(id, firstName, lastName));
                    ctx.status(200);
                } else { // user with that id does not exist
                    ctx.status(404); // 404 = NOT FOUND
                    ctx.result("User with id " + uId + " was not found");
                }

            } catch (NumberFormatException e) {
                ctx.result("Id " + userId + " must be a valid integer");
                ctx.status(400); // 400 BAD REQUEST
            }


        });

        app.get("/users/{user_id}/bank-accounts", (ctx) -> {
            String userId = ctx.pathParam("user_id");
            int uId = Integer.parseInt(userId);

            Connection connection = ConnectionFactory.createConnection();

            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM bank_accounts WHERE user_id = ?");

            pstmt.setInt(1, uId);

            ResultSet rs = pstmt.executeQuery();

            List<BankAccount> accounts = new ArrayList<>();

            while (rs.next()) { // .next() returns a boolean if there is another record in the ResultSet
                // and .next() also iterates to the next record
                int id = rs.getInt("id");
                int balance = rs.getInt("balance");
                int userIdFk = rs.getInt("user_id");

                BankAccount ba = new BankAccount(id, balance, userIdFk);

                accounts.add(ba);
            }

            // 4. Send the information as JSON back to the client
            ctx.json(accounts);
        });

        app.start(8080);
    }

}
