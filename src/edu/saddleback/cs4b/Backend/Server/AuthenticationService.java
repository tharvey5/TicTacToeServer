package edu.saddleback.cs4b.Backend.Server;

import edu.saddleback.cs4b.Backend.Database.DBManager;
import edu.saddleback.cs4b.Backend.Database.SQLDatabase;
import edu.saddleback.cs4b.Backend.Utilitys.Authenticatable;

public class AuthenticationService implements Authenticator {
    private volatile static AuthenticationService authSvc = null;
    private DBManager database;

    private AuthenticationService() {
        this.database = SQLDatabase.getInstance();
    }

    public static AuthenticationService getInstance() {
        if (authSvc == null) {
            synchronized (AuthenticationService.class) {
                if (authSvc == null ) {
                    authSvc = new AuthenticationService();
                }
            }
        }
        return authSvc;
    }


    @Override
    public synchronized Authenticatable authenticate(Authenticatable auth) {
        Authenticatable authObj = null;
        try {
            authObj = database.Login(auth.getIdentifier(), auth.getKey());
        } catch (Exception e) {
            e.printStackTrace();
            return authObj;
        }
        // the authentication is successful if this isn't null
        return authObj;
    }
}
