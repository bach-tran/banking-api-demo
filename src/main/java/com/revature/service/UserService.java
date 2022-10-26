package com.revature.service;

import com.revature.dao.UserDao;
import com.revature.exception.UserNotFoundException;
import com.revature.exception.UserUnsuccessfullyAddedException;
import com.revature.model.User;

import java.sql.SQLException;
import java.util.List;

public class UserService {

    private UserDao userDao = new UserDao();

    public List<User> getAllUsers() throws SQLException {
        return userDao.getAllUsers();
    }

    // The purpose of the service layer is to provide exception throwing if a user does something wrong, etc.
    public void addUser(User user) throws SQLException {
        // Strip the leading and trailing whitespace from firstName and lastName
        user.setFirstName(user.getFirstName().strip());
        user.setLastName(user.getLastName().strip());

        if (user.getFirstName().length() == 0) {
            throw new IllegalArgumentException("First name must not be blank");
        }

        if (user.getLastName().length() == 0) {
            throw new IllegalArgumentException("Last name must not be blank");
        }

        int recordsAdded = userDao.addUser(user); // 1 if a user was added, 0 if no user was added

        if (recordsAdded != 1) { // Throw an exception if a user was not successfully added
            throw new UserUnsuccessfullyAddedException("User could not be added");
        }
    }

    public User getUserById(int id) throws SQLException {
        User user = userDao.getUserById(id); // null if user does not exist

        if (user == null) {
            throw new UserNotFoundException("User with id " + id + " was not found");
        } else {
            return user;
        }
    }

}
