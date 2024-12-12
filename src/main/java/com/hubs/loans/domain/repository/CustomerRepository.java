package com.hubs.loans.domain.repository;

import com.hubs.loans.domain.entity.Customer;
import com.hubs.loans.domain.value.customer.CustomerId;

import java.util.Optional;

public interface CustomerRepository {
    Customer findByIdWithLock(CustomerId customerId);
    Optional<Customer> findById(CustomerId customerId);
}
