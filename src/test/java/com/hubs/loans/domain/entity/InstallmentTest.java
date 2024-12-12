package com.hubs.loans.domain.entity;

import com.hubs.loans.domain.value.customer.CreditLimit;
import com.hubs.loans.domain.value.installment.NumberOfInstallments;
import com.hubs.loans.domain.value.loan.InterestRate;
import com.hubs.loans.domain.value.loan.LoanAmount;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class InstallmentTest {

    @Test
    public void not_is_paid_returns_true_when_installment_not_paid() {
        Installment installment = Installment.builder().isPaid(false).build();

        assertTrue(installment.notIsPaid());
    }

    @Test
    public void not_is_paid_returns_false_when_installment_paid() {
        Installment installment = Installment.builder().isPaid(true).build();

        assertFalse(installment.notIsPaid());
    }

    @Test
    public void calculate_days_after_due_date_returns_correct_days_count() {
        Installment installment = Installment.builder().dueDate(LocalDate.now().minusDays(5)).build();

        assertEquals(5, installment.daysAfterDueDate());
    }

    @Test
    public void calculate_days_after_due_date_returns_zero_when_due_date_did_not_pass() {
        Installment installment = Installment.builder().dueDate(LocalDate.now().plusDays(5)).build();

        assertEquals(0, installment.daysAfterDueDate());
    }

    @Test
    public void calculate_days_after_due_date_returns_zero_when_due_date_is_today() {
        Installment installment = Installment.builder().dueDate(LocalDate.now()).build();

        assertEquals(0, installment.daysAfterDueDate());
    }

    @Test
    public void calculate_days_before_due_date_returns_correct_days_count() {
        Installment installment = Installment.builder().dueDate(LocalDate.now().plusDays(5)).build();

        assertEquals(5, installment.daysBeforeDueDate());
    }

    @Test
    public void calculate_days_before_due_date_returns_zero_when_due_date_did_pass() {
        Installment installment = Installment.builder().dueDate(LocalDate.now().minusDays(5)).build();

        assertEquals(0, installment.daysBeforeDueDate());
    }

    @Test
    public void calculate_days_before_due_date_returns_zero_when_due_date_is_today() {
        Installment installment = Installment.builder().dueDate(LocalDate.now()).build();

        assertEquals(0, installment.daysBeforeDueDate());
    }

    @Test
    public void is_paid_late_returns_true_when_installment_is_paid_late() {
        Installment installment = Installment.builder().dueDate(LocalDate.now().minusDays(5)).build();

        assertTrue(installment.isPaidLate());
    }

    @Test
    public void is_paid_late_returns_false_when_installment_is_paid_early() {
        Installment installment = Installment.builder().dueDate(LocalDate.now().plusDays(5)).build();

        assertFalse(installment.isPaidLate());
    }

    @Test
    public void is_paid_late_returns_false_when_installment_is_paid_on_time() {
        Installment installment = Installment.builder().dueDate(LocalDate.now()).build();

        assertFalse(installment.isPaidLate());
    }

    @Test
    public void is_paid_early_returns_true_when_installment_is_paid_early() {
        Installment installment = Installment.builder().dueDate(LocalDate.now().plusDays(5)).build();

        assertTrue(installment.isPaidEarly());
    }

    @Test
    public void is_paid_early_returns_false_when_installment_is_paid_late() {
        Installment installment = Installment.builder().dueDate(LocalDate.now().minusDays(5)).build();

        assertFalse(installment.isPaidEarly());
    }

    @Test
    public void is_paid_early_returns_false_when_installment_is_paid_on_time() {
        Installment installment = Installment.builder().dueDate(LocalDate.now()).build();

        assertFalse(installment.isPaidEarly());
    }

    @Test
    public void is_payable_today_returns_true_when_installment_due_date_is_within_three_months() {
        Installment installmentOne = Installment.builderWithIdAndDueDate(1).build();
        Installment installmentTwo = Installment.builderWithIdAndDueDate(2).build();
        Installment installmentThree = Installment.builderWithIdAndDueDate(3).build();

        assertTrue(installmentOne.isPayableToday());
        assertTrue(installmentTwo.isPayableToday());
        assertTrue(installmentThree.isPayableToday());
    }

    @Test
    public void is_payable_today_returns_false_when_installment_due_date_is_not_within_three_months() {
        Installment installment = Installment.builderWithIdAndDueDate(4).build();

        assertFalse(installment.isPayableToday());
    }

    @Test
    public void is_payable_today_returns_true_when_installment_due_date_before_current_date() {
        Installment installment = Installment.builder().dueDate(LocalDate.now().minusMonths(2)).build();

        assertTrue(installment.isPayableToday());
    }

    @Test
    public void calculate_penalized_amount_returns_correct_amount() {
        Installment installment =
                Installment.builder().dueDate(LocalDate.now().minusDays(5)).amount(BigDecimal.TEN).build();

        assertTrue(BigDecimal.valueOf(10.05).compareTo(installment.calculatePenalizedAmount()) == 0);
    }

    @Test
    public void calculate_discounted_amount_returns_correct_amount() {
        Installment installment =
                Installment.builder().dueDate(LocalDate.now().plusDays(5)).amount(BigDecimal.TEN).build();

        assertTrue(BigDecimal.valueOf(9.95).compareTo(installment.calculateDiscountedAmount()) == 0);
    }

    @Test
    public void is_payable_with_amount_returns_true_when_customer_pays_on_time_with_same_amount_of_installment_amount() {
        Installment installment = Installment.builder().dueDate(LocalDate.now()).amount(BigDecimal.valueOf(10000)).build();

        assertTrue(installment.isPayableWithAmount(BigDecimal.valueOf(10000)));
    }

    @Test
    public void is_payable_with_amount_returns_false_when_customer_pays_on_time_with_missing_amount() {
        Installment installment = Installment.builder().dueDate(LocalDate.now()).amount(BigDecimal.valueOf(10000)).build();

        assertFalse(installment.isPayableWithAmount(BigDecimal.valueOf(9999)));
    }

    @Test
    public void is_payable_with_amount_returns_true_when_customer_pays_on_time_with_more_amount() {
        Installment installment = Installment.builder().dueDate(LocalDate.now()).amount(BigDecimal.valueOf(10000)).build();

        assertTrue(installment.isPayableWithAmount(BigDecimal.valueOf(10001)));
    }

    @Test
    public void is_payable_with_amount_returns_true_when_customer_pays_early_with_same_amount_of_installment_amount() {
        Installment installment =
                Installment.builder()
                        .dueDate(LocalDate.now().plusDays(5))
                        .amount(BigDecimal.valueOf(10000))
                        .build();

        assertTrue(installment.isPayableWithAmount(BigDecimal.valueOf(10000)));
    }

    @Test
    public void is_payable_with_amount_returns_false_when_customer_pays_early_with_missing_amount() {
        Installment installment = Installment.builder().dueDate(LocalDate.now()).amount(BigDecimal.valueOf(10000)).build();

        assertFalse(installment.isPayableWithAmount(installment.calculateDiscountedAmount().subtract(BigDecimal.ONE)));
    }

    @Test
    public void is_payable_with_amount_returns_true_when_customer_pays_early_with_discounted_amount() {
        Installment installment = Installment.builder().dueDate(LocalDate.now()).amount(BigDecimal.valueOf(10000)).build();

        assertTrue(installment.isPayableWithAmount(installment.calculateDiscountedAmount()));
    }

    @Test
    public void is_payable_with_amount_returns_true_when_customer_pays_early_with_more_amount_than_discounted_amount() {
        Installment installment = Installment.builder().dueDate(LocalDate.now()).amount(BigDecimal.valueOf(10000)).build();

        assertTrue(installment.isPayableWithAmount(installment.calculateDiscountedAmount().add(BigDecimal.ONE)));
    }


    @Test
    public void is_payable_with_amount_returns_false_when_customer_pays_late_with_same_amount_of_installment_amount() {
        Installment installment =
                Installment.builder()
                        .dueDate(LocalDate.now().minusDays(5))
                        .amount(BigDecimal.valueOf(10000))
                        .build();

        assertFalse(installment.isPayableWithAmount(BigDecimal.valueOf(10000)));
    }

    @Test
    public void is_payable_with_amount_returns_false_when_customer_pays_late_with_missing_amount() {
        Installment installment = Installment.builder().dueDate(LocalDate.now()).amount(BigDecimal.valueOf(10000)).build();

        assertFalse(installment.isPayableWithAmount(installment.calculatePenalizedAmount().subtract(BigDecimal.ONE)));
    }

    @Test
    public void is_payable_with_amount_returns_true_when_customer_pays_late_with_penalized_amount() {
        Installment installment = Installment.builder().dueDate(LocalDate.now()).amount(BigDecimal.valueOf(10000)).build();

        assertTrue(installment.isPayableWithAmount(installment.calculatePenalizedAmount()));
    }

    @Test
    public void is_payable_with_amount_returns_true_when_customer_pays_late_with_more_amount_than_penalized_amount() {
        Installment installment = Installment.builder().dueDate(LocalDate.now()).amount(BigDecimal.valueOf(10000)).build();

        assertTrue(installment.isPayableWithAmount(BigDecimal.valueOf(10001)));
    }

    @Test
    public void pay_with_discount_keeps_installment_state_consistent_and_decreases_its_customers_used_credit_limit_properly() {
        Customer customer = new Customer(
                UUID.randomUUID(),
                "John",
                "Doe",
                new CreditLimit(BigDecimal.ZERO, BigDecimal.valueOf(90000))
        );
        Loan loan = customer.makeLoanWithInstallments(
                LoanAmount.of(
                        BigDecimal.valueOf(10000),
                        new InterestRate(0.5)
                ),
                new NumberOfInstallments(9)
        );

        Installment installment = loan.getInstallments().get(0);
        BigDecimal customerInitialUsedCreditLimit = customer.usedCreditLimit();

        installment.payWithDiscount();

        assertTrue(installment.isPaid());
        assertTrue(installment.getPaidAmount().compareTo(installment.calculateDiscountedAmount()) == 0);
        assertEquals(
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                installment.getPaymentDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );

        assertTrue(
                installment.customer().usedCreditLimit()
                        .compareTo(customerInitialUsedCreditLimit.subtract(installment.getAmount())) == 0
        );
    }

    @Test
    public void pay_with_penalty_keeps_installment_state_consistent_and_decreases_its_customers_used_credit_limit_properly() {
        Customer customer = new Customer(
                UUID.randomUUID(),
                "John",
                "Doe",
                new CreditLimit(BigDecimal.ZERO, BigDecimal.valueOf(90000))
        );
        Loan loan = customer.makeLoanWithInstallments(
                LoanAmount.of(
                        BigDecimal.valueOf(10000),
                        new InterestRate(0.5)
                ),
                new NumberOfInstallments(9)
        );
        Installment installment = loan.getInstallments().get(0);
        BigDecimal customerInitialUsedCreditLimit = installment.customer().usedCreditLimit();

        installment.payWithPenalty();

        assertTrue(installment.isPaid());
        assertTrue(installment.getPaidAmount().compareTo(installment.calculatePenalizedAmount()) == 0);
        assertEquals(
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                installment.getPaymentDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );

        assertTrue(
                installment.customer().usedCreditLimit()
                        .compareTo(customerInitialUsedCreditLimit.subtract(installment.getAmount())) == 0
        );
    }

    @Test
    public void pay_ordinary_keeps_installment_state_consistent_and_decreases_its_customers_used_credit_limit_properly() {
        Customer customer = new Customer(
                UUID.randomUUID(),
                "John",
                "Doe",
                new CreditLimit(BigDecimal.ZERO, BigDecimal.valueOf(90000))
        );
        Loan loan = customer.makeLoanWithInstallments(
                LoanAmount.of(
                        BigDecimal.valueOf(10000),
                        new InterestRate(0.5)
                ),
                new NumberOfInstallments(9)
        );

        Installment installment = loan.getInstallments().get(0);
        BigDecimal customerInitialUsedCreditLimit = installment.customer().usedCreditLimit();

        installment.payOrdinary();

        assertTrue(installment.isPaid());
        assertTrue(installment.getPaidAmount().compareTo(installment.getAmount()) == 0);
        assertEquals(
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                installment.getPaymentDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );

        assertTrue(
                installment.customer().usedCreditLimit()
                        .compareTo(customerInitialUsedCreditLimit.subtract(installment.getAmount())) == 0
        );
    }

    @Test
    public void pay_pays_with_discount_when_installment_is_paid_early() {
        Customer customer = new Customer(
                UUID.randomUUID(),
                "John",
                "Doe",
                new CreditLimit(BigDecimal.ZERO, BigDecimal.valueOf(90000))
        );

        Loan loan = customer.makeLoanWithInstallments(
                LoanAmount.of(
                        BigDecimal.valueOf(10000),
                        new InterestRate(0.5)
                ),
                new NumberOfInstallments(9)
        );

        Installment installment = loan.getInstallments().get(0);
        BigDecimal customerInitialUsedCreditLimit = installment.customer().usedCreditLimit();

        installment.pay();

        assertTrue(installment.isPaid());
        assertTrue(installment.getPaidAmount().compareTo(installment.calculateDiscountedAmount()) == 0);
        assertEquals(
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                installment.getPaymentDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );

        assertTrue(
                installment.customer().usedCreditLimit()
                        .compareTo(customerInitialUsedCreditLimit.subtract(installment.getAmount())) == 0
        );
    }

    @Test
    public void pay_pays_with_penalty_when_installment_is_paid_late() {
        Customer customer = new Customer(
                UUID.randomUUID(),
                "John",
                "Doe",
                new CreditLimit(BigDecimal.ZERO, BigDecimal.valueOf(90000))
        );

        Loan loan = customer.makeLoanWithInstallments(
                LoanAmount.of(
                        BigDecimal.valueOf(10000),
                        new InterestRate(0.5)
                ),
                new NumberOfInstallments(9)
        );

        Installment installment = loan.getInstallments().get(0);
        installment.setDueDate(LocalDate.now().minusDays(5));
        BigDecimal customerInitialUsedCreditLimit = installment.customer().usedCreditLimit();

        installment.pay();

        assertTrue(installment.isPaid());
        assertTrue(installment.getPaidAmount().compareTo(installment.calculatePenalizedAmount()) == 0);
        assertEquals(
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                installment.getPaymentDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );

        assertTrue(
                installment.customer().usedCreditLimit()
                        .compareTo(customerInitialUsedCreditLimit.subtract(installment.getAmount())) == 0
        );
    }

    @Test
    public void pay_pays_ordinary_when_installment_is_paid_on_time() {
        Customer customer = new Customer(
                UUID.randomUUID(),
                "John",
                "Doe",
                new CreditLimit(BigDecimal.ZERO, BigDecimal.valueOf(90000))
        );

        Loan loan = customer.makeLoanWithInstallments(
                LoanAmount.of(
                        BigDecimal.valueOf(10000),
                        new InterestRate(0.5)
                ),
                new NumberOfInstallments(9)
        );

        Installment installment = loan.getInstallments().get(0);
        installment.setDueDate(LocalDate.now());
        BigDecimal customerInitialUsedCreditLimit = installment.customer().usedCreditLimit();

        installment.pay();

        assertTrue(installment.isPaid());
        assertTrue(installment.getPaidAmount().compareTo(installment.getAmount()) == 0);
        assertEquals(
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                installment.getPaymentDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );

        assertTrue(
                installment.customer().usedCreditLimit()
                        .compareTo(customerInitialUsedCreditLimit.subtract(installment.getAmount())) == 0
        );
    }
}
