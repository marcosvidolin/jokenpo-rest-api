package com.marcosvidolin.jokenpo.rest.api.controller;

import com.marcosvidolin.jokenpo.domain.Game;
import com.marcosvidolin.jokenpo.domain.Move;
import com.marcosvidolin.jokenpo.domain.Player;
import com.marcosvidolin.jokenpo.domain.exception.BusinessException;
import com.marcosvidolin.jokenpo.domain.exception.ModelAlreadyExistsException;
import com.marcosvidolin.jokenpo.domain.exception.ModelNotFoundException;
import com.marcosvidolin.jokenpo.rest.api.request.CreateGameRequest;
import com.marcosvidolin.jokenpo.rest.api.request.MoveRequest;
import com.marcosvidolin.jokenpo.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/games", produces = MediaType.APPLICATION_JSON_VALUE)
public class GameRestController {

    private GameService service;

    @Autowired
    public GameRestController(GameService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Game> create(@RequestBody CreateGameRequest createGameRequest)
            throws BusinessException {
        List<String> players = createGameRequest.getPlayers();
        Game game = this.service.generateNewGame(players);
        return ResponseEntity.status(HttpStatus.CREATED).body(game);
    }

    @PostMapping("/{gameCode}/moves")
    public ResponseEntity<?> move(@PathVariable UUID gameCode, @RequestBody MoveRequest moveRequest)
            throws ModelNotFoundException, BusinessException {
        Move move = new Move(gameCode, moveRequest.getPlayer(), moveRequest.getMove());
        Move moved = this.service.makeMove(move);
        return ResponseEntity.status(HttpStatus.CREATED).body(moved);
    }

    @PutMapping("/{gameCode}/play")
    public ResponseEntity<Player> play(@PathVariable UUID gameCode)
            throws ModelNotFoundException, BusinessException {
        Player winner = this.service.getWinner(gameCode);
        return ResponseEntity.status(HttpStatus.CREATED).body(winner);
    }

    @PostMapping("/{gameCode}/players")
    public ResponseEntity<Player> move(@PathVariable UUID gameCode, @RequestBody Player player)
            throws ModelNotFoundException, BusinessException, ModelAlreadyExistsException {
        this.service.addPlayerToGame(gameCode, player);
        return ResponseEntity.status(HttpStatus.CREATED).body(player);
    }

    @DeleteMapping("/{gameCode}/players/{username}")
    public ResponseEntity<Player> move(@PathVariable UUID gameCode, @PathVariable String username)
            throws ModelNotFoundException {
        this.service.removePlayerFromGame(gameCode, username);
        return ResponseEntity.noContent().build();
    }

}
