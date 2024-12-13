package com.hubs.loans.domain.service;

import com.hubs.loans.domain.entity.Installment;
import com.hubs.loans.domain.entity.Loan;
import com.hubs.loans.domain.value.loan.PayLoanResult;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PayLoanService {
    public PayLoanResult pay(Loan loan, BigDecimal amount) {
        List<Installment> unpaidInstallments = loan.unpaidInstallments();

        BigDecimal availableAmount = amount;
        BigDecimal totalAmountPaid = BigDecimal.ZERO;
        int numberOfInstallmentsPaid = 0;

        for (Installment unpaidInstallment : unpaidInstallments) {
            if (unpaidInstallment.isPayableToday() && unpaidInstallment.isPayableWithAmount(availableAmount)) {
                unpaidInstallment.pay();
                availableAmount = availableAmount.subtract(unpaidInstallment.getPaidAmount());
                totalAmountPaid = totalAmountPaid.add(unpaidInstallment.getPaidAmount());
                numberOfInstallmentsPaid++;
            }
        }

        if (unpaidInstallments.isEmpty()) {
            loan.complete();
        }

        return new PayLoanResult(numberOfInstallmentsPaid, totalAmountPaid, loan.getIsPaid());
    }
}
