package com.hubs.loans.domain.repository;

import com.hubs.loans.domain.entity.Loan;
import com.hubs.loans.domain.value.customer.CustomerId;
import com.hubs.loans.domain.value.loan.LoanId;

import java.util.List;

public interface LoanRepository {
    Loan findById(LoanId loanId);
    List<Loan> findByCustomerId(CustomerId customerId, int page);
    Loan save(Loan loan);
}
