package com.hubs.loans.infrastructure.converter;

import com.hubs.loans.domain.value.loan.InterestRate;
import com.hubs.loans.domain.value.loan.LoanAmount;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.math.BigDecimal;

@Converter(autoApply = true)
public class LoanAmountConverter implements AttributeConverter<LoanAmount, BigDecimal> {
    @Override
    public BigDecimal convertToDatabaseColumn(LoanAmount loanAmount) {
        System.out.println("LoanAmountConverter::convertToDatabaseColumn");
        return loanAmount.amount();
    }

    @Override
    public LoanAmount convertToEntityAttribute(BigDecimal loanAmount) {
        System.out.println("LoanAmountConverter::convertToEntityAttribute");
        return new LoanAmount(BigDecimal.ZERO, loanAmount);
    }
}
