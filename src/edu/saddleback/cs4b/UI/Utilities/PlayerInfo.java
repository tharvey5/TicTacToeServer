package edu.saddleback.cs4b.UI.Utilities;

import edu.saddleback.cs4b.Backend.Utilitys.User;

public class PlayerInfo
{
//    private String id;
//    private String username;
//    private String firstName;
//    private String lastName;
    private String status;
    private User user;

    public PlayerInfo() {
        status = " ";
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getId()
    {
        return user.getId();
    }
    public String getUsername()
    {
        return user.getUsername();
    }
    public String getFirstName()
    {
        return user.getFirstName();
    }
    public String getLastName()
    {
        return user.getLastName();
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }
}
