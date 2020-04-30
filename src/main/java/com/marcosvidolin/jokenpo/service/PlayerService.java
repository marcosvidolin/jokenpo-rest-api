package com.marcosvidolin.jokenpo.service;

import com.marcosvidolin.jokenpo.domain.Player;
import com.marcosvidolin.jokenpo.domain.exception.BusinessException;
import com.marcosvidolin.jokenpo.domain.exception.ModelAlreadyExistsException;
import com.marcosvidolin.jokenpo.domain.exception.ModelNotFoundException;
import com.marcosvidolin.jokenpo.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link Player} service layer.
 */
@Service
public class PlayerService {

    private PlayerRepository repository;

    @Autowired
    public void PlayerRepository(PlayerRepository repository) {
        this.repository = repository;
    }

    /**
     * Finds all {@link Player}.
     *
     * @return a list of {@link Player}
     */
    public List<Player> findAll() {
        return this.repository.findAll();
    }

    /**
     * Finds a {@link Player} by username.
     *
     * @param username {@link Player} username
     * @return Player found
     */
    public Player findByUsername(String username) throws ModelNotFoundException {
        Player player = this.repository.findPlayerByUsername(username);
        if (player == null) {
            throw new ModelNotFoundException("Player not found with username: " + username);
        }
        return player;
    }

    /**
     * Finds all {@link Player}.
     *
     * @return a list of {@link Player}
     */
    public List<Player> findAllByUsername(List<String> usernames) throws ModelNotFoundException {
        List<Player> players = new ArrayList<>();
        for (String username : usernames) {
            Player player = this.findByUsername(username);
            players.add(player);
        }
        return players;
    }

    /**
     * Check if an username already exists.
     *
     * @param player username to search
     * @return boolean true if exists
     */
    public boolean exists(String player) {
        try {
            this.findByUsername(player);
        } catch (ModelNotFoundException e) {
            return false;
        }
        return true;
    }

    /**
     * Creates a {@link Player}.
     *
     * @param player {@link Player}
     * @return returned the created {@link Player}
     * @throws BusinessException
     * @throws ModelAlreadyExistsException
     */
    public Player create(Player player) throws BusinessException, ModelAlreadyExistsException {
        this.checkPlayer(player);
        if (this.exists(player.getUsername())) {
            throw new ModelAlreadyExistsException("Username '" + player.getUsername() + "' already exists.");
        }
        return this.repository.create(player);
    }

    /**
     * Removes a {@link Player}.
     *
     * @param username the username of the {@link Player}
     * @throws ModelNotFoundException
     */
    public void remove(String username) throws ModelNotFoundException {
        Player player = this.findByUsername(username);
        this.repository.remove(player);
    }

    /**
     * Check if is a valid {@link Player}.
     *
     * @param player the {@link Player} to be checked
     * @throws BusinessException
     */
    public void checkPlayer(Player player) throws BusinessException {
        if (!player.hasUsername()) {
            throw new BusinessException("Invalid player. No username was informed.");
        }

        boolean isLetter = player.getUsername().chars().allMatch(Character::isLetter);
        if (!isLetter) {
            throw new BusinessException("Invalid username. Only letters are accepted.");
        }
    }
}
