package com.pepeta.player.controller;

import com.pepeta.player.dto.PlayerDto;
import com.pepeta.player.model.Player;
import com.pepeta.player.model.enumeration.Gender;
import com.pepeta.player.service.PlayerService;
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
@RequestMapping("/player")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService service;


    @PostMapping
    public ResponseEntity<?> createPlayer(@Valid @RequestBody PlayerDto playerDto) {
        PlayerDto dto = service.createPlayer(playerDto).toPlayerDto();
        return ResponseEntity.ok(dto);

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> fetchPlayerById(@PathVariable(value = "id") Long id) {
        PlayerDto dto = service.fetchPlayerByIdOrThrow(id).toPlayerDto();
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePlayer(@PathVariable(value = "id") Long id, @Valid @RequestBody PlayerDto playerDto) {

        Player resource = service.updatePlayer(id, playerDto);
        return ResponseEntity.ok(resource.toPlayerDto());

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlayer(@PathVariable(value = "id") Long id) {
        service.deletePlayer(id);
        return ResponseEntity.ok("Player deleted successfully");

    }

    @GetMapping
    public ResponseEntity<?> getAllPlayers(
            @RequestParam(value = "gender", required = false) final Gender gender,
            @RequestParam(value = "email", required = false) final String email,
            @RequestParam(value = "name", required = false) final String name,
            @RequestParam(value = "phoneNumber", required = false) final String phoneNumber,
            @RequestParam(value = "page", defaultValue = "0",required = false) Integer page,
            @RequestParam(value = "pageSize", defaultValue = "20",required = false) Integer size) {
        page = page>=1 ? page-1 : page;
        Pageable pageable = PageRequest.of(page, size);

        Page<PlayerDto> pagedList = service.fetchPlayers(gender, email, name, phoneNumber, pageable).map(u -> u.toPlayerDto());
        return ResponseEntity.ok(pagedList);
    }

}
