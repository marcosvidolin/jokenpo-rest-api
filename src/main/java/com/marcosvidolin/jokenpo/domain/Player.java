package com.marcosvidolin.jokenpo.domain;

public class Player {

    private String name;
    private String username;

    public Player() {
    }

    public Player(String username) {
        this.username = username;
    }

    /**
     * Checks if the {@link Player} has username.
     * @return boolean true if has username
     */
    public boolean hasUsername() {
        return this.username != null && !this.username.isEmpty();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

}
