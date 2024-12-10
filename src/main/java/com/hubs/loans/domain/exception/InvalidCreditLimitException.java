package com.hubs.loans.domain.exception;

public class InvalidCreditLimitException extends RuntimeException {
    public InvalidCreditLimitException(String message) {
        super(message);
    }
}
