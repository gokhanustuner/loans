package com.hubs.loans.application.service;

import com.hubs.loans.application.query.ListInstallmentsQuery;
import com.hubs.loans.domain.entity.Installment;
import com.hubs.loans.domain.exception.CustomerNotFoundException;
import com.hubs.loans.domain.exception.LoanNotFoundException;
import com.hubs.loans.domain.repository.CustomerRepository;
import com.hubs.loans.domain.repository.InstallmentRepository;
import com.hubs.loans.domain.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InstallmentService {

    private final CustomerRepository customerRepository;

    private final LoanRepository loanRepository;

    private final InstallmentRepository installmentRepository;

    public List<Installment> listInstallments(ListInstallmentsQuery listInstallmentsQuery) {
        List<Installment> installments = installmentRepository.findByCustomerIdAndLoanId(
                listInstallmentsQuery.customerId(),
                listInstallmentsQuery.loanId()
        );

        if (installments.isEmpty()) {
            customerRepository.findById(listInstallmentsQuery.customerId())
                    .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

            loanRepository.findByCustomerIdAndLoanId(listInstallmentsQuery.customerId(), listInstallmentsQuery.loanId())
                    .orElseThrow(() -> new LoanNotFoundException("Loan not found"));
        }

        return installmentRepository.findByCustomerIdAndLoanId(
                listInstallmentsQuery.customerId(),
                listInstallmentsQuery.loanId()
        );
    }

}
