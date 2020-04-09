package edu.saddleback.cs4b.Backend.Server;

import edu.saddleback.cs4b.Backend.Utilitys.Profile;

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

    /**
     * @return return false if the account details cannot be set, such as
     * a repeated username
     */
    public boolean setAccountDetails(Profile profile) {
        return true;
    }

    /**
     * @return true if successful & false if it failed
     */
    public boolean deactivateAccount(Profile profile) {
        // call on the DB to deactivate the account
        return true;
    }
}
