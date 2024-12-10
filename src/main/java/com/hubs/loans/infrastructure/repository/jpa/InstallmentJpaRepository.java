package com.hubs.loans.infrastructure.repository.jpa;

import com.hubs.loans.domain.entity.Installment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstallmentJpaRepository extends JpaRepository<Installment, Long> {
    /*
    // Find installments by loan ID
    List<LoanInstallment> findByLoanId(LoanId loanId);

    // Find unpaid installments for a loan (sorted by due date)
    List<LoanInstallment> findByLoanIdAndIsPaidFalseOrderByDueDateAsc(LoanId loanId);

     */
}
