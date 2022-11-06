package org.kata.exceptions;

public class UnauthorizedOperationException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = -7448779378295169399L;

    private final OperationErrorCode errorCode;

    public UnauthorizedOperationException(OperationErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public OperationErrorCode getErrorCode() {
        return errorCode;
    }
}
