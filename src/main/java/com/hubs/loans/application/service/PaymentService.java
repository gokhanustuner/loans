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
    public PayLoanResult payLoan(PayLoanCommand payLoanCommand) {
        Loan loan = loanRepository.findById(payLoanCommand.loanId());
        return payLoanService.pay(loan, payLoanCommand.amount());
    }
}

