package com.hubs.loans.domain.factory;

import com.hubs.loans.domain.entity.Customer;
import com.hubs.loans.domain.entity.Installment;
import com.hubs.loans.domain.entity.Loan;
import com.hubs.loans.domain.value.customer.CreditLimit;
import com.hubs.loans.domain.value.installment.NumberOfInstallments;
import com.hubs.loans.domain.value.loan.InterestRate;
import com.hubs.loans.domain.value.loan.LoanAmount;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class LoanFactoryTest {

    @InjectMocks
    private LoanFactory loanFactory;

    @Mock
    private InstallmentsFactory installmentsFactory;

    @Test
    public void loan_factory_creates_loan_and_places_its_installments() {
        Customer customer =
                new Customer(
                        UUID.randomUUID(),
                        "John",
                        "Doe",
                        new CreditLimit(BigDecimal.ZERO, BigDecimal.valueOf(198000))
                );

        List<Installment> installmentList = List.of(new Installment(), new Installment());
        when(installmentsFactory.createInstallmentsWithLoan(any(Loan.class))).thenReturn(installmentList);

        Loan loan = loanFactory.createLoanWithCustomer(
                customer,
                LoanAmount.of(LoanAmount.MIN, new InterestRate(0.3)),
                new NumberOfInstallments(6)
        );

        assertArrayEquals(installmentList.toArray(), loan.getInstallments().toArray());
    }
}
