package com.hubs.loans.domain.factory;

import com.hubs.loans.domain.entity.Loan;
import com.hubs.loans.domain.entity.Installment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InstallmentsFactory {

    public List<Installment> createInstallments(Loan loan, int numberOfInstallments) {
        List<Installment> installments = new ArrayList<>();

        for (int i = 1; i <= numberOfInstallments; i++) {
            Installment installment = loan.makeInstallment(i);

            installments.add(installment);
        }

        return installments;
    }
}
