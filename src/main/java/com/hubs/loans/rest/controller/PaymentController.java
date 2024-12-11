package com.hubs.loans.rest.controller;

import com.hubs.loans.application.service.PaymentService;
import com.hubs.loans.domain.entity.Loan;
import com.hubs.loans.rest.request.PayLoanRequest;
import com.hubs.loans.rest.response.PayLoanResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping(value = "/{loanId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PayLoanResponse> payLoan(@PathVariable UUID loanId, @Valid @RequestBody PayLoanRequest payLoanRequest) {
        Loan loan = paymentService.payLoan(payLoanRequest.toCommandWith(loanId));
        return ResponseEntity.ok(new PayLoanResponse(2, BigDecimal.ZERO, false));
    }
}
