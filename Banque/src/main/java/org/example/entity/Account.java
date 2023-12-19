package org.example.entity;

import java.util.ArrayList;
import java.util.List;

public class Account {

    private long id;
    private double balance;
    private User user;
    private List<Transaction> transactions = new ArrayList<>();

    public Account(long id,double balance) {
        this.id=id;
        this.balance = balance;
    }

    public Account() {

    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public long getId() {
        return id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public User getUser() {
        return user;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

}
