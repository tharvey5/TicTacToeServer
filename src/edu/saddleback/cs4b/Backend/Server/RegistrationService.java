package edu.saddleback.cs4b.Backend.Server;

import edu.saddleback.cs4b.Backend.Database.DBManager;
import edu.saddleback.cs4b.Backend.Database.SQLDatabase;
import edu.saddleback.cs4b.Backend.Utilitys.Profile;

public class RegistrationService {
    private static volatile RegistrationService rs = null;
    private DBManager database;

    private RegistrationService() {
        this.database = SQLDatabase.getInstance();
    }

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
        try {
            database.addUser(profile.getUser());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * @return true if successful & false if it failed
     */
    public boolean deactivateAccount(Profile profile) {
        try {
            //database.inactivateUser();
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
