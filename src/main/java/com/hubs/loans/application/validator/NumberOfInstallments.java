package com.hubs.loans.application.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = NumberOfInstallmentsValidator.class)
public @interface NumberOfInstallments {
    String message() default "Number of installments can only be 6, 9, 12, or 24";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
