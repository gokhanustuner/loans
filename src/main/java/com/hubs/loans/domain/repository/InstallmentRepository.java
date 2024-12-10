package com.hubs.loans.domain.repository;

import com.hubs.loans.domain.entity.Installment;
import com.hubs.loans.domain.value.loan.LoanId;

import java.util.List;

public interface InstallmentRepository {
    List<Installment> findByLoanId(LoanId loanId);
}
