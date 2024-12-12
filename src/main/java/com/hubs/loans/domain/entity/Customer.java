package com.hubs.loans.domain.entity;

import com.hubs.loans.domain.exception.InsufficientCreditLimitException;
import com.hubs.loans.domain.value.customer.CreditLimit;
import com.hubs.loans.domain.value.customer.CustomerId;
import com.hubs.loans.domain.value.installment.NumberOfInstallments;
import com.hubs.loans.domain.value.loan.LoanAmount;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
@IdClass(CustomerId.class)
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    private UUID id = UUID.randomUUID();

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Embedded
    private CreditLimit creditLimit = new CreditLimit(BigDecimal.ZERO, BigDecimal.ZERO);

    public Loan makeLoanWithInstallments(LoanAmount loanAmount, NumberOfInstallments numberOfInstallments) {
        if (creditLimit.isInsufficient(loanAmount))
            throw new InsufficientCreditLimitException("Insufficient credit limit");

        increaseUsedCreditLimit(loanAmount.amount());

        return Loan.builderWithIdAndInstallments(loanAmount, numberOfInstallments)
                .customer(this)
                .isPaid(false)
                .build();
    }

    public void increaseUsedCreditLimit(BigDecimal amount) {
        setCreditLimit(new CreditLimit(creditLimit.increaseUsedCreditLimit(amount), creditLimit.creditLimit()));
    }

    public void decreaseUsedCreditLimit(BigDecimal amount) {
        setCreditLimit(new CreditLimit(creditLimit.decreaseUsedCreditLimit(amount), creditLimit.creditLimit()));
    }

    public BigDecimal usedCreditLimit() {
        return creditLimit.usedCreditLimit();
    }
}
