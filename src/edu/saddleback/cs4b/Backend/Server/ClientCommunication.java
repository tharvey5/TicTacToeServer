package edu.saddleback.cs4b.Backend.Server;

import java.io.*;
import java.net.Socket;

/**
 * Handles communication with a particular client
 */
public class ClientCommunication {
    private Socket socket;
    private ObjectOutputStream os;
    private ObjectInputStream is;
    // probably hold the profile information?

    public ClientCommunication(Socket socket) {
        this.socket = socket;
        initiateStreams();
    }

    private void initiateStreams() {
        try {
            this.is = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            this.os = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
