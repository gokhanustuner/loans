package com.hubs.loans.domain.value.installment;

import com.hubs.loans.domain.exception.InvalidNumberOfInstallmentsException;

import java.util.List;

public record NumberOfInstallments(int value) {

    public final static List<Integer> ACCEPTED_INSTALLMENT_NUMBERS = List.of(6, 9, 12, 24);

    public NumberOfInstallments {
        if (!ACCEPTED_INSTALLMENT_NUMBERS.contains(value))
            throw new InvalidNumberOfInstallmentsException("Number of installments must be in 6, 9, 12, 24");
    }
}
