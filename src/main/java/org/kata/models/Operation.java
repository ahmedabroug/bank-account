package org.kata.models;

import java.util.Date;

public class Operation {

    private OperationType name;
    private Date date;
    private double amount;
    private double balance;

    public Operation() {
        super();
    }

    public OperationType getName() {
        return name;
    }

    public Operation setName(OperationType name) {
        this.name = name;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public Operation setDate(Date date) {
        this.date = date;
        return this;
    }

    public double getAmount() {
        return amount;
    }

    public Operation setAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public double getBalance() {
        return balance;
    }

    public Operation setBalance(double balance) {
        this.balance = balance;
        return this;
    }

    @Override
    public String toString() {
        return "Operation type = " + getName() + ", Date = " + getDate() + ", Amount = " + getAmount() + ", Balance = " + getBalance();
    }

    public Operation(OperationType name, Date date, double amount, double balance) {
        this.name = name;
        this.date = date;
        this.amount = amount;
        this.balance = balance;
    }

    public Operation build() {
        return new Operation(name, date, amount, balance);
    }
}
