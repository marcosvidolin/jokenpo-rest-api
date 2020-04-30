package com.marcosvidolin.jokenpo.rest.api.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateGameRequest {

    private List<String> players;

    public List<String> getPlayers() {
        return this.players;
    }

    public void setPlayers(List<String> players) {
        this.players = players;
    }
}
