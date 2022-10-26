package com.revature.controller;

import com.revature.dao.ConnectionFactory;
import com.revature.model.BankAccount;
import com.revature.service.BankAccountService;
import io.javalin.Javalin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BankAccountController implements Controller {

    private BankAccountService bankAccountService = new BankAccountService();

    public void mapEndpoints(Javalin app) {
        app.get("/users/{user_id}/bank-accounts", (ctx) -> {
            String userId = ctx.pathParam("user_id");

            try {
                int uId = Integer.parseInt(userId);

                List<BankAccount> bankAccounts = bankAccountService.getAllBankAccountsBelongingToUser(uId);

                ctx.json(bankAccounts);
                ctx.status(200);
            } catch (NumberFormatException e) {
                ctx.result("Id " + userId + " must be a valid int!");
                ctx.status(400);
            }

        });
    }

}
