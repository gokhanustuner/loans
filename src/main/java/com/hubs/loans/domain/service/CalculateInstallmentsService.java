package com.hubs.loans.domain.service;

import com.hubs.loans.domain.value.loan.LoanAmount;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Service
public class CalculateInstallmentsService {

    public LocalDate calculateDueDateOfInstallment(int installmentNumber) {
        LocalDate today = LocalDate.now();
        return today.withDayOfMonth(1).plusMonths(installmentNumber);
    }

    public BigDecimal calculateInstallmentAmount(LoanAmount loanAmount, int numberOfInstallments) {
        return loanAmount.amount().divide(
                BigDecimal.valueOf(numberOfInstallments),
                RoundingMode.HALF_UP
        );
    }
}
