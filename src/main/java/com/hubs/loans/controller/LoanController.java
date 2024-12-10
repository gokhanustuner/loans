package com.hubs.loans.controller;

import com.hubs.loans.application.query.InstallmentsQuery;
import com.hubs.loans.application.query.ListLoansQuery;
import com.hubs.loans.application.service.LoanService;
import com.hubs.loans.domain.entity.Loan;
import com.hubs.loans.dto.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<LoanResponse> createLoan(@Valid @RequestBody CreateLoanRequest createLoanRequest) {
        Loan loan = loanService.createLoan(createLoanRequest.toCommand());
        return ResponseEntity.ok(LoanResponse.from(loan));
    }

    @GetMapping(value = "/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<LoanResponse>> listLoans(
            @PathVariable UUID customerId,
            @RequestParam @Min(1) @NotNull int page
    ) {
        List<Loan> loans = loanService.listLoans(ListLoansQuery.of(customerId, page));
        List<LoanResponse> response = loans.stream()
                .map(LoanResponse::from)
                .toList();
        return ResponseEntity.ok(response);
    }

    /*
    @GetMapping(value = "/{loanId}/installments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<InstallmentResponse>> listLoanInstallments(
            @PathVariable UUID loanId,
            @RequestParam @Min(1) @NotNull int page
    ) {
        List<Loan> loans = loanService.listLoans(InstallmentsQuery.of(loanId, page));
        List<LoanResponse> response = loans.stream()
                .map(LoanResponse::from)
                .toList();
        return ResponseEntity.ok(response);
    }

     */
}
