package com.hubs.loans.domain.value.loan;

import com.hubs.loans.domain.exception.InvalidLoanAmountException;

import java.math.BigDecimal;

public record LoanAmount(BigDecimal rawAmount, BigDecimal amount) {

    public final static BigDecimal MIN = BigDecimal.valueOf(1000);

    public LoanAmount {
        if (rawAmount.compareTo(MIN) < 0) {
            throw new InvalidLoanAmountException("rawAmount must be greater than or equal to 1000");
        } else if (rawAmount.compareTo(amount) >= 0) {
            throw new InvalidLoanAmountException("value must be greater than rawAmount");
        }
    }

    public static LoanAmount of(BigDecimal rawAmount, InterestRate interestRate) {
        return new LoanAmount(
                rawAmount,
                rawAmount.multiply(BigDecimal.valueOf(1 + interestRate.value()))
        );
    }
}
