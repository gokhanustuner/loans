package com.hubs.loans.infrastructure.repository;

import com.hubs.loans.domain.entity.Loan;
import com.hubs.loans.domain.exception.LoanNotFoundException;
import com.hubs.loans.domain.value.customer.CustomerId;
import com.hubs.loans.domain.value.loan.LoanId;
import com.hubs.loans.infrastructure.repository.jpa.LoanJpaRepository;
import jakarta.persistence.LockTimeoutException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanJpaRepositoryDecoratorTest {

    @Mock
    private LoanJpaRepository loanJpaRepository;

    @InjectMocks
    private LoanJpaRepositoryDecorator loanRepositoryDecorator;

    @Test
    void find_by_id_with_lock_returns_loan_when_found() {
        UUID loanId = UUID.randomUUID();
        LoanId mockLoanId = new LoanId(loanId);
        Loan mockLoan = mock(Loan.class);

        when(loanJpaRepository.findByIdWithLock(loanId)).thenReturn(Optional.of(mockLoan));

        Loan result = loanRepositoryDecorator.findByIdWithLock(mockLoanId);

        assertEquals(mockLoan, result);
        verify(loanJpaRepository, times(1)).findByIdWithLock(loanId);
    }

    @Test
    void find_by_id_with_lock_throws_loan_not_found_exception_when_not_found() {
        UUID loanId = UUID.randomUUID();
        LoanId mockLoanId = new LoanId(loanId);

        when(loanJpaRepository.findByIdWithLock(loanId)).thenReturn(Optional.empty());

        assertThrows(LoanNotFoundException.class, () -> loanRepositoryDecorator.findByIdWithLock(mockLoanId));
        verify(loanJpaRepository, times(1)).findByIdWithLock(loanId);
    }

    @Test
    void find_by_id_with_lock_handles_lock_timeout_exception_as_runtime_exception() {
        UUID loanId = UUID.randomUUID();
        LoanId mockLoanId = new LoanId(loanId);

        when(loanJpaRepository.findByIdWithLock(loanId)).thenThrow(new LockTimeoutException());

        assertThrows(RuntimeException.class, () -> loanRepositoryDecorator.findByIdWithLock(mockLoanId));
        verify(loanJpaRepository, times(1)).findByIdWithLock(loanId);
    }

    @Test
    void find_by_customer_id_returns_loans_for_customer() {
        UUID customerId = UUID.randomUUID();
        CustomerId mockCustomerId = new CustomerId(customerId);
        int page = 1;
        Loan mockLoan = mock(Loan.class);
        List<Loan> mockLoanList = List.of(mockLoan);

        when(loanJpaRepository.findByCustomerId(customerId, PageRequest.of(page, 10))).thenReturn(mockLoanList);

        List<Loan> result = loanRepositoryDecorator.findByCustomerId(mockCustomerId, page);

        assertFalse(result.isEmpty());
        assertEquals(mockLoanList, result);
        verify(loanJpaRepository, times(1))
                .findByCustomerId(customerId, PageRequest.of(page, 10));
    }

    @Test
    void find_by_customer_id_and_loan_id_returns_loan_when_found() {
        UUID customerId = UUID.randomUUID();
        UUID loanId = UUID.randomUUID();
        CustomerId mockCustomerId = new CustomerId(customerId);
        LoanId mockLoanId = new LoanId(loanId);
        Loan mockLoan = mock(Loan.class);

        when(loanJpaRepository.findByCustomerIdAndLoanId(customerId, loanId)).thenReturn(Optional.of(mockLoan));

        Optional<Loan> result = loanRepositoryDecorator.findByCustomerIdAndLoanId(mockCustomerId, mockLoanId);

        assertTrue(result.isPresent());
        assertEquals(mockLoan, result.get());
        verify(loanJpaRepository, times(1)).findByCustomerIdAndLoanId(customerId, loanId);
    }

    @Test
    void find_by_customer_id_and_loan_id_returns_empty_when_not_found() {
        UUID customerId = UUID.randomUUID();
        UUID loanId = UUID.randomUUID();
        CustomerId mockCustomerId = new CustomerId(customerId);
        LoanId mockLoanId = new LoanId(loanId);

        when(loanJpaRepository.findByCustomerIdAndLoanId(customerId, loanId)).thenReturn(Optional.empty());

        Optional<Loan> result = loanRepositoryDecorator.findByCustomerIdAndLoanId(mockCustomerId, mockLoanId);

        assertTrue(result.isEmpty());
        verify(loanJpaRepository, times(1)).findByCustomerIdAndLoanId(customerId, loanId);
    }

    @Test
    void save_saves_loan_successfully() {
        Loan mockLoan = mock(Loan.class);

        when(loanJpaRepository.save(mockLoan)).thenReturn(mockLoan);

        Loan result = loanRepositoryDecorator.save(mockLoan);

        assertEquals(mockLoan, result);
        verify(loanJpaRepository, times(1)).save(mockLoan);
    }
}
