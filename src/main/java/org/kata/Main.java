package org.kata;

import org.kata.exceptions.UnauthorizedOperationException;
import org.kata.models.Account;
import org.kata.services.BankAccountManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        BankAccountManager bankManager = new BankAccountManager();
        Account bankAccount = new Account();

        try {
            bankManager.deposit(1500, bankAccount);
            bankManager.deposit(1500, bankAccount);
            bankManager.withdraw(1500, bankAccount);
            String history = bankManager.showHistory(bankAccount);
            logger.info(history);
            logger.info("end logging!");
        } catch (UnauthorizedOperationException e) {
            logger.error("Please supply only positive amounts");
        }
    }
}