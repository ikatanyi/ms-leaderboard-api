package com.pepeta.player.dto;

import com.pepeta.player.model.Player;
import com.pepeta.player.model.enumeration.Gender;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.annotations.Formula;

import javax.persistence.*;


@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PlayerDto {
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
    private Integer score;

    public Player toPlayer(){
        Player player = new Player();
        player.setFirstName(this.getFirstName());
        player.setLastName(this.getLastName());
        player.setEmail(this.getEmail());
        player.setGender(this.getGender());
        player.setPhoneNumber(this.getPhoneNumber());
        return player;
    }
}
