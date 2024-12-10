package com.hubs.loans.domain.repository;

import com.hubs.loans.domain.entity.Customer;
import com.hubs.loans.domain.value.customer.CustomerId;

public interface CustomerRepository {
    Customer findById(CustomerId customerId);
}
