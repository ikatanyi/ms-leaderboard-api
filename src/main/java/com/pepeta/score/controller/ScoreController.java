package com.pepeta.score.controller;

import com.pepeta.score.data.ScoreDto;
import com.pepeta.score.data.ScoreUpdateDto;
import com.pepeta.score.model.Score;
import com.pepeta.score.service.ScoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 *
 * @author Kennedy Ikatanyi
 */
@RestController
@Slf4j
@RequestMapping("/score")
@RequiredArgsConstructor
public class ScoreController {

    private final ScoreService service;

    @GetMapping("/{id}")
    public ResponseEntity<?> fetchScoreById(@PathVariable(value = "id") Long id) {
        ScoreDto dto = service.fetchScore(id).toScoreDto();
        return ResponseEntity.ok(dto);
    }

    @PutMapping("player/{id}/score")
    public ResponseEntity<?> updateScore(@PathVariable(value = "id") Long id, @Valid @RequestBody ScoreUpdateDto scoreDto) {
        Score score = service.updatePlayerScore(id, scoreDto);
        return ResponseEntity.ok(service.standardRanking(score.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteScore(@PathVariable(value = "id") Long id) {
        service.deleteScore(id);
        return ResponseEntity.ok("Score deleted successfully");

    }

    @GetMapping
    public ResponseEntity<?> getAllScores(
            @RequestParam(value = "playerId", required = false) final Long playerId,
            @RequestParam(value = "score", required = false) final Long score,
            @RequestParam(value = "page", defaultValue = "0",required = false) Integer page,
            @RequestParam(value = "pageSize", defaultValue = "20",required = false) Integer size) {
        page = page>=1 ? page-1 : page;
        Pageable pageable = PageRequest.of(page, size);

        Page<Score> pagedList = service.fetchScores(playerId, score, pageable);
        return ResponseEntity.ok(service.standardRanking(pagedList.getContent()));
    }

    @GetMapping(" /leaderboard/top/{players}")
    public ResponseEntity<?> getTopPlayers(
            @RequestParam(value = "players", required = false) final Integer players) {
        Page<Score> pagedList = service.fetchTopPlayers(players);
        return ResponseEntity.ok(service.standardRanking(pagedList.getContent()));
    }

    @GetMapping("/players/{id}/rank")
    public ResponseEntity<?> fetchRankByPlayerId(@PathVariable(value = "id") Long id) {
        Score score = service.findByPlayerId(id);
        return ResponseEntity.ok(service.standardRanking(score.getId()));
    }

}
