package com.hubs.loans.infrastructure.repository.jpa;

import com.hubs.loans.domain.entity.Customer;
import com.hubs.loans.domain.value.customer.CustomerId;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerCrudRepository extends CrudRepository<Customer, CustomerId> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM Customer c WHERE c.id = :id")
    Optional<Customer> findByIdWithLock(@Param("id") CustomerId customerId);
}
