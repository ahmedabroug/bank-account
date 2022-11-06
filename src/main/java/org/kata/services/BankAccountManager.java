package org.kata.services;

import org.kata.exceptions.OperationErrorCode;
import org.kata.exceptions.UnauthorizedOperationException;
import org.kata.models.Account;
import org.kata.models.Operation;
import org.kata.models.OperationType;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class BankAccountManager implements IBankAccountManager {

    private static final double WITHDRAW_ROOF = 2000;
    public static final String INVALID_AMOUNT_PROVIDED_FOR_DEPOSIT_MESSAGE = "Provide a valid amount to deposit.";
    public static final String ACCOUNT_NOT_FOUND_FOR_DEPOSIT_MESSAGE = "Provide an account to continue the deposit operation.";
    public static final String INVALID_AMOUNT_PROVIDED_FOR_WITHDRAW_MESSAGE = "Provide a valid amount to withdraw.";
    public static final String ACCOUNT_NOT_FOUND_FOR_WITHDRAW_MESSAGE = "Provide an account to continue the withdraw operation.";
    public static final String WITHDRAW_LIMIT_EXCEEDED_MESSAGE = "Withdraw limit exceeded! try with another amount.";
    public static final String NOT_ENOUGH_FUNDS_MESSAGE = "There is no enough money in the account! try with another amount.";

    public BankAccountManager() {
        super();
    }

    @Override
    public Account deposit(double amount, Account account) throws UnauthorizedOperationException {

        if (isNegative(amount)) {
            throw new UnauthorizedOperationException(OperationErrorCode.INVALID_AMOUNT_PROVIDED, INVALID_AMOUNT_PROVIDED_FOR_DEPOSIT_MESSAGE);
        }

        if (account == null) {
            throw new UnauthorizedOperationException(OperationErrorCode.ACCOUNT_NOT_FOUND, ACCOUNT_NOT_FOUND_FOR_DEPOSIT_MESSAGE);
        }

        double newBalance = account.getBalance() + amount;
        account.setBalance(newBalance);
        Operation operation = new Operation()
                .setName(OperationType.DEPOSIT)
                .setDate(Calendar.getInstance().getTime())
                .setAmount(amount)
                .setBalance(newBalance).build();
        account.getHistory().addOperation(operation);
        return account;
    }

    @Override
    public Account withdraw(double amount, Account account) throws UnauthorizedOperationException {

        if (isNegative(amount)) {
            throw new UnauthorizedOperationException(OperationErrorCode.INVALID_AMOUNT_PROVIDED, INVALID_AMOUNT_PROVIDED_FOR_WITHDRAW_MESSAGE);
        }

        if (account == null) {
            throw new UnauthorizedOperationException(OperationErrorCode.ACCOUNT_NOT_FOUND, ACCOUNT_NOT_FOUND_FOR_WITHDRAW_MESSAGE);
        }

        if (!stillAbleToWithdraw(amount, account)) {
            throw new UnauthorizedOperationException(OperationErrorCode.WITHDRAW_LIMIT_EXCEEDED, WITHDRAW_LIMIT_EXCEEDED_MESSAGE);
        }

        double newBalance = account.getBalance() - amount;

        if (!hasEnoughMoney(newBalance)) {
            throw new UnauthorizedOperationException(OperationErrorCode.NOT_ENOUGH_FUNDS, NOT_ENOUGH_FUNDS_MESSAGE);
        }


        account.setBalance(newBalance);
        Operation operation = new Operation()
                .setName(OperationType.WITHDRAW)
                .setDate(Calendar.getInstance().getTime())
                .setAmount(amount)
                .setBalance(newBalance).build();
        account.getHistory().addOperation(operation);

        return account;
    }

    @Override
    public String showHistory(Account account) {
        return account.getHistory().show();
    }

    private static boolean isNegative(double d) {
        return Double.doubleToRawLongBits(d) < 0;
    }

    private static boolean hasEnoughMoney(double d) {
        return Double.doubleToRawLongBits(d) >= 0;
    }

    private boolean stillAbleToWithdraw(double amount, Account account) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().getActualMinimum(Calendar.DAY_OF_MONTH));
        Date start = calendar.getTime();
        calendar.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH));
        Date end = calendar.getTime();
        List<Operation> withdrawsForThisMonth = account.getHistory().getAll().stream()
                .filter(operation -> operation.getDate().after(start) && operation.getDate().before(end) && operation.getName().equals(OperationType.WITHDRAW))
                .collect(Collectors.toList());

        double total = withdrawsForThisMonth.stream().map(Operation::getAmount).mapToDouble(Double::doubleValue).sum();


        return Double.doubleToRawLongBits(total + amount) <= Double.doubleToRawLongBits(WITHDRAW_ROOF);
    }
}
