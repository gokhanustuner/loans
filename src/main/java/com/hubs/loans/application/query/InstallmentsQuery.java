package com.hubs.loans.application.query;

import com.hubs.loans.domain.value.loan.LoanId;

import java.util.UUID;

public record InstallmentsQuery(LoanId loanId, int page) {
    public static InstallmentsQuery of(UUID loanId, int page) {
        return new InstallmentsQuery(new LoanId(loanId), page);
    }
}
