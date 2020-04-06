package edu.saddleback.cs4b.Backend.Utilitys;

import java.io.Serializable;

public class Profile implements Serializable {
    private User user;
    // private GameRecord gameRecord;
    // private List<Games> activeGames;

    /**
     * This ctor will be used to create a user profile, but can also
     * be used to update a user profile
     * @throws NullPointerException
     */
    public Profile(User user) throws NullPointerException {
        setUser(user);
    }

    /**
     * This method can be used to create or update a user profile
     */
    public Profile(String userName, String firstName, String lastName, String password) {
        setUser(new User(userName, firstName, lastName, password));
    }

    /**
     * @throws NullPointerException if a null user is added
     */
    private void setUser(User user) throws NullPointerException {
        if (user == null) {
            throw new NullPointerException();
        }
        this.user = user;
    }

    public User getUser() { return (User)user.clone(); }

    @Override
    protected Object clone()  {
        try {
            Profile copy = (Profile) super.clone();
            copy.user = (User)user.clone();
            return copy;
        } catch (CloneNotSupportedException ce) {
            return null;
        }
    }

    @Override
    public String toString() {
        //todo add details about other fields later as we add more data
        StringBuilder sb = new StringBuilder(user.toString());
        return sb.toString();
    }
}
