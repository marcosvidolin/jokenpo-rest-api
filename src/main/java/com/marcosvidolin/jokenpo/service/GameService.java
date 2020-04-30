package com.marcosvidolin.jokenpo.service;

import com.marcosvidolin.jokenpo.domain.Game;
import com.marcosvidolin.jokenpo.domain.GeekEngine;
import com.marcosvidolin.jokenpo.domain.Move;
import com.marcosvidolin.jokenpo.domain.Player;
import com.marcosvidolin.jokenpo.domain.exception.BusinessException;
import com.marcosvidolin.jokenpo.domain.exception.ModelNotFoundException;
import com.marcosvidolin.jokenpo.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GameService {

    private GameRepository repository;
    private PlayerService playerService;

    @Autowired
    public GameService(GameRepository repository, PlayerService playerService) {
        this.repository = repository;
        this.playerService = playerService;
    }

    /**
     * Finds a {@link Game} by code.
     *
     * @param code the code of the {@link Game}
     * @return Game
     * @throws ModelNotFoundException
     */
    public Game findByCode(UUID code) throws ModelNotFoundException {
        Game game = this.repository.findByCode(code);
        if (game == null) {
            throw new ModelNotFoundException("No game found with code: " + code);
        }
        return game;
    }

    /**
     * Generates a new {@link Game}.
     *
     * @param usernames a list of {@link Player}'s username
     * @return a new {@link Game}
     */
    public Game generateNewGame(List<String> usernames) throws BusinessException {
        Game game = new Game(new GeekEngine());
        List<Player> players;
        try {
            players = this.playerService.findAllByUsername(usernames);
        } catch (ModelNotFoundException e) {
            throw new BusinessException("A new game could not be created. Invalid player.");
        }
        game.addPlayers(players);
        return this.repository.create(game);
    }

    /**
     * Adds a {@link Player} to an existent {@link Game}.
     *
     * @param code the code of the {@link Game}
     * @param player the {@link Player} to be added
     * @return Game
     * @throws ModelNotFoundException
     */
    public Game addPlayerToGame(UUID code, Player player) throws ModelNotFoundException, BusinessException {
        Game game = this.findByCode(code);
        this.playerService.checkPlayer(player);
        game.addPlayer(player);
        return game;
    }

    /**
     * Removes a {@link Player} to an existent {@link Game}.
     *
     * @param code the code of the {@link Game}
     * @param username the {@link Player}'s username to be removed
     * @return Game
     * @throws ModelNotFoundException
     */
    public Game removePlayerFromGame(UUID code, String username) throws ModelNotFoundException {
        Game game = this.findByCode(code);
        game.removePlayer(username);
        return game;
    }

    /**
     * Makes a move.
     *
     * @param move add a {@link Move} to the game.
     * @return Move
     * @throws ModelNotFoundException
     * @throws BusinessException
     */
    public Move makeMove(Move move) throws ModelNotFoundException, BusinessException {
        Game game = this.findByCode(move.getGameCode());
        if (game.hasFinishedAllMoves()) {
            throw new BusinessException("This game is over. Start a new game.");
        }
        game.addMove(move);
        return move;
    }

    /**
     * Gets the game winner.
     *
     * @param gameCode the {@link Game}'s ID.
     * @return Player
     * @throws BusinessException
     * @throws ModelNotFoundException
     */
    public Player getWinner(UUID gameCode) throws BusinessException, ModelNotFoundException {
        Game game = this.findByCode(gameCode);
        if (!game.hasFinishedAllMoves()) {
            throw new BusinessException("This game is not over yet.");
        }
        return game.getWinner();
    }
}
