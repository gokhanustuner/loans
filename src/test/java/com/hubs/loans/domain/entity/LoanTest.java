package com.hubs.loans.domain.entity;

import com.hubs.loans.domain.value.installment.NumberOfInstallments;
import com.hubs.loans.domain.value.loan.InterestRate;
import com.hubs.loans.domain.value.loan.LoanAmount;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class LoanTest {

    @Test
    public void loan_makes_installment_correctly() {
        Loan loan = Loan.builderWithId()
                .loanAmount(LoanAmount.of(LoanAmount.MIN, new InterestRate(0.35)))
                .numberOfInstallments(new NumberOfInstallments(9))
                .isPaid(false)
                .build();

        loan.makeInstallment(2);
    }

    @Test
    public void calculate_installment_amount_correctly_calculates() {
        NumberOfInstallments numberOfInstallments = new NumberOfInstallments(9);
        BigDecimal loanRawAmount = BigDecimal.valueOf(1376);

        Loan loan = Loan.builderWithId()
                .loanAmount(LoanAmount.of(loanRawAmount, new InterestRate(0.35)))
                .numberOfInstallments(new NumberOfInstallments(9))
                .isPaid(false)
                .build();

        assertTrue(
                loan.calculateInstallmentAmount()
                        .compareTo(
                                BigDecimal.valueOf(
                                        loanRawAmount.doubleValue() * (1 + 0.35) / numberOfInstallments.value()
                                )
                        ) == 0
        );
    }

    @Test
    public void complete_sets_loan_is_paid_as_true() {
        Loan loan = Loan.builderWithId().isPaid(false).build();
        loan.complete();
        assertTrue(loan.getIsPaid());
    }

    @Test
    public void builder_with_id_returns_a_loan_with_a_uuid() {
        Loan loan = Loan.builderWithId().build();
        assertInstanceOf(UUID.class, loan.getId());
    }

    @Test
    public void unpaid_installments_returns_installments_not_paid() {
        Loan loan = Loan.builder().build();
        Installment installmentOne = Installment.builder().isPaid(false).build();
        Installment installmentTwo = Installment.builder().isPaid(true).build();
        Installment installmentThree = Installment.builder().isPaid(false).build();
        loan.setInstallments(List.of(installmentOne, installmentTwo, installmentThree));

        List<Installment> installments = loan.unpaidInstallments();

        assertEquals(2, installments.size());
        assertArrayEquals(new Installment[] { installmentOne, installmentThree }, installments.toArray());
    }
}
