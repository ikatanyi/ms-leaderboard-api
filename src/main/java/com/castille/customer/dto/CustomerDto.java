package com.castille.customer.dto;

import com.castille.customer.model.Customer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Data
public class CustomerDto {
    @Schema(hidden = true)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    public Customer toCustomer(){
        Customer customer = new Customer();
        customer.setFirstName(this.getFirstName());
        customer.setLastName(this.getLastName());
        customer.setEmail(this.getEmail());
        customer.setPhoneNumber(this.getPhoneNumber());
        return customer;
    }
}
