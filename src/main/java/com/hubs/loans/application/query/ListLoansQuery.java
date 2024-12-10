package com.hubs.loans.application.query;

import com.hubs.loans.domain.value.customer.CustomerId;

import java.util.UUID;

public record ListLoansQuery(CustomerId customerId, int page) {
    public static ListLoansQuery of(UUID customerId, int page) {
        return new ListLoansQuery(new CustomerId(customerId), page - 1);
    }
}
