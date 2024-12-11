package com.hubs.loans.domain.factory;

import com.hubs.loans.domain.entity.Customer;
import com.hubs.loans.domain.entity.Loan;
import com.hubs.loans.domain.entity.Installment;
import com.hubs.loans.domain.value.installment.NumberOfInstallments;
import com.hubs.loans.domain.value.loan.LoanAmount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanFactory {

    private final InstallmentsFactory installmentsFactory;

    public Loan createLoanWithCustomer(Customer customer, LoanAmount loanAmount, NumberOfInstallments numInstallments) {
        Loan loan = customer.makeLoan(loanAmount, numInstallments);

        List<Installment> installments = installmentsFactory.createInstallments(loan);
        loan.setInstallments(installments);

        return loan;
    }
}
