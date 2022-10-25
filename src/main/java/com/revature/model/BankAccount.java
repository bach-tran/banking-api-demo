package com.revature.model;

import java.util.Objects;

public class BankAccount {

    private int id;
    private int balance;
    private int userId;

    public BankAccount() {
    }

    public BankAccount(int id, int balance, int userId) {
        this.id = id;
        this.balance = balance;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankAccount that = (BankAccount) o;
        return id == that.id && balance == that.balance && userId == that.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, balance, userId);
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "id=" + id +
                ", balance=" + balance +
                ", userId=" + userId +
                '}';
    }
}
