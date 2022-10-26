package com.revature.dao;

import org.postgresql.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    public static Connection createConnection() throws SQLException {
        // Very bad practice
        // Don't hardcode actual production database credentials into your code
        // The reason is that this could result in a credential leak
        // Ex. pushing the code to Github (public repository)

//        String url = "jdbc:postgresql://35.238.248.173:5432/postgres"; // JDBC connection string
//        String username = "postgres";
//        String password = "password";

        /*
            Better practice: Environment variables
         */
        String url = System.getenv("db_url");
        String username = System.getenv("db_username");
        String password = System.getenv("db_password");

        Driver postgresDriver = new Driver();
        DriverManager.registerDriver(postgresDriver);

        Connection connection = DriverManager.getConnection(url, username, password);

        return connection;
    }

}
