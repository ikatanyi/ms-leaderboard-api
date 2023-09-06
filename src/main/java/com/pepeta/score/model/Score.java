package com.pepeta.score.model;

import com.pepeta.player.model.Player;
import com.pepeta.score.data.ScoreDto;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "score")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class Score implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name="player_id")
    private Player player;
    private Integer score;

    public ScoreDto toScoreDto(){
        ScoreDto scoreDto = new ScoreDto();
        scoreDto.setId(this.getId());
        scoreDto.setScore(this.getScore());
        scoreDto.setPlayerId(this.getPlayer().getId());
        scoreDto.setPlayerName(this.getPlayer().getFirstName()+" "+this.getPlayer().getLastName());
        return scoreDto;
    }
}
