package com.hubs.loans.domain.entity;

import com.hubs.loans.domain.value.customer.CreditLimit;
import com.hubs.loans.domain.value.customer.CustomerId;
import com.hubs.loans.domain.value.loan.LoanAmount;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
@IdClass(CustomerId.class)
public class Customer {

    @Id
    private UUID id = UUID.randomUUID();

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Embedded
    private CreditLimit creditLimit = new CreditLimit(BigDecimal.ZERO, BigDecimal.ZERO);

    public Loan makeLoan(LoanAmount loanAmount, int numberOfInstallments) {
        return Loan.builderWithId()
                .customer(this)
                .loanAmount(loanAmount)
                .numberOfInstallments(numberOfInstallments)
                .isPaid(false)
                .build();
    }

    public boolean hasInsufficientCreditLimit(LoanAmount loanAmount) {
        return creditLimit.isInsufficient(loanAmount);
    }

    public void increaseUsedCreditLimit(BigDecimal amount) {
        setCreditLimit(new CreditLimit(creditLimit.usedCreditLimit().add(amount), creditLimit.creditLimit()));
    }
}
