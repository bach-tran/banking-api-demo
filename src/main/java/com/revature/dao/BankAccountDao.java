package com.revature.dao;

import com.revature.model.BankAccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BankAccountDao {

    public List<BankAccount> getAllBankAccountsBelongingToUser(int userId) throws SQLException {

        // try-with-resources
        // It automatically calls the .close() method when the block of code is finished
        // Whether a return statement is reached or an exception occurs, try-with-resources ensures that whatever
        // you want closed actually gets closed
        try(Connection connection = ConnectionFactory.createConnection()) {
            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM bank_accounts WHERE user_id = ?");

            pstmt.setInt(1, userId);

            ResultSet rs = pstmt.executeQuery();

            List<BankAccount> accounts = new ArrayList<>();

            while (rs.next()) {
                BankAccount bankAccount = new BankAccount(rs.getInt("id"), rs.getInt("balance"), rs.getInt("user_id"));

                accounts.add(bankAccount);
            }

            return accounts;
        }

    }

}
