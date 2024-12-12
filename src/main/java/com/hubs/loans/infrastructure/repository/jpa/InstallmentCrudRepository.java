package com.hubs.loans.infrastructure.repository.jpa;

import com.hubs.loans.domain.entity.Installment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface InstallmentCrudRepository extends CrudRepository<Installment, Long> {

    @Query("SELECT i FROM Installment i WHERE i.loan.id = :loanId")
    List<Installment> findByLoanId(@Param("loanId") UUID loanId);

    @Query("SELECT i FROM Installment i " +
            "JOIN i.loan l " +
            "JOIN l.customer c " +
            "WHERE l.id = :loanId AND c.id = :customerId")
    List<Installment> findByCustomerIdAndLoanId(@Param("customerId") UUID customerId, @Param("loanId") UUID loanId);
}
