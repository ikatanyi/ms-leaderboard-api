package com.pepeta.score.service;

import com.pepeta.exception.APIException;

import com.pepeta.player.model.Player;
import com.pepeta.player.service.PlayerService;
import com.pepeta.score.data.ScoreDto;
import com.pepeta.score.data.ScoreUpdateDto;
import com.pepeta.score.model.Score;
import com.pepeta.score.model.specification.ScoreSpecification;
import com.pepeta.score.repository.ScoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Kennedy Ikatanyi
 */
@Slf4j
@Service
@EnableCaching
@RequiredArgsConstructor
//@CacheConfig(cacheNames = {"Score"})
public class ScoreService {

    private final ScoreRepository scoreRepository;
    private final PlayerService playerService;

    //
    @Transactional
    public Score createScore(ScoreDto scoreDto) {
        Player player = playerService.fetchPlayerByIdOrThrow(scoreDto.getPlayerId());
        Score score = scoreDto.toScore();
        score.setPlayer(player);
        return scoreRepository.save(score);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Score updateScore(Long id, ScoreDto scoreDto) {
        Score score = fetchScore(id);
        score.setScore(scoreDto.getScore());
        return scoreRepository.save(score);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Boolean deleteScore(Long id) {
        Score score = fetchScore(id);
        scoreRepository.delete(score);
        return true;
    }

    @Cacheable("scoreCache")
    public Page<Score> fetchScores(Long playerId, Long score, Pageable pageable) {
        Specification<Score> spec = ScoreSpecification.createSpecification(playerId, score);
        Page<Score> scores = scoreRepository.findAll(spec, pageable);
        return scores;
    }

    @Cacheable("scoreCache")
    public Page<Score> fetchTopPlayers(int players) {
        Page<Score> products = scoreRepository.findAll(PageRequest.of(0, players, Sort.by(Sort.Direction.DESC, "score")));
        return products;
    }

    public Score updatePlayerScore(Long id, ScoreUpdateDto scoreDto) {
        Score score = scoreRepository.findByPlayerId(id)
                .orElseThrow(() -> APIException.notFound("Score identified by id {0} not found.", id));
        score.setScore(scoreDto.getScore());
        return scoreRepository.save(score);
    }

    @Cacheable("scoreCache")
    public Score fetchScore(Long id) {
        return this.scoreRepository.findById(id)
                .orElseThrow(() -> APIException.notFound("Score identified by id {0} not found.", id));
    }

    @Cacheable("scoreCache")
    @Transactional(readOnly = true)
    public List<Score> findAll() {
        return scoreRepository.findAll();
    }

    public List<ScoreDto> standardRanking(List<Score> scores) {
        System.out.println("\nStandard ranking");
        List<ScoreDto> sortedList = scores.stream()
                .sorted(Comparator.comparing(Score::getScore).reversed())
                .map(Score::toScoreDto)
                .collect(Collectors.toList());

        int rank = 0;
        for (int i = 0; i < sortedList.size(); i++) {
            if (i == 0)
                rank = 1;
            else
                rank = sortedList.get(i).getScore() == sortedList.get(i-1).getScore() ? sortedList.get(i - 1).getRank() : i + 1;
            sortedList.get(i).setRank(rank);
        }
        return sortedList;
    }

    public ScoreDto standardRanking(Long id) {
        System.out.println("\nStandard ranking");
        List<Score> scores = findAll();
        List<ScoreDto> sortedList = scores.stream()
                .sorted(Comparator.comparing(Score::getScore).reversed())
                .map(Score::toScoreDto)
                .collect(Collectors.toList());
        int rank = 1;
        for (int i = 0; i < sortedList.size(); i++) {
            if (i == 0)
                rank = 1;
            else
                rank = sortedList.get(i).getScore() == sortedList.get(i - 1).getScore() ? sortedList.get(i - 1).getRank() : i + 1;
            sortedList.get(i).setRank(rank);
        }
        return sortedList.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
    }

    @Transactional(readOnly = true)
    @Cacheable
    public Score findByPlayerId(Long id) {
        return scoreRepository.findByPlayerId(id)
                .orElseThrow(() -> APIException.notFound("Player identified by id {0} not found.", id));
    }
}
