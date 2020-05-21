package edu.saddleback.cs4b.Backend.Server;

import edu.saddleback.cs4b.Backend.Messages.BaseMessage;

public class UserAddedGameMessage extends BaseMessage {
    private String username;
    private String gameId;

    public UserAddedGameMessage() {
        this (null, null);
    }

    public UserAddedGameMessage(String username, String gameId) {
        super(null);
        setUsername(username);
        setGameId(gameId);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
}
