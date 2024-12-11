package com.hubs.loans.application.service;

import com.hubs.loans.application.command.CreateLoanCommand;
import com.hubs.loans.application.query.ListLoansQuery;
import com.hubs.loans.domain.entity.Customer;
import com.hubs.loans.domain.factory.LoanFactory;
import com.hubs.loans.domain.repository.CustomerRepository;
import com.hubs.loans.domain.repository.LoanRepository;
import com.hubs.loans.domain.entity.Loan;
import com.hubs.loans.domain.value.loan.LoanAmount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final CustomerRepository customerRepository;

    private final LoanFactory loanFactory;

    private final LoanRepository loanRepository;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Loan createLoan(CreateLoanCommand createLoanCommand) {
        Customer customer = customerRepository.findById(createLoanCommand.customerId());

        return loanRepository.save(
                loanFactory.createLoanWithCustomer(
                    customer,
                    LoanAmount.of(createLoanCommand.loanAmount(), createLoanCommand.interestRate()),
                    createLoanCommand.numberOfInstallments()
            )
        );
    }

    @Transactional(readOnly = true)
    public List<Loan> listLoans(ListLoansQuery listLoansQuery) {
        return loanRepository.findByCustomerId(listLoansQuery.customerId(), listLoansQuery.page());
    }

    public void payLoan() {

    }


}
