package com.hubs.loans.application.service;

import com.hubs.loans.application.command.PayLoanCommand;
import com.hubs.loans.domain.entity.Loan;
import com.hubs.loans.domain.repository.LoanRepository;
import com.hubs.loans.domain.service.PayLoanService;
import com.hubs.loans.domain.value.loan.PayLoanResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final LoanRepository loanRepository;

    private final PayLoanService payLoanService;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Loan payLoan(PayLoanCommand payLoanCommand) {
        Loan loan = loanRepository.findById(payLoanCommand.loanId());
        PayLoanResult payLoanResult = payLoanService.pay(loan, payLoanCommand.amount());
        loanRepository.save(loan);

        return loan;
    }

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

