package com.hubs.loans.infrastructure.repository.jpa;

import com.hubs.loans.domain.entity.Loan;
import com.hubs.loans.domain.value.customer.CustomerId;
import com.hubs.loans.domain.value.loan.LoanId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface LoanJpaRepository extends JpaRepository<Loan, LoanId> {
    List<Loan> findByCustomerId(UUID customerId, Pageable pageable);
}
