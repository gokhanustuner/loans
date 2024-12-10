package com.hubs.loans.domain.value.customer;

import com.hubs.loans.domain.exception.InvalidCreditLimitException;
import com.hubs.loans.domain.value.loan.LoanAmount;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.math.BigDecimal;

@Embeddable
public record CreditLimit(
        @Column(nullable = false, precision = 15, scale = 2) BigDecimal usedCreditLimit,
        @Column(nullable = false, precision = 15, scale = 2) BigDecimal creditLimit
) {

    public CreditLimit {
        if (usedCreditLimitLessThanZero() && creditLimitLessThanZero()) {
            throw new InvalidCreditLimitException("usedCreditLimit and creditLimit are invalid values");
        } else if (usedCreditLimitLessThanZero() && creditLimitGreaterThanAndEqualToZero()) {
            throw new InvalidCreditLimitException("usedCreditLimit is an invalid value");
        } else if (usedCreditLimitGreaterThanAndEqualToZero() && creditLimitLessThanZero()) {
            throw new InvalidCreditLimitException("creditLimit is an invalid value");
        }
    }

    public boolean isInsufficient(LoanAmount loanAmount) {
        return creditLimit.subtract(usedCreditLimit)
                .compareTo(loanAmount.rawAmount()) < 0;
    }

    private boolean usedCreditLimitLessThanZero() {
        return usedCreditLimit().compareTo(BigDecimal.ZERO) < 0;
    }

    private boolean creditLimitLessThanZero() {
        return usedCreditLimit().compareTo(BigDecimal.ZERO) < 0;
    }

    private boolean usedCreditLimitGreaterThanAndEqualToZero() {
        return usedCreditLimit().compareTo(BigDecimal.ZERO) >= 0;
    }

    private boolean creditLimitGreaterThanAndEqualToZero() {
        return creditLimit().compareTo(BigDecimal.ZERO) >= 0;
    }
}
