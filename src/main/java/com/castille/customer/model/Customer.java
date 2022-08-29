package com.castille.customer.model;

import com.castille.customer.dto.CustomerDto;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;


@Data
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    private Long id;
    @NotEmpty(message = "First name is required")
    private String firstName;
    @NotEmpty(message = "Last Name is required")
    private String lastName;
    @NotEmpty(message = "Email is required")
    private String email;
    private String phoneNumber;

    public CustomerDto toCustomerDto(){
        CustomerDto customerDto = new CustomerDto();
        customerDto.setFirstName(this.getFirstName());
        customerDto.setLastName(this.getLastName());
        customerDto.setEmail(this.getEmail());
        customerDto.setPhoneNumber(this.getPhoneNumber());
        return customerDto;
    }
}
