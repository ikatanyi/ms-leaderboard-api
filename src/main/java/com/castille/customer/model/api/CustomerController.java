package com.castille.customer.model.api;

import com.castille.customer.dto.CustomerDto;
import com.castille.customer.model.Customer;
import com.castille.customer.model.enumeration.Gender;
import com.castille.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Kennedy Ikatanyi
 */
@RestController
@Slf4j
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;


    @PostMapping
    public ResponseEntity<?> createCustomer(@Valid @RequestBody CustomerDto resourceDto) {
        CustomerDto dto = service.createCustomer(resourceDto).toCustomerDto();
        return ResponseEntity.ok(dto);

    }


    @GetMapping("/{id}")
    public ResponseEntity<?> fetchCustomerById(@PathVariable(value = "id") Long id) {
        CustomerDto dto = service.fetchCustomerByIdOrThrow(id).toCustomerDto();
        return ResponseEntity.ok(dto);
    }



    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable(value = "id") Long id, @Valid @RequestBody CustomerDto customerDto) {

        Customer resource = service.updateCustomer(id, customerDto);
        return ResponseEntity.ok(resource.toCustomerDto());

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable(value = "id") Long id) {
        service.deleteCustomer(id);
        return ResponseEntity.ok("Customer deleted successfully");

    }

    @GetMapping
    public ResponseEntity<?> getAllCustomers(
            @RequestParam(value = "gender", required = false) final Gender gender,
            @RequestParam(value = "email", required = false) final String email,
            @RequestParam(value = "name", required = false) final String name,
            @RequestParam(value = "phoneNumber", required = false) final String phoneNumber,
            @RequestParam(value = "page", defaultValue = "0",required = false) Integer page,
            @RequestParam(value = "pageSize", defaultValue = "20",required = false) Integer size) {
        page = page>=1 ? page-1 : page;
        Pageable pageable = PageRequest.of(page, size);

        Page<CustomerDto> pagedList = service.fetchCustomers(gender, email, name, phoneNumber, pageable).map(u -> u.toCustomerDto());
        return ResponseEntity.ok(pagedList);
    }

}
