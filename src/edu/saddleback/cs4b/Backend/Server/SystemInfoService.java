package edu.saddleback.cs4b.Backend.Server;

import java.util.List;

/**
 * Keeps track stats about the system such as who is logged in/active
 * number of games running etc
 */
public class SystemInfoService {
    private static volatile SystemInfoService si = null;
    private List<ClientCommunication> onlineUsers;

    private SystemInfoService() {}

    public static SystemInfoService getInstance() {
        if (si == null) {
            synchronized (SystemInfoService.class) {
                if (si == null) {
                    si = new SystemInfoService();
                }
            }
        }
        return si;
    }

    public void markUserAsOnline(ClientCommunication user) {
        onlineUsers.add(user);
    }

    public void removeOnlineUser(ClientCommunication user) {
        onlineUsers.remove(user);
    }
}
