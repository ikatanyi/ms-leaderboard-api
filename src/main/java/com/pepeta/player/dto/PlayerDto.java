package com.castille.customer.dto;

import com.castille.customer.model.Customer;
import com.castille.customer.model.enumeration.Gender;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.annotations.Formula;

import javax.persistence.*;


@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CustomerDto {
    @Schema(hidden = true)
    private Long id;
    private String firstName;
    private String lastName;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String email;
    private String phoneNumber;
    @Schema(hidden = true)
    @Formula("concat(first_name, ' ', last_name)")
    private String fullName;

    public Customer toCustomer(){
        Customer customer = new Customer();
        customer.setFirstName(this.getFirstName());
        customer.setLastName(this.getLastName());
        customer.setEmail(this.getEmail());
        customer.setGender(this.getGender());
        customer.setPhoneNumber(this.getPhoneNumber());
        return customer;
    }
}
