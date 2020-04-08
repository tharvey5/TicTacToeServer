package edu.saddleback.cs4b.Backend.Server;

public class AuthenticationService implements Authenticator {
    private volatile static AuthenticationService authSvc = null;

    private AuthenticationService() {}

    private static AuthenticationService getInstance() {
        if (authSvc == null) {
            synchronized (AuthenticationService.class) {
                if (authSvc == null ) {
                    authSvc = new AuthenticationService();
                }
            }
        }
        return authSvc;
    }

    //TODO will we need to synchronize this when we have it running
    // with the data base?
    @Override
    public boolean authenticate(Authenticatable auth) {
        // call on the DB to verify the credentials, return the status of
        // the authentication
        return false;
    }
}
