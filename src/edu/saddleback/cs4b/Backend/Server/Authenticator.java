package edu.saddleback.cs4b.Backend.Server;

import edu.saddleback.cs4b.Backend.Utilitys.Authenticatable;

public interface Authenticator {
    boolean authenticate(Authenticatable auth);
}
