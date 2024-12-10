package com.hubs.loans.application.query;

import com.hubs.loans.domain.value.loan.LoanId;

import java.util.UUID;

public record ListInstallmentsQuery(LoanId loanId) {
    public static ListInstallmentsQuery of(UUID loanId) {
        return new ListInstallmentsQuery(new LoanId(loanId));
    }
}
