package com.hubs.loans.infrastructure.repository;

import com.hubs.loans.domain.entity.Loan;
import com.hubs.loans.domain.exception.LoanNotFoundException;
import com.hubs.loans.domain.repository.LoanRepository;
import com.hubs.loans.domain.value.customer.CustomerId;
import com.hubs.loans.domain.value.loan.LoanId;
import com.hubs.loans.infrastructure.repository.jpa.LoanJpaRepository;
import jakarta.persistence.LockTimeoutException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LoanJpaRepositoryDecorator implements LoanRepository {

    private final static int NUMBER_OF_ITEMS_PER_PAGE = 10;

    private final LoanJpaRepository loanJpaRepository;

    @Override
    public Loan findByIdWithLock(LoanId loanId) {
        try {
            return loanJpaRepository
                    .findByIdWithLock(loanId.id())
                    .orElseThrow(() -> new LoanNotFoundException("Loan not found"));
        } catch (LockTimeoutException e) {
            throw new RuntimeException("System error occurred");
        }
    }

    @Override
    public List<Loan> findByCustomerId(CustomerId customerId, int page) {
        return loanJpaRepository.findByCustomerId(
                customerId.id(),
                PageRequest.of(page, NUMBER_OF_ITEMS_PER_PAGE)
        );
    }

    @Override
    public Optional<Loan> findByCustomerIdAndLoanId(CustomerId customerId, LoanId loanId) {
        return loanJpaRepository.findByCustomerIdAndLoanId(
                customerId.id(),
                loanId.id()
        );
    }

    @Override
    public Loan save(Loan loan) {
        return loanJpaRepository.save(loan);
    }
}
