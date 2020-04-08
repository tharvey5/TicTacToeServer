package edu.saddleback.cs4b.Backend.Server;

import edu.saddleback.cs4b.Backend.Messages.*;

import java.io.*;
import java.net.Socket;

/**
 * Handles communication with a particular client
 */
public class ClientCommunication implements Runnable {
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

    @Override
    // the connection will listen for incoming messages
    public void run() {
        boolean isRunning = false;
        try {
            //ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            while (isRunning) {
                Packet packet = (Packet)is.readObject();
                BaseMessage message = packet.getData();
                handleMessages(message);
            }
        } catch (EOFException eof) {
            isRunning = false;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void handleMessages(BaseMessage message) {
        if (message instanceof SignInMessage) {
            SignInMessage signIn = (SignInMessage)message;

            if (AuthenticationService.getInstance().authenticate(signIn.getUserInfo())) {
                // respond with an authenticated message as output
            } else {
                // output a message that denied the access to the system
            }

        } else if (message instanceof ActiveUserMessage) {
            // ask for the system info to get you that
            // output the appropriate result
        }
    }
}
