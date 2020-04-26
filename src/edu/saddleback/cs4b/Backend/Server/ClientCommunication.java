package edu.saddleback.cs4b.Backend.Server;

import edu.saddleback.cs4b.Backend.Messages.*;
import edu.saddleback.cs4b.Backend.Objects.Game;
import edu.saddleback.cs4b.Backend.PubSub.MessageEvent;
import edu.saddleback.cs4b.Backend.PubSub.SystemEvent;
import edu.saddleback.cs4b.Backend.Utilitys.Profile;
import edu.saddleback.cs4b.Backend.Utilitys.TTTProfile;
import edu.saddleback.cs4b.Backend.Utilitys.TTTUser;
import edu.saddleback.cs4b.Backend.Utilitys.User;

import java.io.*;
import java.net.Socket;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    private Logger log = ServerLogger.getInstance();
    private RegistrationService regSvc = RegistrationService.getInstance();


    // maps a game id to the game itself
    private Map<String, Game> gameMap;

    public ClientCommunication(Socket socket) {
        this.socket = socket;
        initiateStreams();
        SystemInfoService.getInstance().markUserAsOnline(this);
        this.msgFactory = new AdminMessageFactory();
        this.authenticator = AuthenticationService.getInstance();
        this.gameMap = new Hashtable<>();
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

                // log the new user on the UI
                log.log(new MessageEvent(new UserAddedMessage(userProfile.getUser())));
            } else {
                // output a message that denied the access to the system
                 notifyClient(new Packet(msgFactory.createMessage(MsgTypes.DENIED.getType())));
            }

        } else if (message instanceof ActiveUserMessage) {

            // todo change this out with more efficient way of getting the message
            ActiveUserResponseMessage usrMsg = (ActiveUserResponseMessage) msgFactory.createMessage(MsgTypes.ACTIVE_USER_RESPONSE.getType());
            usrMsg.setActiveUsers(getActiveUsers());
            Packet packet = new Packet(usrMsg);
            notifyClient(packet);
        } else if (message instanceof SignOutMessage) {
            SystemInfoService.getInstance().removeOnlineUser(this);
            notifyClient(new Packet(msgFactory.createMessage(MsgTypes.SIGN_OUT_CONFIRM.getType())));

            log.log(new MessageEvent(new UserRemovedMessage(userProfile.getUser())));
        } else if (message instanceof RegistrationMessage) {
            handleRegistration(message);
        } else if (message instanceof UpdateProfileMessage) {
            updateChanges(message);
        } else if (message instanceof AcctDeactivationMessage) {

            // call on the Registration service to deactivate the account
            RegistrationService.getInstance().deactivateAccount(userProfile);
            Packet packet = new Packet(msgFactory.createMessage(MsgTypes.DEACTIVATION_CONFIRM.getType()));
            notifyClient(packet);
        } else if (message instanceof CreateGameMessage) {
            Game newGame = GameLobby.getInstance().createGame(userProfile.getUser());
            gameMap.put(newGame.getGameID(), newGame);

            GameSuccessfullyCreatedMessage gameMsg =
                    (GameSuccessfullyCreatedMessage) msgFactory.createMessage(MsgTypes.GAME_CREATED.getType());
            gameMsg.setGame(newGame);
            notifyClient(new Packet(gameMsg));

        } else if (message instanceof JoinGameRequestMessage) {
            // user would like to join a game
        } else if (message instanceof ViewGameRequestMessage) {
            // user would like to view a game
        } else if (message instanceof MoveMessage) {
            Game game = gameMap.get(((MoveMessage)message).getGameId());

        }
    }

    private void updateChanges(BaseMessage message) throws IOException {
        UpdateProfileMessage updateMsg = (UpdateProfileMessage)message;
        Profile existingProfile = updateMsg.getProfile();
        existingProfile.setId(userProfile.getId());
        if (regSvc.setAccountDetails(existingProfile)) {
            log.log(new MessageEvent(new UserRemovedMessage(userProfile.getUser())));

            userProfile = existingProfile;
            BaseMessage retMsg = msgFactory.createMessage(MsgTypes.SUCCESS_UPDATE_PROFILE.getType());
            //BaseMessage retMsg = new SuccessfulUpdateProfileMessage();
            ((SuccessfulUpdateProfileMessage)retMsg).setUser(userProfile.getUser());
            notifyClient(new Packet(retMsg));
            
            log.log(new MessageEvent(new UserAddedMessage(userProfile.getUser())));
        } else {
            notifyClient(new Packet(msgFactory.createMessage(MsgTypes.INVALID_PROFILE_UPDATE.getType())));
        }
    }

    private void handleRegistration(BaseMessage message) throws IOException {
        RegistrationMessage msg = (RegistrationMessage) message;
        Profile newProfile = msg.getProfile();
        newProfile.setId("-1");
        if (regSvc.setAccountDetails(newProfile)) {
            int id = regSvc.getUsersId(newProfile);
            newProfile.setId(Integer.toString(id));
            userProfile = newProfile;
            BaseMessage retMsg = msgFactory.createMessage(MsgTypes.SUCCESS_REG.getType());
            ((SuccessfulRegistrationMessage)retMsg).setUser(userProfile.getUser());
            notifyClient(new Packet(retMsg));
        } else {
            notifyClient(new Packet(msgFactory.createMessage(MsgTypes.REG_ERROR.getType())));
        }
    }


    private void notifyClient(Packet packet) throws IOException {
        os.writeObject(packet);
        os.flush();
    }

    // todo is to finish this
    private List<String> getActiveUsers() {
        Set<String> connections = SystemInfoService.getInstance().getOnlineUsers();
        //
        return null;
    }

    @Override
    public String identifyClient() { return userProfile.getUser().getUsername(); }

    @Override
    public void update(SystemEvent e) {
        if (e instanceof MessageEvent) {
            BaseMessage bm = ((MessageEvent) e).getMessage();

            // forward the message
            try {
                notifyClient(new Packet(bm));
            } catch (IOException io) {
                io.printStackTrace();
            }
        }
    }
}
