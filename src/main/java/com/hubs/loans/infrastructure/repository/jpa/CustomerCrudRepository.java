package com.hubs.loans.infrastructure.repository.jpa;

import com.hubs.loans.domain.entity.Customer;
import com.hubs.loans.domain.value.customer.CustomerId;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface CustomerCrudRepository extends CrudRepository<Customer, CustomerId> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM Customer c WHERE c.id = :id")
    @QueryHints({@QueryHint(name = "jakarta.persistence.lock.timeout", value = "30000")})
    Optional<Customer> findByIdWithLock(@Param("id") UUID customerId);
}
