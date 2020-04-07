package edu.saddleback.cs4b.Backend.Server;

public class AuthenticationService {
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
}
