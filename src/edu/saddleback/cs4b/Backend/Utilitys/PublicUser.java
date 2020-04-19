package edu.saddleback.cs4b.Backend.Utilitys;

/**
 * This user will only contain information about a user
 * that is safe for public visibility
 *
 * NOTE: Our user structure could of been designed in a better way
 * possibly using an abstract class for the specific use cases
 */
public class PublicUser implements User {
    private String firstName;  // included bc our contract expects it
    private String lastName;   // included bc our contract expects it
    private String username;
    private String id;

    @Override
    public String getUsername() { return username; }

    @Override
    public String getFirstName() { return firstName; }

    @Override
    public String getLastName() { return lastName; }

    @Override
    public String getPassword() { return null; }

    @Override
    public void setUsername(String username) { this.username = username; }

    @Override
    public void setFirstName(String firstName) { this.firstName = firstName; }

    @Override
    public void setLastName(String lastName) { this.lastName = lastName; }

    @Override
    public void setPassword(String password) { }

    @Override
    public String getKey() {
        return username;
    }

    @Override
    public String getIdentifier() {
        return id;
    }
}
