package com.pepeta.player.service;

import com.pepeta.player.dto.PlayerDto;
import com.pepeta.player.model.Player;
import com.pepeta.player.model.enumeration.Gender;
import com.pepeta.player.model.specification.PlayerSpecification;
import com.pepeta.player.repository.PlayerRepository;
import com.pepeta.exception.APIException;
import com.pepeta.score.model.Score;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@EnableCaching
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;

    public List<Player> findAll() {
        return playerRepository.findAll();
    }

    public Player fetchPlayerByIdOrThrow(Long id) {
        return playerRepository.findById(id).orElseThrow(() -> APIException.notFound("Player identified by id {0} not found", id));
    }

    public Optional<Player> findByEmail(String email) {
        return playerRepository.findByEmail(email);
    }

    public Player createPlayer(PlayerDto playerDto) {
        Player player = playerDto.toPlayer();
        Score score = new Score();
        score.setScore(playerDto.getScore());
        player.addScore(score);
        return playerRepository.save(player);
    }

    public void deletePlayer(Long id) {
        Player player = playerRepository.findById(id).orElseThrow(() -> APIException.notFound("Player identified by id {0} not found", id));
        playerRepository.delete(player);
    }

    public Player updatePlayer(Long id, PlayerDto playerDto) {
        LocalDate now = LocalDate.now();
        Player player = playerRepository.findById(id).orElseThrow(() -> APIException.notFound("Player identified by id {0} not found", id));
        player.setFirstName(playerDto.getFirstName());
        player.setLastName(playerDto.getLastName());
        player.setGender(playerDto.getGender());
        player.setEmail(playerDto.getEmail());
        player.setPhoneNumber(playerDto.getPhoneNumber());
        return playerRepository.save(player);
    }

    @Cacheable("playerCache")
    public Page<Player> fetchPlayers(Gender gender, String email, String name, String phoneNumber, Pageable pageable) {
        Specification<Player> spec = PlayerSpecification.createSpecification(gender, email, name, phoneNumber);
        Page<Player> items = playerRepository.findAll(spec, pageable);
        return items;
    }

}
