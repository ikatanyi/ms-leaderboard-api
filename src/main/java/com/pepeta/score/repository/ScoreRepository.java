package com.pepeta.score.repository;

import com.pepeta.player.model.Player;
import com.pepeta.score.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long>, JpaSpecificationExecutor<Score> {
    Optional<Score> findByPlayerId(Long id);
}

