package com.hubs.loans.domain.value.loan;

import com.hubs.loans.domain.exception.InvalidInterestRateException;

public record InterestRate(double value) {

    public final static double MIN = 0.1;

    public final static double MAX = 0.5;

    public InterestRate {
        if (value < MIN || value > MAX)
            throw new InvalidInterestRateException(String.format("Interest Rate must be between %s and %s", MIN, MAX));
    }
}
