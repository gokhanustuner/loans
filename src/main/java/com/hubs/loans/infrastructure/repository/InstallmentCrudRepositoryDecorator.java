package com.hubs.loans.infrastructure.repository;

import com.hubs.loans.domain.entity.Installment;
import com.hubs.loans.domain.repository.InstallmentRepository;
import com.hubs.loans.domain.value.customer.CustomerId;
import com.hubs.loans.domain.value.loan.LoanId;
import com.hubs.loans.infrastructure.repository.jpa.InstallmentCrudRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class InstallmentCrudRepositoryDecorator implements InstallmentRepository {

    private final InstallmentCrudRepository installmentCrudRepository;

    @Override
    public List<Installment> findByLoanId(LoanId loanId) {
        return installmentCrudRepository.findByLoanId(loanId.id());
    }

    @Override
    public List<Installment> findByCustomerIdAndLoanId(CustomerId customerId, LoanId loanId) {
        return installmentCrudRepository.findByCustomerIdAndLoanId(customerId.id(), loanId.id());
    }
}
