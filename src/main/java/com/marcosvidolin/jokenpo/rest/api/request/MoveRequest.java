package com.marcosvidolin.jokenpo.rest.api.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MoveRequest {

    private String player;
    private String move;

    public String getPlayer() {
        return player;
    }
    public void setPlayer(String player) {
        this.player = player;
    }
    public String getMove() {
        return move;
    }
    public void setMove(String move) {
        this.move = move;
    }
}
