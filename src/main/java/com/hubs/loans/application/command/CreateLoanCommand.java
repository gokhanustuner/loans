package com.hubs.loans.application.command;

import com.hubs.loans.domain.value.customer.CustomerId;
import com.hubs.loans.domain.value.loan.InterestRate;

import java.math.BigDecimal;

public record CreateLoanCommand(
        CustomerId customerId,
        BigDecimal loanAmount,
        InterestRate interestRate,
        int numberOfInstallments
) {}
