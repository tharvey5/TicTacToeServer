package edu.saddleback.cs4b.Backend.Utilitys;

public class User {
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
}
