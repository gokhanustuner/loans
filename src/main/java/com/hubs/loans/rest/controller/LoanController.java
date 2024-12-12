package com.hubs.loans.rest.controller;

import com.hubs.loans.application.query.ListInstallmentsQuery;
import com.hubs.loans.application.query.ListLoansQuery;
import com.hubs.loans.application.service.InstallmentService;
import com.hubs.loans.application.service.LoanService;
import com.hubs.loans.domain.entity.Installment;
import com.hubs.loans.domain.entity.Loan;
import com.hubs.loans.rest.dto.request.CreateLoanRequest;
import com.hubs.loans.rest.dto.response.InstallmentResponse;
import com.hubs.loans.rest.dto.response.LoanResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/customers/{customerId}/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    private final InstallmentService installmentService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoanResponse> createLoan(
            @PathVariable UUID customerId,
            @Valid @RequestBody CreateLoanRequest createLoanRequest
    ) {
        Loan loan = loanService.createLoan(createLoanRequest.toCommandWithCustomerId(customerId));
        return ResponseEntity.ok(LoanResponse.from(loan));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<LoanResponse>> listLoans(
            @PathVariable UUID customerId,
            @RequestParam @Min(1) int page
    ) {
        List<Loan> loans = loanService.listLoans(ListLoansQuery.of(customerId, page));
        List<LoanResponse> response = loans.stream()
                .map(LoanResponse::from)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{loanId}/installments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<InstallmentResponse>> listInstallments(@PathVariable UUID customerId, @PathVariable UUID loanId) {
        List<Installment> installments = installmentService.listInstallments(ListInstallmentsQuery.of(customerId, loanId));
        List<InstallmentResponse> response = installments.stream()
                .map(InstallmentResponse::from)
                .toList();

        return ResponseEntity.ok(response);
    }
}
