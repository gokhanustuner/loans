package com.hubs.loans.domain.value.loan;

import java.util.UUID;

public record LoanId(UUID id) {
    public static LoanId of(UUID id) {
        return new LoanId(id);
    }
}
