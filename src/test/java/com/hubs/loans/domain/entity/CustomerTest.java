package com.hubs.loans.domain.entity;

import com.hubs.loans.domain.exception.InsufficientCreditLimitException;
import com.hubs.loans.domain.value.customer.CreditLimit;
import com.hubs.loans.domain.value.installment.NumberOfInstallments;
import com.hubs.loans.domain.value.loan.InterestRate;
import com.hubs.loans.domain.value.loan.LoanAmount;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

    @Test
    public void customer_makes_loan_correctly_and_keeps_its_credit_limit_state_consistent() {
        Customer customer =
                new Customer(
                        UUID.randomUUID(),
                        "John",
                        "Doe",
                        new CreditLimit(BigDecimal.ZERO, BigDecimal.valueOf(100000))
                );

        LoanAmount loanAmount = LoanAmount.of(BigDecimal.valueOf(10000), new InterestRate(0.41));
        NumberOfInstallments numberOfInstallments = new NumberOfInstallments(12);

        Loan loan = customer.makeLoanWithInstallments(loanAmount, numberOfInstallments);

        assertEquals(loan.getCustomer(), customer);
        assertFalse(loan.getIsPaid());
        assertEquals(loan.getLoanAmount(), loanAmount);
        assertEquals(loan.getNumberOfInstallments(), numberOfInstallments);

        assertEquals(customer.usedCreditLimit(), loanAmount.amount());
    }

    @Test
    public void customer_has_insufficient_credit_limit_insufficient_credit_limit_exception_is_thrown() {
        Customer customer =
                new Customer(
                        UUID.randomUUID(),
                        "John",
                        "Doe",
                        new CreditLimit(BigDecimal.ZERO, BigDecimal.valueOf(10000))
                );

        LoanAmount loanAmount = LoanAmount.of(BigDecimal.valueOf(15000), new InterestRate(0.41));
        NumberOfInstallments numberOfInstallments = new NumberOfInstallments(12);

        assertThrows(InsufficientCreditLimitException.class, () -> customer.makeLoanWithInstallments(loanAmount, numberOfInstallments));

        assertEquals(BigDecimal.ZERO, customer.usedCreditLimit());
    }

    @Test
    public void increase_used_credit_limit_correctly_increases_customers_used_credit_limit() {
        Customer customer =
                new Customer(
                        UUID.randomUUID(),
                        "John",
                        "Doe",
                        new CreditLimit(BigDecimal.ZERO, BigDecimal.valueOf(10000))
                );

        customer.increaseUsedCreditLimit(BigDecimal.TEN);

        assertEquals(BigDecimal.TEN, customer.usedCreditLimit());
    }

    @Test
    public void decrease_used_credit_limit_correctly_decreases_customers_used_credit_limit() {
        Customer customer =
                new Customer(
                        UUID.randomUUID(),
                        "John",
                        "Doe",
                        new CreditLimit(BigDecimal.TEN, BigDecimal.valueOf(10000))
                );

        customer.decreaseUsedCreditLimit(BigDecimal.ONE);

        assertEquals(BigDecimal.valueOf(9), customer.usedCreditLimit());
    }

    @Test
    public void used_credit_limit_returns_customers_used_credit_limit() {
        Customer customer =
                new Customer(
                        UUID.randomUUID(),
                        "John",
                        "Doe",
                        new CreditLimit(BigDecimal.TEN, BigDecimal.valueOf(10000))
                );

        assertEquals(BigDecimal.TEN, customer.usedCreditLimit());
    }
}
