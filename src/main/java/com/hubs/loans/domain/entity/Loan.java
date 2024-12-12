package com.hubs.loans.domain.entity;

import com.hubs.loans.domain.value.installment.NumberOfInstallments;
import com.hubs.loans.domain.value.loan.LoanAmount;
import com.hubs.loans.domain.value.loan.LoanId;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@IdClass(LoanId.class)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Loan {
    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(nullable = false, precision = 15, scale = 2)
    private LoanAmount loanAmount;

    @Column(nullable = false)
    private NumberOfInstallments numberOfInstallments;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createDate;

    @Column(nullable = false)
    private Boolean isPaid;

    @OneToMany(mappedBy = "loan", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("dueDate ASC")
    private List<Installment> installments;

    public Installment makeInstallment(int installmentNumber) {
        return Installment.builderWithIdAndDueDate(installmentNumber)
                .loan(this)
                .amount(calculateInstallmentAmount())
                .paidAmount(BigDecimal.ZERO)
                .isPaid(false)
                .build();
    }

    public List<Installment> unpaidInstallments() {
        return installments.stream().filter(Installment::notIsPaid).toList();
    }

    public BigDecimal calculateInstallmentAmount() {
        return loanAmount.amount().divide(
                BigDecimal.valueOf(numberOfInstallments.value()),
                RoundingMode.HALF_UP
        );
    }

    public void complete() {
        setIsPaid(true);
    }

    public void decreaseCustomersUsedCreditLimit(BigDecimal amount) {
        customer.decreaseUsedCreditLimit(amount);
    }

    public static LoanBuilder builderWithId() {
        return builder().id(UUID.randomUUID());
    }

    public static LoanBuilderWithInstallments builderWithIdAndInstallments(
            LoanAmount loanAmount,
            NumberOfInstallments numberOfInstallments
    ) {
        LoanBuilder loanBuilder =
                builderWithId().numberOfInstallments(numberOfInstallments)
                        .loanAmount(loanAmount);

        return new LoanBuilderWithInstallments(loanBuilder);
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static class LoanBuilderWithInstallments {

        private final LoanBuilder loanBuilder;

        public LoanBuilderWithInstallments customer(Customer customer) {
            loanBuilder.customer(customer);

            return this;
        }

        public LoanBuilderWithInstallments isPaid(boolean isPaid) {
            loanBuilder.isPaid(isPaid);

            return this;
        }

        public Loan build() {
            Loan loan = loanBuilder.build();
            List<Installment> installments = new ArrayList<>();

            for (int i = 1; i <= loan.getNumberOfInstallments().value(); i++)
                installments.add(loan.makeInstallment(i));

            loan.setInstallments(installments);

            return loan;
        }
    }
}
