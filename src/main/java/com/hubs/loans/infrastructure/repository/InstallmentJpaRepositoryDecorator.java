package com.hubs.loans.infrastructure.repository;

import com.hubs.loans.domain.repository.InstallmentRepository;
import com.hubs.loans.infrastructure.repository.jpa.InstallmentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class InstallmentJpaRepositoryDecorator implements InstallmentRepository {

    private final InstallmentJpaRepository installmentJpaRepository;
}
