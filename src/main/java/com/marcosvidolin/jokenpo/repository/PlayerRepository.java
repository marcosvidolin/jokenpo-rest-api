package com.marcosvidolin.jokenpo.repository;

import com.marcosvidolin.jokenpo.domain.Player;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@link Player} repository layer.
 */
@Repository
public class PlayerRepository {

    private Map<String, Player> repository = new HashMap<>();

    /**
     * Finds all {@link Player}.
     *
     * @return a list of {@link Player}
     */
    public List<Player> findAll() {
        return new ArrayList<>(this.repository.values());
    }

    /**
     * Finds a {@link Player} by username.
     *
     * @param username {@link Player} username
     * @return Player found
     */
    public Player findPlayerByUsername(String username) {
        return this.repository.get(username);
    }

    /**
     * Creates a {@link Player}.
     *
     * @param player {@link Player}
     * @return returned the created {@link Player}
     */
    public Player create(Player player) {
        this.repository.put(player.getUsername(), player);
        return player;
    }

    /**
     * Removes a {@link Player}.
     *
     * @param player the {@link Player} to be removed
     */
    public void remove(Player player) {
        this.repository.remove(player.getUsername());
    }

}
