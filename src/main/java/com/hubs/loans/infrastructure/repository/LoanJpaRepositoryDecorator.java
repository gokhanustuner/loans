package com.hubs.loans.infrastructure.repository;

import com.hubs.loans.domain.entity.Loan;
import com.hubs.loans.domain.repository.LoanRepository;
import com.hubs.loans.domain.value.customer.CustomerId;
import com.hubs.loans.infrastructure.repository.jpa.LoanJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class LoanJpaRepositoryDecorator implements LoanRepository {

    private final static int NUMBER_OF_ITEMS_PER_PAGE = 10;

    private final LoanJpaRepository loanJpaRepository;

    @Override
    public List<Loan> findByCustomerId(CustomerId customerId, int page) {
        return loanJpaRepository.findByCustomerId(
                customerId.id(),
                PageRequest.of(page, NUMBER_OF_ITEMS_PER_PAGE)
        );
    }

    @Override
    public Loan save(Loan loan) {
        return loanJpaRepository.save(loan);
    }
}
