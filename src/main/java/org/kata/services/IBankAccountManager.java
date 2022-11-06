package org.kata.services;

import org.kata.exceptions.UnauthorizedOperationException;
import org.kata.models.Account;

public interface IBankAccountManager {

    Account deposit(double amount, Account account) throws UnauthorizedOperationException;

    Account withdraw(double amount, Account account) throws UnauthorizedOperationException;

    String showHistory(Account account);

}
