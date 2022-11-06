package org.kata.models;

public class Account {

    private double balance;
    private final History history;

    public Account() {
        super();
        this.balance = 0d;
        this.history = new History();
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public History getHistory() {
        return history;
    }

}
