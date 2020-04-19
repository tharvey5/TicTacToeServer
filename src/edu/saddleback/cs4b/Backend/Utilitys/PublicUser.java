package edu.saddleback.cs4b.Backend.Utilitys;

/**
 * This user will only contain information about a user
 * that is safe for public visibility
 */
public class PublicUser implements User {
    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public String getFirstName() {
        return null;
    }

    @Override
    public String getLastName() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public void setUsername(String username) {

    }

    @Override
    public void setFirstName(String firstName) {

    }

    @Override
    public void setLastName(String lastName) {

    }

    @Override
    public void setPassword(String password) {

    }

    @Override
    public String getKey() {
        return null;
    }

    @Override
    public String getIdentifier() {
        return null;
    }
}
