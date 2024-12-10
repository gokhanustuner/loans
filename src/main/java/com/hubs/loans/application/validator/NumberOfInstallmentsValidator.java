package com.hubs.loans.application.validator;

import com.hubs.loans.domain.value.installment.NumberOfInstallments;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NumberOfInstallmentsValidator implements ConstraintValidator<ValidNumberOfInstallments, Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return NumberOfInstallments.ACCEPTED_INSTALLMENT_NUMBERS.contains(value);
    }
}
