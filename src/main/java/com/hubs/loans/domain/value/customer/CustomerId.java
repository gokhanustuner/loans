package com.hubs.loans.domain.value.customer;

import java.io.Serializable;
import java.util.UUID;


public record CustomerId(UUID id) implements Serializable {
}
