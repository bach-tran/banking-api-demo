package com.revature.service;

import com.revature.dao.BankAccountDao;
import com.revature.model.BankAccount;

import java.sql.SQLException;
import java.util.List;

public class BankAccountService {

    private BankAccountDao bankAccountDao = new BankAccountDao();

    public List<BankAccount> getAllBankAccountsBelongingToUser(int userId) throws SQLException {
        return bankAccountDao.getAllBankAccountsBelongingToUser(userId);
    }

}
