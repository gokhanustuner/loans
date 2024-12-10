package com.hubs.loans.domain.exception;

public class InvalidInterestRateException extends RuntimeException {
    public InvalidInterestRateException(String message) {
        super(message);
    }
}
