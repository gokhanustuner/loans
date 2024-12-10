package com.hubs.loans.domain.value.loan;

import com.hubs.loans.domain.exception.InvalidInterestRateException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InterestRateTest {

    @Test
    public void throws_invalid_interest_rate_exception_when_interest_rate_less_than_min() {
        double min = InterestRate.MIN - 0.01;

        assertThrows(InvalidInterestRateException.class, () -> new InterestRate(min));
    }

    @Test
    public void throws_invalid_interest_rate_exception_when_interest_rate_greater_than_max() {
        double max = InterestRate.MAX + 0.01;

        assertThrows(InvalidInterestRateException.class, () -> new InterestRate(max));
    }

    @Test
    public void instantiates_when_interest_rate_between_min_and_max() {
        double rate = InterestRate.MAX - InterestRate.MIN;
        InterestRate interestRate = new InterestRate(rate);

        assertInstanceOf(InterestRate.class, interestRate);
    }
}
