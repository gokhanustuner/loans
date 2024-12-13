package com.hubs.loans.domain.value.installment;

import com.hubs.loans.domain.exception.InvalidNumberOfInstallmentsException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class NumberOfInstallmentsTest {

    @Test
    void accepted_installment_numbers_are_correct() {
        assertEquals(NumberOfInstallments.ACCEPTED_INSTALLMENT_NUMBERS, List.of(6, 9, 12, 24));
    }

    @Test
    void throws_invalid_number_of_installments_exception_when_installment_numbers_are_incorrect() {
        assertThrows(InvalidNumberOfInstallmentsException.class, () -> new NumberOfInstallments(7));
    }
}
