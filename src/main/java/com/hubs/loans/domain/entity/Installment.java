package com.hubs.loans.domain.entity;

import com.hubs.loans.domain.value.installment.InstallmentId;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@IdClass(InstallmentId.class)
public class Installment {

    private final static double EARLY_PAYMENT_DISCOUNT_FACTOR = 0.001;

    private final static double LATE_PAYMENT_PENALTY_FACTOR = 0.001;

    @Id
    private UUID id;

    @ManyToOne(targetEntity = Loan.class)
    @JoinColumn(name = "loan_id", referencedColumnName = "id", nullable = false, updatable = false)
    private Loan loan;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal paidAmount;

    @Column(nullable = false)
    private LocalDate dueDate;

    @Column
    private LocalDateTime paymentDate;

    @Column(nullable = false)
    private boolean isPaid;

    private transient int number;

    public boolean notIsPaid() {
        return !isPaid;
    }

    public boolean isPayableWithAmount(BigDecimal payAmount) {
        if (isPaidEarly())
            return calculateDiscountedAmount().compareTo(payAmount) <= 0;
        else if (isPaidLate())
            return calculatePenalizedAmount().compareTo(payAmount) <= 0;
        else
            return amount.compareTo(payAmount) <= 0;
    }

    public boolean isPayableToday() {
        LocalDate now = LocalDate.now();
        return now.isAfter(dueDate) || dueDate.isEqual(now) || now.plusMonths(3).isAfter(dueDate);
    }

    public boolean isPaidLate() {
        LocalDate now = LocalDate.now();
        return now.isAfter(dueDate);
    }

    public boolean isPaidEarly() {
        LocalDate now = LocalDate.now();
        return now.isBefore(dueDate);
    }

    public long daysAfterDueDate() {
        LocalDate now = LocalDate.now();

        if (now.isBefore(dueDate)) {
            return ChronoUnit.DAYS.between(dueDate, now);
        }

        return 0;
    }

    public long daysBeforeDueDate() {
        LocalDate now = LocalDate.now();

        if (now.isBefore(dueDate)) {
            return ChronoUnit.DAYS.between(now, dueDate);
        }

        return 0;
    }

    public void pay() {
        if (isPaidEarly()) {
            payWithDiscount();
        } else if (isPaidLate()) {
            payWithPenalty();
        } else {
            setPaidAmount(amount);
            setPaymentDate(LocalDateTime.now());
            setPaid(true);
        }

        loan.decreaseCustomersUsedCreditLimit(amount);
    }

    public void payWithDiscount() {
        setPaidAmount(calculateDiscountedAmount());
        setPaymentDate(LocalDateTime.now());
        setPaid(true);
    }

    public BigDecimal calculateDiscountedAmount() {
        return amount.subtract(
                amount.multiply(
                        BigDecimal.valueOf(EARLY_PAYMENT_DISCOUNT_FACTOR)
                ).multiply(BigDecimal.valueOf(daysBeforeDueDate()))
        );
    }

    public BigDecimal calculatePenalizedAmount() {
        return amount.subtract(
                amount.add(
                        BigDecimal.valueOf(LATE_PAYMENT_PENALTY_FACTOR)
                ).multiply(BigDecimal.valueOf(daysAfterDueDate()))
        );
    }

    public void payWithPenalty() {
        setPaidAmount(calculatePenalizedAmount());
        setPaymentDate(LocalDateTime.now());
        setPaid(true);
    }

    public static Installment.InstallmentBuilder builderWithIdAndDueDate(int number) {
        LocalDate today = LocalDate.now();
        LocalDate dueDate = today.withDayOfMonth(1).plusMonths(number);

        return builder().id(UUID.randomUUID()).dueDate(dueDate);
    }
}
