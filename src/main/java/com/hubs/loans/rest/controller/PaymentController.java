package com.hubs.loans.rest.controller;

import com.hubs.loans.application.service.PaymentService;
import com.hubs.loans.domain.value.loan.PayLoanResult;
import com.hubs.loans.rest.dto.request.PayLoanRequest;
import com.hubs.loans.rest.dto.response.PayLoanResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping(value = "/{loanId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PayLoanResponse> payLoan(@PathVariable UUID loanId, @Valid @RequestBody PayLoanRequest payLoanRequest) {
        PayLoanResult payLoanResult = paymentService.payLoan(payLoanRequest.toCommandWith(loanId));

        return ResponseEntity.ok(PayLoanResponse.from(payLoanResult));
    }
}
