package edu.saddleback.cs4b.Backend.Server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Keeps track stats about the system such as who is logged in/active
 * number of games running etc
 */
public class SystemInfoService {
    private static volatile SystemInfoService si = null;
    private List<ClientConnection> onlineUsers;

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

    public synchronized void markUserAsOnline(ClientConnection user) {
        onlineUsers.add(user);
    }

    public synchronized void removeOnlineUser(ClientConnection user) {
        onlineUsers.remove(user);
    }

    public List<ClientConnection> getOnlineUsers() {
        return Collections.unmodifiableList(onlineUsers);
    }
}
