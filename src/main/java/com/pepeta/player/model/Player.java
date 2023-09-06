package com.pepeta.player.model;

import com.pepeta.player.dto.PlayerDto;
import com.pepeta.player.model.enumeration.Gender;
import com.pepeta.score.model.Score;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;


@Data
@Entity
@Table(name = "player")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToOne(mappedBy = "player", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Score score;

    public PlayerDto toPlayerDto(){
        PlayerDto playerDto = new PlayerDto();
        playerDto.setFirstName(this.getFirstName());
        playerDto.setLastName(this.getLastName());
        playerDto.setEmail(this.getEmail());
        playerDto.setGender(this.getGender());
        playerDto.setPhoneNumber(this.getPhoneNumber());
        playerDto.setId(this.getId());
        playerDto.setFullName(this.getFirstName()+" "+this.getLastName());
        if(this.getScore()!=null)
            playerDto.setScore(this.getScore().getScore());
        return playerDto;
    }

    public void addScore(Score score){
      score.setPlayer(this);
      this.setScore(score);
    }
}
