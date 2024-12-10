package com.hubs.loans.application.service;

import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    /*
    public PaymentResult payLoan(Long loanId, BigDecimal paymentAmount) {
        Loan loan = loanRepo.findById(loanId)
                .orElseThrow(() -> new EntityNotFoundException("Loan not found"));

        List<LoanInstallment> unpaidInstallments = loanInstallmentRepo.findUnpaidInstallments(loanId);
        BigDecimal remainingAmount = paymentAmount;
        int installmentsPaid = 0;

        for (LoanInstallment installment : unpaidInstallments) {
            if (isBeyondPaymentWindow(installment.getDueDate())) {
                continue; // Skip installments beyond 3 months.
            }

            BigDecimal installmentAmount = calculateInstallmentWithPenaltyOrReward(installment);

            if (remainingAmount.compareTo(installmentAmount) >= 0) {
                installment.pay(installmentAmount);
                remainingAmount = remainingAmount.subtract(installmentAmount);
                installmentsPaid++;
            } else {
                break; // Stop if payment cannot fully cover the next installment.
            }
        }

        boolean isLoanFullyPaid = unpaidInstallments.stream().allMatch(LoanInstallment::isPaid);
        loan.setPaid(isLoanFullyPaid);
        loanRepo.save(loan);

        return new PaymentResult(installmentsPaid, paymentAmount.subtract(remainingAmount), isLoanFullyPaid);
    }

     */
}

