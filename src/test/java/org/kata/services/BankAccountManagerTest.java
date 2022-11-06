package org.kata.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.kata.exceptions.OperationErrorCode;
import org.kata.exceptions.UnauthorizedOperationException;
import org.kata.models.Account;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BankAccountManagerTest {

    private BankAccountManager bankManager;
    private Account account;

    @BeforeEach
    public void init() {
        // generate an empty account for each use case.
        bankManager = new BankAccountManager();
        account = new Account();
    }

    @Test
    void when_deposit_1000_to_empty_account_then_balance_should_be_1000() throws UnauthorizedOperationException {
        // Arrange
        double amountToDeposit = 1000;

        // Act
        Account result = bankManager.deposit(amountToDeposit, account);

        // Assert
        assertThat(result.getBalance()).isEqualTo(amountToDeposit);
    }

    @Test
    void when_deposit_a_negative_amount_to_account_then_throw_invalid_amount_exception() {
        // Arrange
        double amountToDeposit = -1000;

        // Act
        Executable execution = () -> bankManager.deposit(amountToDeposit, account);

        // Assert
        UnauthorizedOperationException exception = assertThrows(UnauthorizedOperationException.class, execution);
        assertThat(exception.getErrorCode()).isEqualTo(OperationErrorCode.INVALID_AMOUNT_PROVIDED);
    }

    @Test
    void when_deposit_an_amount_to_null_account_then_throw_account_not_found_exception() {
        // Arrange
        double amountToDeposit = 1000;

        // Act
        Executable execution = () -> bankManager.deposit(amountToDeposit, null);

        // Assert
        assertThrows(UnauthorizedOperationException.class, execution);
    }

    @Test
    void when_withdraw_1000_from_an_account_then_balance_should_be_the_difference_old_balance_and_the_amount() throws UnauthorizedOperationException {
        // Arrange
        double amountToDeposit = 1000;
        bankManager.deposit(amountToDeposit, account);
        bankManager.deposit(amountToDeposit, account);
        double amountToWithdraw = 1000;
        double expectedBalance = 1000;

        // Act
        Account result = bankManager.withdraw(amountToWithdraw, account);

        // Assert
        assertThat(result.getBalance()).isEqualTo(expectedBalance);
    }

    @Test
    void when_withdraw_a_negative_amount_from_account_then_throw_invalid_amount_exception() {
        // Arrange
        double amountToWithdraw = -1000;

        // Act
        Executable execution = () -> bankManager.withdraw(amountToWithdraw, account);

        // Assert
        assertThrows(UnauthorizedOperationException.class, execution);
    }

    @Test
    void when_withdraw_an_amount_from_null_account_then_throw_account_not_found_exception() {
        // Arrange
        double amountToWithdraw = 1000;

        // Act
        Executable execution = () -> bankManager.withdraw(amountToWithdraw, null);

        // Assert
        assertThrows(UnauthorizedOperationException.class, execution);
    }

    @Test
    void when_withdraw_1000_from_empty_account_then_throw_no_enough_money_exception() {
        // Arrange
        double amountToWithdraw = 1000;

        // Act
        Executable execution = () -> bankManager.withdraw(amountToWithdraw, account);

        // Assert
        assertThrows(UnauthorizedOperationException.class, execution);
    }

    @Test
    void when_withdraw_1000_from_limit_reached_account_then_throw_withdraw_limit_exceeded() throws UnauthorizedOperationException {
        // Arrange
        double amountToDeposit = 3000;
        bankManager.deposit(amountToDeposit, account);
        double amountToWithdraw = 1000;
        bankManager.withdraw(amountToWithdraw, account);
        bankManager.withdraw(amountToWithdraw, account);

        // Act
        Executable execution = () -> bankManager.withdraw(amountToWithdraw, account);

        // Assert
        assertThrows(UnauthorizedOperationException.class, execution);
    }

    @Test
    void when_show_history_of_account_with_deposit_and_withdraw_then_operations_size_should_be_two() throws UnauthorizedOperationException {
        // Arrange
        double amountToDeposit = 1000;

        bankManager.deposit(amountToDeposit, account);
        bankManager.withdraw(amountToDeposit, account);

        // Act
        String result = bankManager.showHistory(account);


        // Assert
        assertThat(result.split("\n")).hasSize(3);
        assertThat(result.split("\n")[0]).isEqualTo("Total account operations: 2");
    }

    @Test
    void when_show_history_of_empty_account_then_operations_size_should_be_Zero() {
        // Arrange


        // Act
        String result = bankManager.showHistory(account);


        // Assert
        assertThat(result.split("\n")).hasSize(1);
        assertThat(result.split("\n")[0]).isEqualTo("Total account operations: 0");
    }
}
