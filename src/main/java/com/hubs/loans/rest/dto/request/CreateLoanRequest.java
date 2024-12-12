package com.hubs.loans.rest.dto.request;

import com.hubs.loans.application.command.CreateLoanCommand;
import com.hubs.loans.application.validator.ValidNumberOfInstallments;
import com.hubs.loans.domain.value.customer.CustomerId;
import com.hubs.loans.domain.value.loan.InterestRate;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.UUID;

public record CreateLoanRequest(
        @NotNull @DecimalMin("1000.00") BigDecimal amount,
        @NotNull @DecimalMin("0.1") @DecimalMax("0.5") double interestRate,
        @NotNull @ValidNumberOfInstallments int numberOfInstallments
) {
        public CreateLoanCommand toCommandWithCustomerId(UUID customerId) {
                return new CreateLoanCommand(
                        new CustomerId(customerId),
                        amount,
                        new InterestRate(interestRate),
                        new com.hubs.loans.domain.value.installment.NumberOfInstallments(numberOfInstallments)
                );
        }
}
