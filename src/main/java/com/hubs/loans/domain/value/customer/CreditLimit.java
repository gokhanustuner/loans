package com.hubs.loans.domain.value.customer;

import com.hubs.loans.domain.value.loan.LoanAmount;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.math.BigDecimal;

// Cannot be negative values
@Embeddable
public record CreditLimit(
        @Column(nullable = false, precision = 15, scale = 2) BigDecimal usedCreditLimit,
        @Column(nullable = false, precision = 15, scale = 2) BigDecimal creditLimit
) {
    public boolean isInsufficient(LoanAmount loanAmount) {
        return creditLimit.subtract(usedCreditLimit)
                .compareTo(loanAmount.rawAmount()) < 0;
    }
}
