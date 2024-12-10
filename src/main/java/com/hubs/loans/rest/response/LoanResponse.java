package com.hubs.loans.rest.response;

import com.hubs.loans.domain.entity.Loan;

import java.math.BigDecimal;
import java.util.UUID;

public record LoanResponse(UUID id, BigDecimal amount, int numberOfInstallments, boolean isPaid) {
    public static LoanResponse from(Loan loan) {
        return new LoanResponse(
                loan.getId(),
                loan.getLoanAmount().amount(),
                loan.getNumberOfInstallments().value(),
                loan.getIsPaid()
        );
    }
}
