package edu.saddleback.cs4b.Backend.Server;

import edu.saddleback.cs4b.Backend.Messages.BaseMessage;

public class UserRemovedGameMessage extends BaseMessage {
    private String username;

    public UserRemovedGameMessage() {
        this (null);
    }

    public UserRemovedGameMessage(String username) {
        super(null);
        setUsername(username);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
