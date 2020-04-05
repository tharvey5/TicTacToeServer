package edu.saddleback.cs4b.Backend.Messages;

import java.util.ArrayList;

public class ActiveUserMessage {
    ArrayList<String> activeUsers;

    /*
     * Request Message Constructor
     */
    public ActiveUserMessage()
    {
        activeUsers = null;
    }

    /*
     * Server Response Message Constructor
     */
    public ActiveUserMessage(ArrayList<String> newActiveUsers)
    {
        setActiveUsers(newActiveUsers);
    }

    public void setActiveUsers(ArrayList<String> newActiveUsers)
    {
        activeUsers = newActiveUsers;
    }

    public ArrayList<String> getActiveUsers()
    {
        return activeUsers;
    }

    public void addActiveUser(String newUser)
    {
        activeUsers.add(newUser);
    }

    public void removeActiveUser(String oldUser)
    {
        activeUsers.remove(oldUser);
    }
}
