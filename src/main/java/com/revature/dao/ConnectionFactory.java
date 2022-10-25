package com.revature.dao;

import org.postgresql.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    public static Connection createConnection() throws SQLException {
        String url = "jdbc:postgresql://35.238.248.173:5432/postgres"; // JDBC connection string
        String username = "postgres";
        String password = "password";

        Driver postgresDriver = new Driver();
        DriverManager.registerDriver(postgresDriver);

        Connection connection = DriverManager.getConnection(url, username, password);

        return connection;
    }

}
