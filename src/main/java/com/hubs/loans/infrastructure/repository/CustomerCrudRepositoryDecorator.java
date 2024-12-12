package com.hubs.loans.infrastructure.repository;

import com.hubs.loans.domain.entity.Customer;
import com.hubs.loans.domain.exception.CustomerNotFoundException;
import com.hubs.loans.domain.repository.CustomerRepository;
import com.hubs.loans.domain.value.customer.CustomerId;
import com.hubs.loans.infrastructure.repository.jpa.CustomerCrudRepository;
import jakarta.persistence.LockTimeoutException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomerCrudRepositoryDecorator implements CustomerRepository {

    private final CustomerCrudRepository customerCrudRepository;

    @Override
    public Customer findByIdWithLock(CustomerId customerId) {
        try {
            return customerCrudRepository.findByIdWithLock(customerId.id())
                    .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        } catch (LockTimeoutException e) {
            throw new RuntimeException("System error");
        }
    }

    @Override
    public Optional<Customer> findById(CustomerId customerId) {
        return customerCrudRepository.findById(customerId);
    }
}
