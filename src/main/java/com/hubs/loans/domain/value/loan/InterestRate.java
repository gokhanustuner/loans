package com.hubs.loans.domain.value.loan;

import com.hubs.loans.domain.exception.InvalidInterestRateException;

public record InterestRate(double amount) {

    public final static double MIN = 0.1;

    public final static double MAX = 0.5;

    public InterestRate {
        if (amount < MIN || amount > MAX)
            throw new InvalidInterestRateException(String.format("Interest Rate must be between %s and %s", MIN, MAX));
    }
}
