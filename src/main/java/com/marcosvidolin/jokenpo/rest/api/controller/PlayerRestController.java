package com.marcosvidolin.jokenpo.rest.api.controller;

import com.marcosvidolin.jokenpo.domain.Player;
import com.marcosvidolin.jokenpo.domain.exception.BusinessException;
import com.marcosvidolin.jokenpo.domain.exception.ModelAlreadyExistsException;
import com.marcosvidolin.jokenpo.domain.exception.ModelNotFoundException;
import com.marcosvidolin.jokenpo.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/players", produces = MediaType.APPLICATION_JSON_VALUE)
public class PlayerRestController {

    private PlayerService service;

    @Autowired
    private PlayerRestController(PlayerService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Player>> list() {
        List<Player> players = this.service.findAll();
        if (players.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(players);
    }

    @PostMapping
    public ResponseEntity<Player> create(@RequestBody Player player)
            throws ModelAlreadyExistsException, BusinessException {
        Player created = this.service.create(player);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping(path = "/{username}")
    public ResponseEntity<?> delete(@PathVariable String username) throws ModelNotFoundException {
        this.service.remove(username);
        return ResponseEntity.noContent().build();
    }

}
