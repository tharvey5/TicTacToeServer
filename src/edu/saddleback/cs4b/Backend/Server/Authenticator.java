package edu.saddleback.cs4b.Backend.Server;

public interface Authenticator {
    boolean authenticate(Authenticatable auth);
}
