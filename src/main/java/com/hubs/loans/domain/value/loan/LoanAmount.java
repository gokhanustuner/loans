package com.hubs.loans.domain.value.loan;

import java.math.BigDecimal;

public record LoanAmount(BigDecimal rawAmount, BigDecimal amount) {
    public LoanAmount {
        // Cannot be negative values, etc.
        System.out.println("Loan Amount is: " + rawAmount.toPlainString());
    }

    public static LoanAmount of(BigDecimal rawAmount, InterestRate interestRate) {
        return new LoanAmount(
                rawAmount,
                rawAmount.multiply(BigDecimal.valueOf(1 + interestRate.amount()))
        );
    }
}
