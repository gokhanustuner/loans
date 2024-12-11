package com.hubs.loans.domain.exception;

import jakarta.persistence.EntityNotFoundException;

public class LoanNotFoundException extends EntityNotFoundException {
    public LoanNotFoundException(String message) {
        super(message);
    }
}
