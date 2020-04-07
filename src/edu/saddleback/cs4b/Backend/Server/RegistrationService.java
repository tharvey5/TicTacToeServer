package edu.saddleback.cs4b.Backend.Server;

public class RegistrationService {
    private static volatile RegistrationService rs = null;

    private RegistrationService() {}

    public static RegistrationService getInstance() {
        if (rs == null) {
            synchronized (RegistrationService.class) {
                if (rs == null) {
                    rs = new RegistrationService();
                }
            }
        }
        return rs;
    }
}
