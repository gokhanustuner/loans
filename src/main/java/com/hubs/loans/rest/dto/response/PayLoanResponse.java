package com.hubs.loans.rest.dto.response;

import com.hubs.loans.domain.value.loan.PayLoanResult;

import java.math.BigDecimal;

public record PayLoanResponse(int numberOfInstallmentsPaid, BigDecimal paidAmount, boolean isPaid) {
    public static PayLoanResponse from(PayLoanResult payLoanResult) {
        return new PayLoanResponse(
                payLoanResult.numberOfInstallmentsPaid(),
                payLoanResult.paidAmount(),
                payLoanResult.isPaid()
        );
    }
}
