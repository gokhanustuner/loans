package com.hubs.loans.rest.response;

import java.math.BigDecimal;

public record PayLoanResponse(int numberOfInstallmentsPaid, BigDecimal paidAmount, boolean isPaid) {}
