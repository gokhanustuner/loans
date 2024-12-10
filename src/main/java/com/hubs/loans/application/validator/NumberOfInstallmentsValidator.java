package com.hubs.loans.application.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class NumberOfInstallmentsValidator implements ConstraintValidator<NumberOfInstallments, Integer> {

    private final static List<Integer> ACCEPTED_INSTALLMENT_NUMBERS = List.of(6, 9, 12, 24);

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return ACCEPTED_INSTALLMENT_NUMBERS.contains(value);
    }
}
