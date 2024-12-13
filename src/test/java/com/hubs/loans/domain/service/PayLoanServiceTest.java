package com.hubs.loans.domain.service;

import com.hubs.loans.domain.entity.Customer;
import com.hubs.loans.domain.entity.Loan;
import com.hubs.loans.domain.value.customer.CreditLimit;
import com.hubs.loans.domain.value.installment.NumberOfInstallments;
import com.hubs.loans.domain.value.loan.InterestRate;
import com.hubs.loans.domain.value.loan.LoanAmount;
import com.hubs.loans.domain.value.loan.PayLoanResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class PayLoanServiceTest {

    private PayLoanService payLoanService;

    @BeforeEach
    public void setUp() {
        payLoanService = new PayLoanService();
    }

    @Test
    void pay_pays_unpaid_installments_within_the_amount() {
        Customer customer = new Customer(
                UUID.randomUUID(),
                "John",
                "Doe",
                new CreditLimit(BigDecimal.ZERO, BigDecimal.valueOf(10000))
        );
        Loan loan = customer.makeLoanWithInstallments(
                LoanAmount.of(LoanAmount.MIN, new InterestRate(0.2)),
                new NumberOfInstallments(6)
        );

        PayLoanResult payLoanResult = payLoanService.pay(loan, BigDecimal.valueOf(400));

        assertTrue(loan.getInstallments().get(0).getPaidAmount().compareTo(BigDecimal.ZERO) > 0);
        assertTrue(loan.getInstallments().get(1).getPaidAmount().compareTo(BigDecimal.ZERO) > 0);
        assertEquals(0, loan.getInstallments().get(2).getPaidAmount().compareTo(BigDecimal.ZERO));
        assertTrue(loan.getInstallments().get(0).isPaid());
        assertTrue(loan.getInstallments().get(1).isPaid());
        assertFalse(loan.getInstallments().get(2).isPaid());
        assertEquals(2, payLoanResult.numberOfInstallmentsPaid());
        assertFalse(payLoanResult.isPaid());
        assertTrue(payLoanResult.paidAmount().compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    void pay_completes_loan_when_no_unpaid_installments_anymore() {
        Customer customer = new Customer(
                UUID.randomUUID(),
                "John",
                "Doe",
                new CreditLimit(BigDecimal.ZERO, BigDecimal.valueOf(10000))
        );
        Loan loan = customer.makeLoanWithInstallments(
                LoanAmount.of(LoanAmount.MIN, new InterestRate(0.2)),
                new NumberOfInstallments(6)
        );

        loan.getInstallments().get(0).payOrdinary();
        loan.getInstallments().get(1).payOrdinary();
        loan.getInstallments().get(2).payOrdinary();
        loan.getInstallments().get(3).payOrdinary();
        loan.getInstallments().get(4).payOrdinary();
        loan.getInstallments().get(5).setDueDate(LocalDate.now());

        PayLoanResult payLoanResult = payLoanService.pay(loan, BigDecimal.valueOf(400));

        assertTrue(loan.getInstallments().get(5).getPaidAmount().compareTo(BigDecimal.ZERO) > 0);
        assertTrue(loan.getInstallments().get(5).isPaid());
        assertEquals(1, payLoanResult.numberOfInstallmentsPaid());
        assertTrue(payLoanResult.isPaid());
        assertTrue(payLoanResult.paidAmount().compareTo(BigDecimal.ZERO) > 0);
    }
}
