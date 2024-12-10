package com.hubs.loans.rest.controller;

import com.hubs.loans.application.query.ListInstallmentsQuery;
import com.hubs.loans.application.service.InstallmentService;
import com.hubs.loans.domain.entity.Installment;
import com.hubs.loans.rest.response.InstallmentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/installments")
@RequiredArgsConstructor
public class InstallmentController {

    private final InstallmentService installmentService;

    @GetMapping(value = "/{loanId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<InstallmentResponse>> listInstallments(@PathVariable UUID loanId) {
        List<Installment> installments = installmentService.listInstallments(ListInstallmentsQuery.of(loanId));
        List<InstallmentResponse> response = installments.stream()
                .map(InstallmentResponse::from)
                .toList();

        return ResponseEntity.ok(response);
    }
}
