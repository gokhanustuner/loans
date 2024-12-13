package com.hubs.loans.application.service;

import com.hubs.loans.application.query.ListInstallmentsQuery;
import com.hubs.loans.domain.entity.Customer;
import com.hubs.loans.domain.entity.Installment;
import com.hubs.loans.domain.exception.CustomerNotFoundException;
import com.hubs.loans.domain.exception.LoanNotFoundException;
import com.hubs.loans.domain.repository.CustomerRepository;
import com.hubs.loans.domain.repository.InstallmentRepository;
import com.hubs.loans.domain.repository.LoanRepository;
import com.hubs.loans.domain.value.customer.CustomerId;
import com.hubs.loans.domain.value.loan.LoanId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InstallmentServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private InstallmentRepository installmentRepository;

    @InjectMocks
    private InstallmentService installmentService;

    private ListInstallmentsQuery query;

    @BeforeEach
    void setUp() {
        query = ListInstallmentsQuery.of(UUID.randomUUID(), UUID.randomUUID());
    }

    @Test
    void list_installments_returns_installments_when_installments_exist() {
        Installment installment = mock(Installment.class);
        when(installmentRepository.findByCustomerIdAndLoanId(query.customerId(), query.loanId()))
                .thenReturn(List.of(installment));

        List<Installment> result = installmentService.listInstallments(query);

        assertEquals(1, result.size());
        assertEquals(installment, result.get(0));
        verify(installmentRepository, times(1))
                .findByCustomerIdAndLoanId(query.customerId(), query.loanId());
    }

    @Test
    void list_installments_when_installments_do_not_exist_and_customer_does_not_exist_throws_customer_not_found_exception() {
        when(installmentRepository.findByCustomerIdAndLoanId(query.customerId(), query.loanId()))
                .thenReturn(List.of());
        when(customerRepository.findById(query.customerId())).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> installmentService.listInstallments(query));
        verify(customerRepository, times(1)).findById(query.customerId());
    }

    @Test
    void list_installments_throws_loan_not_found_exception_when_installments_do_not_exist_and_loan_does_not_exist() {
        when(installmentRepository.findByCustomerIdAndLoanId(query.customerId(), query.loanId()))
                .thenReturn(List.of());
        when(customerRepository.findById(query.customerId())).thenReturn(Optional.of(mock(Customer.class)));
        when(loanRepository.findByCustomerIdAndLoanId(query.customerId(), query.loanId())).thenReturn(Optional.empty());

        assertThrows(LoanNotFoundException.class, () -> installmentService.listInstallments(query));
        verify(loanRepository, times(1)).findByCustomerIdAndLoanId(query.customerId(), query.loanId());
    }

    @Test
    void list_installments_when_installments_exist_does_not_query_for_customer_and_loan() {
        Installment installment = mock(Installment.class);
        when(installmentRepository.findByCustomerIdAndLoanId(query.customerId(), query.loanId()))
                .thenReturn(List.of(installment));

        List<Installment> result = installmentService.listInstallments(query);

        assertEquals(1, result.size());
        verify(customerRepository, never()).findById(any(CustomerId.class));
        verify(loanRepository, never()).findByCustomerIdAndLoanId(any(CustomerId.class), any(LoanId.class));
    }
}
