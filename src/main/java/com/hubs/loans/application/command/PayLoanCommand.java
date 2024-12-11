package com.hubs.loans.application.command;

import com.hubs.loans.domain.value.loan.LoanId;

import java.math.BigDecimal;

public record PayLoanCommand(LoanId loanId, BigDecimal amount) {
}
