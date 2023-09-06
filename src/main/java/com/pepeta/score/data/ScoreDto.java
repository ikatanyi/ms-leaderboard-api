package com.pepeta.score.data;

import com.pepeta.score.model.Score;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ScoreDto {
    @Schema(hidden = true)
    private Long id;
    private Integer score;
    @NotEmpty(message = "Player Id is required")
    private Long playerId;
    @Schema(hidden = true)
    private String playerName;
    private Integer rank;

    public Score toScore(){
        Score score = new Score();
        score.setScore(this.getScore());
        return score;
    }
}
