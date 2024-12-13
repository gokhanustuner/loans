package com.hubs.loans.domain.value.customer;

import com.hubs.loans.domain.exception.InvalidCreditLimitException;
import com.hubs.loans.domain.value.loan.LoanAmount;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class CreditLimitTest {

    @Test
    void throws_invalid_credit_limit_exception_when_used_credit_limit_is_less_than_zero() {
        assertThrows(
                InvalidCreditLimitException.class,
                () -> new CreditLimit(BigDecimal.valueOf(-1), BigDecimal.valueOf(0))
        );
    }

    @Test
    void throws_invalid_credit_limit_exception_when_credit_limit_is_less_than_zero() {
        assertThrows(
                InvalidCreditLimitException.class,
                () -> new CreditLimit(BigDecimal.valueOf(0), BigDecimal.valueOf(-1))
        );
    }

    @Test
    void throws_invalid_credit_limit_exception_when_used_credit_limit_and_credit_limit_is_less_than_zero() {
        assertThrows(
                InvalidCreditLimitException.class,
                () -> new CreditLimit(BigDecimal.valueOf(-1), BigDecimal.valueOf(-1))
        );
    }

    @Test
    void instantiates_when_arguments_are_equal_to_zero() {
            CreditLimit creditLimit = new CreditLimit(BigDecimal.valueOf(0), BigDecimal.valueOf(0));
            assertInstanceOf(CreditLimit.class, creditLimit);
    }

    @Test
    void instantiates_when_arguments_are_greater_than_zero() {
            CreditLimit creditLimit = new CreditLimit(BigDecimal.valueOf(1), BigDecimal.valueOf(1));
            assertInstanceOf(CreditLimit.class, creditLimit);
    }

    @Test
    void is_insufficient_returns_false_when_credit_limit_is_sufficient() {
        CreditLimit creditLimit = new CreditLimit(BigDecimal.valueOf(0), BigDecimal.valueOf(10000));
        assertFalse(creditLimit.isInsufficient(new LoanAmount(BigDecimal.valueOf(1000), BigDecimal.valueOf(1500))));
    }

    @Test
    void is_insufficient_returns_true_when_credit_limit_is_insufficient() {
        CreditLimit creditLimit = new CreditLimit(BigDecimal.valueOf(0), BigDecimal.valueOf(10000));
        assertFalse(creditLimit.isInsufficient(new LoanAmount(BigDecimal.valueOf(2500), BigDecimal.valueOf(3000))));
    }
}
