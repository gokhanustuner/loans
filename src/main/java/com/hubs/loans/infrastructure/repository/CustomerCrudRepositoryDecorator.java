package com.hubs.loans.infrastructure.repository;

import com.hubs.loans.domain.entity.Customer;
import com.hubs.loans.domain.exception.CustomerNotFoundException;
import com.hubs.loans.domain.repository.CustomerRepository;
import com.hubs.loans.domain.value.customer.CustomerId;
import com.hubs.loans.infrastructure.repository.jpa.CustomerCrudRepository;
import jakarta.persistence.LockTimeoutException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomerCrudRepositoryDecorator implements CustomerRepository {

    private final CustomerCrudRepository customerCrudRepository;

    @Override
    public Customer findById(CustomerId customerId) {
        try {
            return customerCrudRepository.findByIdWithLock(customerId.id())
                    .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        } catch (LockTimeoutException e) {
            throw new RuntimeException("System error");
        }
    }
}
