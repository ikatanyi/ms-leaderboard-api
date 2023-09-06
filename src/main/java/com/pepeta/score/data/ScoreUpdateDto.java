package com.pepeta.score.data;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.pepeta.score.model.Score;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ScoreUpdateDto {
    private Integer score;
}
