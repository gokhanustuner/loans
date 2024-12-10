package com.hubs.loans.domain.value.loan;

import com.hubs.loans.domain.exception.InvalidInterestRateException;

public record InterestRate(double amount) {
    public InterestRate {
        if (amount < 0.1 || amount > 0.5)
            throw new InvalidInterestRateException("Interest Rate must be between 0.1 and 0.5");
    }
}
