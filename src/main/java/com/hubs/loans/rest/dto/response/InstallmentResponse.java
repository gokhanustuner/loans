package com.hubs.loans.rest.dto.response;

import com.hubs.loans.domain.entity.Installment;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record InstallmentResponse(
        UUID id,
        BigDecimal amount,
        BigDecimal paidAmount,
        LocalDate dueDate,
        boolean isPaid,
        LocalDateTime paymentDate
) {
    public static InstallmentResponse from(Installment installment) {
        return new InstallmentResponse(
                installment.getId(),
                installment.getAmount(),
                installment.getPaidAmount(),
                installment.getDueDate(),
                installment.isPaid(),
                installment.getPaymentDate()
        );
    }
}
