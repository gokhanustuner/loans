package com.hubs.loans.rest.dto.request;

import com.hubs.loans.application.command.PayLoanCommand;
import com.hubs.loans.domain.value.loan.LoanId;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.UUID;

public record PayLoanRequest(@NotNull @DecimalMin("0.01") BigDecimal amount) {
    public PayLoanCommand toCommandWith(UUID loanId) {
        return new PayLoanCommand(new LoanId(loanId), amount);
    }
}
