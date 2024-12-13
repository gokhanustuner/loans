package com.hubs.loans.domain.value.loan;

import com.hubs.loans.domain.exception.InvalidLoanAmountException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class LoanAmountTest {

    @Test
    void throws_invalid_loan_amount_exception_when_raw_amount_is_less_than_min() {
        BigDecimal rawAmount = LoanAmount.MIN.subtract(BigDecimal.ONE);

        assertThrows(
                InvalidLoanAmountException.class,
                () -> new LoanAmount(rawAmount, rawAmount.multiply(BigDecimal.valueOf(1.3)))
        );
    }

    @Test
    void throws_invalid_loan_amount_exception_when_raw_amount_is_equal_to_amount() {
        assertThrows(InvalidLoanAmountException.class, () -> new LoanAmount(LoanAmount.MIN, LoanAmount.MIN));
    }

    @Test
    void throws_invalid_loan_amount_exception_when_raw_amount_is_greater_than_amount() {
        assertThrows(
                InvalidLoanAmountException.class,
                () -> new LoanAmount(LoanAmount.MIN, LoanAmount.MIN.subtract(BigDecimal.ONE))
        );
    }

    @Test
    void instantiates_when_raw_amount_is_equal_to_min() {
        LoanAmount loanAmount = new LoanAmount(LoanAmount.MIN, LoanAmount.MIN.multiply(BigDecimal.valueOf(1.3)));

        assertInstanceOf(LoanAmount.class, loanAmount);
    }

    @Test
    void instantiates_when_raw_amount_is_greater_than_min() {
        BigDecimal rawAmount = LoanAmount.MIN.add(BigDecimal.ONE);
        LoanAmount loanAmount = new LoanAmount(rawAmount, rawAmount.multiply(BigDecimal.valueOf(1.3)));

        assertInstanceOf(LoanAmount.class, loanAmount);
    }

    @Test
    void amount_correctly_calculated_according_to_raw_amount_and_interest_rate() {
        LoanAmount loanAmount = LoanAmount.of(LoanAmount.MIN, new InterestRate(0.3));

        assertEquals(loanAmount.amount(), BigDecimal.valueOf(1300.00));
    }
}
