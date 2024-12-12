package com.hubs.loans.domain.factory;

import com.hubs.loans.domain.entity.Customer;
import com.hubs.loans.domain.entity.Loan;
import com.hubs.loans.domain.value.customer.CreditLimit;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoanFactoryTest {

    @InjectMocks
    private LoanFactory loanFactory;

    @Mock
    private InstallmentsFactory installmentsFactory;

    public void loan_factory_creates_loan_as_expected_when_rules_of_creation_is_ok() {
        Customer customer =
                new Customer(
                        UUID.randomUUID(),
                        "John",
                        "Doe",
                        new CreditLimit(BigDecimal.ZERO, BigDecimal.valueOf(198000))
                );


        //when(installmentsFactory.createInstallmentsWithLoan(customer.makeLoan()))
    }
}
