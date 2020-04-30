package com.marcosvidolin.jokenpo.domain;

import java.util.UUID;

public class Move {

    private UUID gameCode;
    private String playerUsername;
    private String itemValue;

    public Move(UUID gameCode, String playerUsername, String itemValue) {
        this.gameCode = gameCode;
        this.playerUsername = playerUsername;
        this.itemValue = itemValue;
    }

    public UUID getGameCode() {
        return gameCode;
    }

    public void setGameCode(UUID gameCode) {
        this.gameCode = gameCode;
    }

    public String getPlayerUsername() {
        return playerUsername;
    }

    public void setPlayerUsername(String playerUsername) {
        this.playerUsername = playerUsername;
    }

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }
}
