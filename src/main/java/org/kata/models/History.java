package org.kata.models;

import java.util.ArrayList;
import java.util.List;

public class History {

    List<Operation> operations = new ArrayList<>();

    public History() {
        super();
    }

    public void addOperation(Operation operation) {
        operations.add(operation);
    }

    public List<Operation> getAll() {
        return operations;
    }

    public String show() {
        StringBuilder historyBuilder = new StringBuilder("Total account operations: " + operations.size());
        historyBuilder.append("\n");
        for (Operation operation : operations) {
            historyBuilder.append(operation.toString());
            historyBuilder.append("\n");
        }
        return historyBuilder.toString();
    }
}
