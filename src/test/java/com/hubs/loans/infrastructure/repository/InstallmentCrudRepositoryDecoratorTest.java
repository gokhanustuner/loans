package com.hubs.loans.infrastructure.repository;

import com.hubs.loans.domain.entity.Installment;
import com.hubs.loans.domain.value.customer.CustomerId;
import com.hubs.loans.domain.value.loan.LoanId;
import com.hubs.loans.infrastructure.repository.jpa.InstallmentCrudRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InstallmentCrudRepositoryDecoratorTest {

    @Mock
    private InstallmentCrudRepository installmentCrudRepository;

    @InjectMocks
    private InstallmentCrudRepositoryDecorator installmentCrudRepositoryDecorator;

    @Test
    void find_by_loan_id_delegates_to_installment_crud_repository_and_returns_expected_result() {
        LoanId loanId = new LoanId(UUID.randomUUID());
        List<Installment> expectedInstallments = List.of(mock(Installment.class));
        when(installmentCrudRepository.findByLoanId(loanId.id())).thenReturn(expectedInstallments);

        List<Installment> result = installmentCrudRepositoryDecorator.findByLoanId(loanId);

        assertEquals(expectedInstallments, result);
        verify(installmentCrudRepository, times(1)).findByLoanId(loanId.id());
    }

    @Test
    void find_by_customer_id_and_loan_id_delegates_to_installment_crud_repository_and_returns_expected_result() {
        CustomerId customerId = new CustomerId(UUID.randomUUID());
        LoanId loanId = new LoanId(UUID.randomUUID());
        List<Installment> expectedInstallments = List.of(mock(Installment.class));
        when(installmentCrudRepository.findByCustomerIdAndLoanId(customerId.id(), loanId.id()))
                .thenReturn(expectedInstallments);

        List<Installment> result = installmentCrudRepositoryDecorator.findByCustomerIdAndLoanId(customerId, loanId);

        assertEquals(expectedInstallments, result);
        verify(installmentCrudRepository, times(1))
                .findByCustomerIdAndLoanId(customerId.id(), loanId.id());
    }
}
