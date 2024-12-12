package com.hubs.loans.application.query;

import com.hubs.loans.domain.value.customer.CustomerId;
import com.hubs.loans.domain.value.loan.LoanId;

import java.util.UUID;

public record ListInstallmentsQuery(CustomerId customerId, LoanId loanId) {
    public static ListInstallmentsQuery of(UUID customerId, UUID loanId) {
        return new ListInstallmentsQuery(new CustomerId(customerId), new LoanId(loanId));
    }
}
