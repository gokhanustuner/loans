package com.hubs.loans.domain.value.loan;

import java.math.BigDecimal;

public record PayLoanResult(int numberOfInstallmentsPaid, BigDecimal paidAmount, boolean isPaid) {}
