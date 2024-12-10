package com.hubs.loans.application.service;

import com.hubs.loans.application.query.ListInstallmentsQuery;
import com.hubs.loans.domain.entity.Installment;
import com.hubs.loans.domain.repository.InstallmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InstallmentService {

    private final InstallmentRepository installmentRepository;

    public List<Installment> listInstallments(ListInstallmentsQuery listInstallmentsQuery) {
        return installmentRepository.findByLoanId(listInstallmentsQuery.loanId());
    }
}
