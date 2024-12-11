package com.hubs.loans.domain.factory;

import com.hubs.loans.domain.entity.Installment;
import com.hubs.loans.domain.entity.Loan;
import com.hubs.loans.domain.value.installment.NumberOfInstallments;
import com.hubs.loans.domain.value.loan.InterestRate;
import com.hubs.loans.domain.value.loan.LoanAmount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(classes = { InstallmentsFactory.class })
class InstallmentsFactoryTest {

    private InstallmentsFactory installmentsFactory;

    @BeforeEach
    void setUp() {
        installmentsFactory = new InstallmentsFactory();
    }

    @Test
    void create_installments_returns_correct_number_of_installments() {
        NumberOfInstallments numberOfInstallments = new NumberOfInstallments(6);

        Loan loan = Loan.builderWithId()
                .loanAmount(LoanAmount.of(LoanAmount.MIN, new InterestRate(0.3)))
                .numberOfInstallments(numberOfInstallments)
                .build();


        System.out.println("NumberOfInstallments: " + numberOfInstallments.value());

        List<Installment> installments = installmentsFactory.createInstallmentsWithLoan(loan);

        assertEquals(6, installments.size());
    }
}
