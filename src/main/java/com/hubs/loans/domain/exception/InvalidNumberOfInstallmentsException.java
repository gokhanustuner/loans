package com.hubs.loans.domain.exception;

public class InvalidNumberOfInstallmentsException extends RuntimeException {
    public InvalidNumberOfInstallmentsException(String message) {
        super(message);
    }
}
