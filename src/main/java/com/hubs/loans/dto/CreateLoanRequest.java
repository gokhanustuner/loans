package com.hubs.loans.dto;

import com.hubs.loans.application.command.CreateLoanCommand;
import com.hubs.loans.domain.value.customer.CustomerId;
import com.hubs.loans.domain.value.loan.InterestRate;
import com.hubs.loans.domain.value.loan.LoanAmount;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.UUID;

public record CreateLoanRequest(
        @NotNull UUID customerId,
        @NotNull @DecimalMin("0.01") BigDecimal amount,
        @NotNull @DecimalMin("0.1") @DecimalMax("0.5") double interestRate,
        @NotNull
        //@Pattern(regexp = "6|9|12|24", message = "Installments can only be 6, 9, 12, or 24")
        int numberOfInstallments
) {
        public CreateLoanCommand toCommand() {
                return new CreateLoanCommand(
                        new CustomerId(customerId),
                        amount,
                        new InterestRate(interestRate),
                        numberOfInstallments
                );
        }
}
