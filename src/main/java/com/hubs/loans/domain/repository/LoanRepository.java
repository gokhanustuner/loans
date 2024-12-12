package com.hubs.loans.domain.repository;

import com.hubs.loans.domain.entity.Loan;
import com.hubs.loans.domain.value.customer.CustomerId;
import com.hubs.loans.domain.value.loan.LoanId;

import java.util.List;
import java.util.Optional;

public interface LoanRepository {
    Loan findById(LoanId loanId);
    List<Loan> findByCustomerId(CustomerId customerId, int page);
    Optional<Loan> findByCustomerIdAndLoanId(CustomerId customerId, LoanId loanId);
    Loan save(Loan loan);
}
