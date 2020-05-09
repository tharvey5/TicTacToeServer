package edu.saddleback.cs4b.Backend.Server;

import edu.saddleback.cs4b.Backend.Messages.BaseMessage;
import edu.saddleback.cs4b.Backend.Utilitys.User;

import java.util.ArrayList;
import java.util.List;

public class ReturnAllRegisteredUsersMessage extends BaseMessage {
    private List<User> allUsers;

    public ReturnAllRegisteredUsersMessage() {
        this (new ArrayList<>());
    }

    public ReturnAllRegisteredUsersMessage(List<User> user) {
        super (null);
        setAllUsers(user);
    }

    public List<User> getAllUsers() {
        return allUsers;
    }

    public void setAllUsers(List<User> allUsers) {
        this.allUsers = allUsers;
    }
}
