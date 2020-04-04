package edu.saddleback.cs4b.Backend.Utilitys;

import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String fn;
    private String ln;
    private String password;
    private int id;

    private User(String username, String fn, String ln, String password, int id) {
        this.username = username;
        this.fn = fn;
        this.ln = ln;
        this.password = password;
        this.id = id;
    }
    
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
