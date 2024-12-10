package com.hubs.loans.domain.entity;

import com.hubs.loans.domain.value.installment.InstallmentId;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@IdClass(InstallmentId.class)
public class Installment {

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

    public static Installment.InstallmentBuilder builderWithIdAndDueDate(int number) {
        LocalDate today = LocalDate.now();
        LocalDate dueDate = today.withDayOfMonth(1).plusMonths(number);

        return builder().id(UUID.randomUUID()).dueDate(dueDate);
    }
}
