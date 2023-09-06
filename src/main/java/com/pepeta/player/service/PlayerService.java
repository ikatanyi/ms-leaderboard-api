package com.castille.customer.service;

import com.castille.customer.dto.CustomerDto;
import com.castille.customer.model.Customer;
import com.castille.customer.model.enumeration.Gender;
import com.castille.customer.model.specification.CustomerSpecification;
import com.castille.customer.repository.CustomerRepository;
import com.castille.exception.APIException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer fetchCustomerByIdOrThrow(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> APIException.notFound("Customer identified by id {0} not found", id));
    }

    public Optional<Customer> findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    public Customer createCustomer(CustomerDto customerDto) {
        Customer customer = customerDto.toCustomer();
        return customerRepository.save(customer);
    }

    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> APIException.notFound("Customer identified by id {0} not found", id));
        customerRepository.delete(customer);
    }

    public Customer updateCustomer(Long id, CustomerDto customerDto) {
        LocalDate now = LocalDate.now();
        Customer customer = customerRepository.findById(id).orElseThrow(() -> APIException.notFound("Customer identified by id {0} not found", id));
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setGender(customerDto.getGender());
        customer.setEmail(customerDto.getEmail());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        return customerRepository.save(customer);
    }

    public Page<Customer> fetchCustomers(Gender gender, String email, String name, String phoneNumber, Pageable pageable) {
        Specification<Customer> spec = CustomerSpecification.createSpecification(gender, email, name, phoneNumber);
        Page<Customer> items = customerRepository.findAll(spec, pageable);
        return items;
    }

}
