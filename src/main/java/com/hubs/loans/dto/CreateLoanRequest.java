package com.hubs.loans.dto;

import com.hubs.loans.application.command.CreateLoanCommand;
import com.hubs.loans.application.validator.NumberOfInstallments;
import com.hubs.loans.domain.value.customer.CustomerId;
import com.hubs.loans.domain.value.loan.InterestRate;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.UUID;

public record CreateLoanRequest(
        @NotNull UUID customerId,
        @NotNull @DecimalMin("0.01") BigDecimal amount,
        @NotNull @DecimalMin("0.1") @DecimalMax("0.5") double interestRate,
        @NotNull @NumberOfInstallments int numberOfInstallments
) {
        public CreateLoanCommand toCommand() {
                return new CreateLoanCommand(
                        new CustomerId(customerId),
                        amount,
                        new InterestRate(interestRate),
                        new com.hubs.loans.domain.value.installment.NumberOfInstallments(numberOfInstallments)
                );
        }
}
