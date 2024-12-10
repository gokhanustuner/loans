package com.hubs.loans.domain.factory;

import com.hubs.loans.domain.entity.Loan;
import com.hubs.loans.domain.entity.Installment;
import com.hubs.loans.domain.value.installment.NumberOfInstallments;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InstallmentsFactory {

    public List<Installment> createInstallments(Loan loan, NumberOfInstallments numberOfInstallments) {
        List<Installment> installments = new ArrayList<>();

        for (int i = 1; i <= numberOfInstallments.value(); i++) {
            Installment installment = loan.makeInstallment(i);

            installments.add(installment);
        }

        return installments;
    }
}
