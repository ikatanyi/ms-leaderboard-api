package com.castille.customer.repository;

import com.castille.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer>findByEmail(String email);
}
