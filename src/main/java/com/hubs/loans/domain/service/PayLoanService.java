package com.hubs.loans.domain.service;

import com.hubs.loans.domain.entity.Installment;
import com.hubs.loans.domain.entity.Loan;
import com.hubs.loans.domain.value.loan.PayLoanResult;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PayLoanService {
    public PayLoanResult pay(Loan loan, BigDecimal amount) {
        BigDecimal remainingAmount = BigDecimal.valueOf(amount.doubleValue());
        BigDecimal totalPaidAmount = BigDecimal.ZERO;

        for (Installment unpaidInstallment : loan.unpaidInstallments()) {
            if (unpaidInstallment.isPayableToday() && unpaidInstallment.isPayableWithAmount(remainingAmount)) {
                unpaidInstallment.pay();
                remainingAmount = remainingAmount.subtract(unpaidInstallment.getPaidAmount());
                totalPaidAmount = totalPaidAmount.add(unpaidInstallment.getPaidAmount());
            }
        }

        if (loan.unpaidInstallments().isEmpty()) {
            loan.complete();
        }

        return new PayLoanResult();
    }
}
