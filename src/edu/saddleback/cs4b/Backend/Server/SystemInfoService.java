package edu.saddleback.cs4b.Backend.Server;

import java.util.*;

/**
 * Keeps track stats about the system such as who is logged in/active
 * number of games running etc
 */
public class SystemInfoService {
    private static volatile SystemInfoService si = null;
    //private List<ClientConnection> onlineUsers;
    private Map<String, ClientConnection> onlineUsers;

    private SystemInfoService() {
        onlineUsers = new Hashtable<>();
        //onlineUsers = new ArrayList<>();
    }

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
        onlineUsers.put(user.identifyClient(), user);
    }

    public synchronized void removeOnlineUser(ClientConnection user) {
        onlineUsers.remove(user.identifyClient());
    }

    public Set<String> getOnlineUsers() {
        return Collections.unmodifiableSet(onlineUsers.keySet());
    }

    public ClientConnection getConnection(String onlineUser) {
        return onlineUsers.get(onlineUser);
    }
}
