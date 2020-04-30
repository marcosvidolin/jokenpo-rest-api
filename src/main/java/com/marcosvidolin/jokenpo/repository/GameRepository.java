package com.marcosvidolin.jokenpo.repository;

import com.marcosvidolin.jokenpo.domain.Game;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Fake {@link Game} repository.
 */
@Repository
public class GameRepository {

    private Map<UUID, Game> datastore = new HashMap<>();

    public Game findByCode(UUID code) {
        return this.datastore.get(code);
    }

    public Game create(Game game) {
        this.datastore.put(game.getCode(), game);
        return game;
    }

    public List<Game> temp() {
        return new ArrayList<>(this.datastore.values());
    }
}
