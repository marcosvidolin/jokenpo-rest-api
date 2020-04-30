package com.marcosvidolin.jokenpo.domain;

import com.marcosvidolin.jokenpo.domain.exception.BusinessException;

import java.util.*;

public class Game {

    private UUID code;
    private Map<String, Player> players = new HashMap<>();
    private Player winner;
    private List<Move> moves = new ArrayList<>();
    private GameEngine gameEngine;

    public Game(GameEngine gameEngine) {
        this.code = UUID.randomUUID();
        this.gameEngine = gameEngine;
    }

    /**
     * Adds a {@link Player}.
     *
     * @param player {@link Player}
     */
    public void addPlayer(Player player) {
        this.players.put(player.getUsername(), player);
    }

    /**
     * Adds all {@link Player}.
     *
     * @param players {@link Player}
     */
    public void addPlayers(List<Player> players) {
        for (Player player : players) {
            this.addPlayer(player);
        }
    }

    /**
     * Removes a {@link Player}.
     *
     * @param username {@link Player}'s username
     */
    public void removePlayer(String username) {
        this.players.remove(username);
    }

    /**
     * Get the winner of the {@link Game}.
     *
     * @return Player the winner
     */
    public Player getWinner() throws BusinessException {
        if (this.hasFinishedAllMoves()) {
            this.gameEngine.addMoves(this.moves);
            Move move = this.gameEngine.getWinnerMove();
            if (move == null) {
                throw new BusinessException("This game have no winner. Try a new game.");
            }
            return this.players.get(move.getPlayerUsername());
        }
        return null;
    }

    /**
     * Checks if all players already informed a move.
     *
     * @return boolean true if every players informed a move
     */
    public boolean hasFinishedAllMoves() {
        return this.players.values().size() == this.moves.size();
    }

    /**
     * Adds a {@link Move} into the {@link Game}.
     *
     * @param move
     */
    public void addMove(Move move) throws BusinessException {
        String username = move.getPlayerUsername();
        Player player = this.players.get(username);
        if (player == null) {
            throw new BusinessException("Player with username '" + username + "' was not found.");
        }
        this.moves.add(move);
    }


    public UUID getCode() {
        return code;
    }

    public List<Player> getPlayers() {
        return new ArrayList<>(this.players.values());
    }

}
