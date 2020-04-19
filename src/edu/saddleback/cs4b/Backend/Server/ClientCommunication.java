package edu.saddleback.cs4b.Backend.Server;

import edu.saddleback.cs4b.Backend.Messages.*;
import edu.saddleback.cs4b.Backend.Utilitys.Profile;
import edu.saddleback.cs4b.Backend.Utilitys.TTTProfile;
import edu.saddleback.cs4b.Backend.Utilitys.TTTUser;
import edu.saddleback.cs4b.Backend.Utilitys.User;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles communication with a particular client
 */
public class ClientCommunication implements Runnable, ClientConnection {
    private Socket socket;
    private ObjectOutputStream os;
    private ObjectInputStream is;
    private Profile userProfile; // set when registered
    // probably hold the profile information?
    private AbstractMessageFactory msgFactory;
    private Authenticator authenticator;

    public ClientCommunication(Socket socket) {
        this.socket = socket;
        initiateStreams();
        SystemInfoService.getInstance().markUserAsOnline(this);
        this.msgFactory = new AdminMessageFactory();
        this.authenticator = AuthenticationService.getInstance();
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
        boolean isRunning = true;
        try {
            //ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            while (isRunning) {
                Packet packet = (Packet)is.readObject();
                BaseMessage message = packet.getData();

                // todo userProfile must be registered before they can proceed
                //should be a way to only sign-in and register before
                //other stuff can be done
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

    private void handleMessages(BaseMessage message) throws IOException {
        if (message instanceof SignInMessage) {
            SignInMessage signIn = (SignInMessage)message;
            User userProcessed = (User)authenticator.authenticate(signIn.getUserInfo());
            if (userProcessed != null) {

                 // if the user was authenticated, then set the user profile
                AuthenticatedMessage authMsg = (AuthenticatedMessage) msgFactory.createMessage(MsgTypes.AUTHENTICATION.getType());
                authMsg.setAuthUser(userProcessed);
                 notifyClient(new Packet(authMsg));
                 userProfile = new TTTProfile((TTTUser)userProcessed);
                 int id = RegistrationService.getInstance().getUsersId(userProfile);
                 userProfile.setId(Integer.toString(id));
            } else {
                // output a message that denied the access to the system
                 notifyClient(new Packet(msgFactory.createMessage(MsgTypes.DENIED.getType())));
            }

        } else if (message instanceof ActiveUserMessage) {

            // todo change this out with more efficient way of getting the message
            ActiveUserMessage usrMsg = (ActiveUserMessage) msgFactory.createMessage(MsgTypes.ACTIVE_USER_REQ.getType());
            usrMsg.setActiveUsers(getActiveUsers());
            Packet packet = new Packet(usrMsg);
            notifyClient(packet);
        } else if (message instanceof SignOutMessage) {

            SystemInfoService.getInstance().removeOnlineUser(this);
            notifyClient(new Packet(msgFactory.createMessage(MsgTypes.SIGN_OUT.getType())));
            // todo finish this up
            // propagate to the users

        } else if (message instanceof ProfileMessage) {

            Profile profileToProcess = ((ProfileMessage) message).getProfile();
            handleProfile(profileToProcess);
        } else if (message instanceof AcctDeactivationMessage) {

            // call on the Registration service to deactivate the account
            RegistrationService.getInstance().deactivateAccount(userProfile);
            Packet packet = new Packet(msgFactory.createMessage(MsgTypes.DEACTIVATION.getType()));
            notifyClient(packet);
        }
    }

    private void handleProfile(Profile profileToProcess) throws IOException {
        if (userProfile != null && !userProfile.getId().equals("-1")) {
            profileToProcess.setId(userProfile.getId());
        }

        if (RegistrationService.getInstance().setAccountDetails(profileToProcess)) {
            userProfile = profileToProcess;
            SuccessfulRegistration msg = (SuccessfulRegistration)msgFactory.createMessage(MsgTypes.SUCCESS_REG.getType());
            msg.setUser(userProfile.getUser());
            notifyClient(new Packet(msg));
        } else {
            notifyClient(new Packet(msgFactory.createMessage(MsgTypes.REG_ERROR.getType())));
        }
    }

    private void notifyClient(Packet packet) throws IOException {
        os.writeObject(packet);
        os.flush();
    }

    private List<String> getActiveUsers() {
        List<ClientConnection> connections = SystemInfoService.getInstance().getOnlineUsers();
        List<String> users = new ArrayList<>();
        for (ClientConnection c : connections) {
            users.add(c.identifyClient());
        }
        return users;
    }

    @Override
    public String identifyClient() { return userProfile.getUser().getUsername(); }
}
