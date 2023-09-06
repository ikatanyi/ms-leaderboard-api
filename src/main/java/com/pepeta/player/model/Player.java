package com.castille.customer.model;

import com.castille.customer.dto.CustomerDto;
import com.castille.customer.model.enumeration.Gender;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;


@Data
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotEmpty(message = "First name is required")
    private String firstName;
    @NotEmpty(message = "Last Name is required")
    private String lastName;
    @NotEmpty(message = "Email is required")
    private String email;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String phoneNumber;

    public CustomerDto toCustomerDto(){
        CustomerDto customerDto = new CustomerDto();
        customerDto.setFirstName(this.getFirstName());
        customerDto.setLastName(this.getLastName());
        customerDto.setEmail(this.getEmail());
        customerDto.setGender(this.getGender());
        customerDto.setPhoneNumber(this.getPhoneNumber());
        customerDto.setId(this.getId());
        return customerDto;
    }
}
