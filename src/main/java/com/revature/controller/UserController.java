package com.revature.controller;

import com.revature.dao.ConnectionFactory;
import com.revature.dao.UserDao;
import com.revature.exception.UserNotFoundException;
import com.revature.exception.UserUnsuccessfullyAddedException;
import com.revature.model.User;
import com.revature.service.UserService;
import io.javalin.Javalin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class UserController implements Controller {

    private UserService userService = new UserService();

    public void mapEndpoints(Javalin app) {

        app.get("/users", (ctx) -> {
            List<User> allUsers = userService.getAllUsers();

            ctx.json(allUsers); // allUsers is a List of User objects
        });

        app.post("/users", (ctx) -> {
            User userToAdd = ctx.bodyAsClass(User.class);

            try {
                userService.addUser(userToAdd);

                ctx.result("User successfully added!");
                ctx.status(201); // 201 = CREATED
            } catch (IllegalArgumentException | UserUnsuccessfullyAddedException e) {
                ctx.result(e.getMessage());
                ctx.status(400);
            }

        });

        app.get("/users/{user_id}", (ctx) -> { // user_id is a "path parameter"
            String userId = ctx.pathParam("user_id");

            try {
                // This method could throw NumberFormatException if we do not provide a String that represents a valid int
                int uId = Integer.parseInt(userId); // Converting a String into an int

                User user = userService.getUserById(uId); // This method could potentially throw UserNotFoundException, which
                // we then catch if it occurs

                ctx.json(user);

            } catch (NumberFormatException e) {
                ctx.result("Id " + userId + " must be a valid integer");
                ctx.status(400); // 400 BAD REQUEST
            } catch (UserNotFoundException e) {
                ctx.result(e.getMessage());
                ctx.status(404);
            }
        });
    }

}
