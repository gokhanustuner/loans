package com.hubs.loans.application.service;

import com.hubs.loans.application.command.CreateLoanCommand;
import com.hubs.loans.application.query.ListLoansQuery;
import com.hubs.loans.domain.entity.Customer;
import com.hubs.loans.domain.entity.Loan;
import com.hubs.loans.domain.exception.CustomerNotFoundException;
import com.hubs.loans.domain.repository.CustomerRepository;
import com.hubs.loans.domain.repository.LoanRepository;
import com.hubs.loans.domain.value.customer.CreditLimit;
import com.hubs.loans.domain.value.customer.CustomerId;
import com.hubs.loans.domain.value.installment.NumberOfInstallments;
import com.hubs.loans.domain.value.loan.InterestRate;
import com.hubs.loans.domain.value.loan.LoanAmount;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private LoanRepository loanRepository;

    @InjectMocks
    private LoanService loanService;

    @Test
    void create_loan_successfully_creates_loan_when_every_rule_is_ok() {
        CustomerId customerId = new CustomerId(UUID.randomUUID());
        BigDecimal loanAmount = BigDecimal.valueOf(10000);
        InterestRate interestRate = new InterestRate(0.4);
        NumberOfInstallments numberOfInstallments = new NumberOfInstallments(12);

        CreateLoanCommand createLoanCommand = new CreateLoanCommand(
                customerId,
                loanAmount,
                interestRate,
                numberOfInstallments
        );

        Customer mockCustomer = mock(Customer.class);
        Loan mockLoan = mock(Loan.class);

        when(customerRepository.findByIdWithLock(customerId)).thenReturn(mockCustomer);
        LoanAmount calculatedLoanAmount = LoanAmount.of(loanAmount, interestRate);
        when(mockCustomer.makeLoanWithInstallments(
                calculatedLoanAmount,
                numberOfInstallments
        )).thenReturn(mockLoan);
        when(loanRepository.save(mockLoan)).thenReturn(mockLoan);

        Loan result = loanService.createLoan(createLoanCommand);

        assertEquals(mockLoan, result);
        verify(customerRepository, times(1)).findByIdWithLock(customerId);
        verify(mockCustomer, times(1))
                .makeLoanWithInstallments(calculatedLoanAmount, numberOfInstallments);
        verify(loanRepository, times(1)).save(mockLoan);
    }

    @Test
    void create_loan_throws_customer_not_found_exception_when_customer_is_absent() {
        CustomerId customerId = new CustomerId(UUID.randomUUID());
        BigDecimal rawLoanAmount = BigDecimal.valueOf(10000);
        InterestRate interestRate = new InterestRate(0.5);
        NumberOfInstallments numberOfInstallments = new NumberOfInstallments(12);
        CreateLoanCommand createLoanCommand = new CreateLoanCommand(
                customerId,
                rawLoanAmount,
                interestRate,
                numberOfInstallments
        );

        when(customerRepository.findByIdWithLock(customerId))
                .thenThrow(new CustomerNotFoundException("Customer not found"));

        assertThrows(CustomerNotFoundException.class, () -> loanService.createLoan(createLoanCommand));
        verify(customerRepository, times(1)).findByIdWithLock(customerId);
        verifyNoInteractions(loanRepository);
    }

    @Test
    void list_loans_successfully_lists_loans_when_customer_and_loans_exist() {
        CustomerId customerId = new CustomerId(UUID.randomUUID());
        int page = 1;

        ListLoansQuery listLoansQuery = new ListLoansQuery(customerId, page);
        Loan mockLoan = mock(Loan.class);
        List<Loan> mockLoanList = List.of(mockLoan);

        when(loanRepository.findByCustomerId(customerId, page)).thenReturn(mockLoanList);

        List<Loan> result = loanService.listLoans(listLoansQuery);

        assertFalse(result.isEmpty());
        assertEquals(mockLoanList, result);
        verify(loanRepository, times(1)).findByCustomerId(customerId, page);
        verifyNoInteractions(customerRepository);
    }

    @Test
    void list_loans_successfully_returns_empty_list_when_no_loan_exists_for_customer() {
        CustomerId customerId = new CustomerId(UUID.randomUUID());
        int page = 1;

        ListLoansQuery listLoansQuery = new ListLoansQuery(customerId, page);
        List<Loan> mockLoanList = Collections.emptyList();
        Customer mockCustomer = new Customer(
                customerId.id(),
                "John",
                "Doe",
                new CreditLimit(BigDecimal.ZERO, BigDecimal.ZERO)
        );

        when(loanRepository.findByCustomerId(customerId, page)).thenReturn(List.of());
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(mockCustomer));

        List<Loan> result = loanService.listLoans(listLoansQuery);

        assertTrue(result.isEmpty());
        assertEquals(mockLoanList, result);
        verify(loanRepository, times(1)).findByCustomerId(customerId, page);
        verify(customerRepository, times(1)).findById(customerId);
    }
}
