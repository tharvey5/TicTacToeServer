package edu.saddleback.cs4b.Backend.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

/**
 * Simply listens for new connections and manages those connections
 */
public class ConnectionService {
    private volatile static ConnectionService connectionService = null;

    private List<ClientCommunication> connections;
    private ServerSocket serverSocket;
    private final static int port = 8080;

    private ConnectionService() {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ConnectionService getInstance() {
        if (connectionService == null) {
            synchronized (ConnectionService.class) {
                if (connectionService == null) {
                    connectionService = new ConnectionService();
                }
            }
        }
        return connectionService;
    }

    /**
     * this method will start the connectionService
     * // TODO prevent from being started once its already started
     */
    public void start() {
        boolean isRunning = true;
        Socket client = null;

        while (isRunning) {
            try {
                client = serverSocket.accept();
            } catch (SocketException se) {
                isRunning = false;
            } catch (IOException io) {
                io.printStackTrace();
            }
        }
    }
}
