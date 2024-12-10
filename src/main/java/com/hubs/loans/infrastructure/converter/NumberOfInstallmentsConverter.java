package com.hubs.loans.infrastructure.converter;

import com.hubs.loans.domain.value.installment.NumberOfInstallments;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class NumberOfInstallmentsConverter implements AttributeConverter<NumberOfInstallments, Integer> {
    @Override
    public Integer convertToDatabaseColumn(NumberOfInstallments numberOfInstallments) {
        return numberOfInstallments.value();
    }

    @Override
    public NumberOfInstallments convertToEntityAttribute(Integer integer) {
        return new NumberOfInstallments(integer);
    }
}
