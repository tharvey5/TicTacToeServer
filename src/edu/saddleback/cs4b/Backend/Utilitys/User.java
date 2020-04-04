package edu.saddleback.cs4b.Backend.Utilitys;

import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String fn;
    private String ln;
    private String password;
    private int id;

    private User(String username, String fn, String ln, String password, int id) {
        setUsername(username);
        setFirstName(fn);
        setLastName(ln);
        setPassword(password);
        setId(id);
    }

    public String getUsername() { return username; }
    public String getFirstName() { return fn; }
    public String getLn() { return ln; }
    // todo how should we handle this?? this could be an issue if its public
    private String getPassword() { return password; }

    // todo this probably would only be used by server??? ** should it be public **
    private int getId() { return id; }

    private void setUsername(String username) { this.username = username; }
    private void setFirstName(String fn) { this.fn = fn; }
    private void setLastName(String ln) { this.ln = ln; }
    private void setPassword(String password) { this.password = password; }
    private void setId(int id) { this.id = id; }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (!(obj instanceof User)) {
            return false;
        } else {
            return username.equals((((User) obj).username));
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        return sb.toString();
    }
}
