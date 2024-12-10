package com.hubs.loans.rest.request;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class PaymentRequest {
    @NotNull
    @DecimalMin("0.01")
    private BigDecimal amount;
}
