package com.hubs.loans.infrastructure.repository.jpa;

import com.hubs.loans.domain.entity.Loan;
import com.hubs.loans.domain.value.loan.LoanId;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoanJpaRepository extends JpaRepository<Loan, LoanId> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "jakarta.persistence.lock.timeout", value = "60000")})
    @Query("SELECT l FROM Loan l WHERE l.id = :id")
    Optional<Loan> findByIdWithLock(@Param("id") UUID loanId);

    List<Loan> findByCustomerId(UUID customerId, Pageable pageable);

    @Query("SELECT l FROM Loan l WHERE l.customer.id = :customerId AND l.id = :loanId")
    Optional<Loan> findByCustomerIdAndLoanId(UUID customerId, UUID loanId);
}
