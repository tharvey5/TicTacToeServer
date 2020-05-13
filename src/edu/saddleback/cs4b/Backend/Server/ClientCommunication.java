package edu.saddleback.cs4b.Backend.Server;

import edu.saddleback.cs4b.Backend.Messages.*;
import edu.saddleback.cs4b.Backend.Objects.DatabaseGame;
import edu.saddleback.cs4b.Backend.Objects.Game;
import edu.saddleback.cs4b.Backend.Objects.Move;
import edu.saddleback.cs4b.Backend.Objects.TTTMove;
import edu.saddleback.cs4b.Backend.PubSub.MessageEvent;
import edu.saddleback.cs4b.Backend.PubSub.SystemEvent;
import edu.saddleback.cs4b.Backend.Utilitys.Profile;
import edu.saddleback.cs4b.Backend.Utilitys.TTTProfile;
import edu.saddleback.cs4b.Backend.Utilitys.TTTUser;
import edu.saddleback.cs4b.Backend.Utilitys.User;
import edu.saddleback.cs4b.UI.Utilities.GameInfo;

import java.io.*;
import java.net.Socket;
import java.util.*;

/**
 * Handles communication with a particular client
 */
public class ClientCommunication implements Runnable, ClientConnection {
    private Socket socket;
    private ObjectOutputStream os;
    private ObjectInputStream is;
    private Profile userProfile; // set when registered
    // probably hold the profile information?
    private AbstractMessageFactory adminFactory;
    private AbstractMessageFactory gameFactory;
    private Authenticator authenticator;
    private Logger log = ServerLogger.getInstance();
    private RegistrationService regSvc = RegistrationService.getInstance();


    // maps a game id to the game itself
    private Map<String, Game> gameMap;

    public ClientCommunication(Socket socket) {
        this.socket = socket;
        initiateStreams();
        this.adminFactory = MessageFactoryProducer.getFactory(FactoryTypes.ADMIN_FACT.getTypes());
        this.gameFactory = MessageFactoryProducer.getFactory(FactoryTypes.GAME_FACT.getTypes());
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
                AuthenticatedMessage authMsg = (AuthenticatedMessage) adminFactory.createMessage(MsgTypes.AUTHENTICATION.getType());
                authMsg.setAuthUser(userProcessed);
                userProfile = new TTTProfile((TTTUser)userProcessed);

                int id = RegistrationService.getInstance().getUsersId(userProfile);
                userProfile.setId(Integer.toString(id));

                List<Integer> record = GameInfoService.getInstance().getWLandTotalGames(Integer.parseInt(userProfile.getId()));
                ((TTTProfile)userProfile).getGameRecord().setWins(record.get(0));
                ((TTTProfile)userProfile).getGameRecord().setLosses(record.get(1));
                ((TTTProfile)userProfile).getGameRecord().setTotalGames(record.get(2));


                //todo since there is an ai for each user logged in, just grab the number of users logged in
                // if its an AI && the second condition below is met 
                if (SystemInfoService.getInstance().getConnection(userProfile.getUser().getUsername()) != null) {
                    int userNum = SystemInfoService.getInstance().numberConnectedUsers();
                    String name = userProfile.getUser().getUsername();
                    User otherLogin = new TTTUser(name + userNum,
                            userProfile.getUser().getFirstName(), userProfile.getUser().getLastName(),
                            userProfile.getUser().getPassword());
                    ((TTTUser) otherLogin).setId(id);
                    String oldId = userProfile.getId();
                    userProfile.setUser(otherLogin);
                    System.out.println("occuring");
                    authMsg.setAuthUser(otherLogin);
                }

                authMsg.setProfile(userProfile);
                notifyClient(new Packet(authMsg));
//                if (SystemInfoService.getInstance().getConnection(userProfile.getUsername().getUsername()) != null) {
//                    String name = userProfile.getUsername().getUsername();
//                    User otherLogin = new TTTUser(name + new Random().nextInt(50),
//                            userProfile.getUsername().getFirstName(), userProfile.getUsername().getLastName(),
//                            userProfile.getUsername().getPassword());
//                    userProfile.setUsername(otherLogin);
//                }

                // log the new user on the UI
                SystemInfoService.getInstance().markUserAsOnline(this);
                log.log(new MessageEvent(new UserAddedMessage(userProfile.getUser())));
            } else {
                // output a message that denied the access to the system
                 notifyClient(new Packet(adminFactory.createMessage(MsgTypes.DENIED.getType())));
            }

        }
        else if (message instanceof ActiveUserMessage) {

            // todo change this out with more efficient way of getting the message
            ActiveUserResponseMessage usrMsg = (ActiveUserResponseMessage) adminFactory.createMessage(MsgTypes.ACTIVE_USER_RESPONSE.getType());
            usrMsg.setActiveUsers(getActiveUsers());
            Packet packet = new Packet(usrMsg);
            notifyClient(packet);
        }
        else if (message instanceof SignOutMessage) {
            SystemInfoService.getInstance().removeOnlineUser(this);
            notifyClient(new Packet(adminFactory.createMessage(MsgTypes.SIGN_OUT_CONFIRM.getType())));

            log.log(new MessageEvent(new UserRemovedMessage(userProfile.getUser())));
        }
        else if (message instanceof RegistrationMessage) {
            handleRegistration(message);
        }
        else if (message instanceof UpdateProfileMessage) {
            updateChanges(message);
        }
        else if (message instanceof AcctDeactivationMessage) {

            // call on the Registration service to deactivate the account
            RegistrationService.getInstance().deactivateAccount(userProfile);
            Packet packet = new Packet(adminFactory.createMessage(MsgTypes.DEACTIVATION_CONFIRM.getType()));
            notifyClient(packet);

            log.log(new MessageEvent(new UserRemovedMessage(userProfile.getUser())));
        }
        else if (message instanceof CreateGameMessage) {
            Game newGame = GameLobby.getInstance().createGame(userProfile.getUser());
            gameMap.put(newGame.getGameID(), newGame);

            GameSuccessfullyCreatedMessage gameMsg =
                    (GameSuccessfullyCreatedMessage) gameFactory.createMessage(MsgTypes.GAME_CREATED.getType());
            gameMsg.setGame(new DatabaseGame(newGame));
            gameMsg.setGameId(newGame.getGameID());
            notifyClient(new Packet(gameMsg));

            log.log(new MessageEvent(new UserAddedGameMessage(userProfile.getUser().getUsername(), newGame.getGameID())));

        }
        else if (message instanceof JoinGameRequestMessage) {
            JoinGameRequestMessage reqMsg = (JoinGameRequestMessage)message;
            Game newGame = GameLobby.getInstance().joinGame(userProfile.getUser(), reqMsg.getGameID());
            if (newGame != null) {
                gameMap.put(newGame.getGameID(), newGame);
                AvailableGameMessage gameMsg =
                        (AvailableGameMessage) gameFactory.createMessage(MsgTypes.AVAILABLE_GAME.getType());
                gameMsg.setGame(new DatabaseGame(newGame));
                gameMsg.setGameId(newGame.getGameID());
                notifyClient(new Packet(gameMsg));

                GameInfoService.getInstance().addGameToDB(newGame);
                log.log(new MessageEvent(new UserAddedGameMessage(userProfile.getUser().getUsername(), newGame.getGameID())));
            } else {
                notifyClient(new Packet(gameFactory.createMessage(MsgTypes.UNAVAILABLE_GAME.getType())));
            }

        }
        else if (message instanceof ViewGameRequestMessage) {
            ViewGameRequestMessage viewMsg = (ViewGameRequestMessage)message;
            Game game = GameLobby.getInstance().viewGame(userProfile.getUser(), viewMsg.getGameID());
            if (game != null) {
                SuccessfulViewGameMessage successView = (SuccessfulViewGameMessage)gameFactory.createMessage(MsgTypes.SUCCESS_VIEW_GAME.getType());
                successView.setGame(new DatabaseGame(game));
                notifyClient(new Packet(successView));
                log.log(new MessageEvent(new UserAddedGameMessage(userProfile.getUser().getUsername(), game.getGameID())));
            } else {
                notifyClient(new Packet(gameFactory.createMessage(MsgTypes.NO_GAME_TO_VIEW.getType())));
            }

        }
        else if (message instanceof MoveMessage) {
            MoveMessage moveMsg = (MoveMessage)message;
            Game game = gameMap.get(moveMsg.getGameId());
            boolean validMove = game.playMove(new TTTMove(moveMsg.getGameId(), userProfile.getId(), moveMsg.getCoordinate()));
            if (!validMove) {
                InvalidMoveMessage invalidMove = (InvalidMoveMessage) gameFactory.createMessage(MsgTypes.INVALID_MOVE.getType());
                notifyClient(new Packet(invalidMove));
            }
        }
        else if (message instanceof RequestAllActiveGamesMessage) {
            ReturnAllActiveGamesMessage retGames = (ReturnAllActiveGamesMessage) gameFactory.createMessage(MsgTypes.RETURN_ACTIVE_GAMES.getType());
            retGames.setGames(GameLobby.getInstance().getActiveGames());
            notifyClient(new Packet(retGames));
        }
        else if (message instanceof RequestSingleGameMessage) {
            ReturnSingleGameMessage retGame = new ReturnSingleGameMessage();
            Game requestedGame = GameInfoService.getInstance().getGame(((RequestSingleGameMessage)message).getGameId());
            retGame.setGame(requestedGame);
            notifyClient(new Packet(retGame));
        }
        else if (message instanceof GameHistoryRequestMessage) {
            GameHistoryResponseMessage responseMsg = (GameHistoryResponseMessage) gameFactory.createMessage(MsgTypes.GAME_HISTORY_RESPONSE.getType());
            List<Game> games = GameInfoService.getInstance().gameHistory(Integer.parseInt(userProfile.getId()));
            responseMsg.setGames(games);
            notifyClient(new Packet(responseMsg));
        }
        else if (message instanceof RequestMovesOfGameMessage) {
            RespondMovesMessage respMsg = new RespondMovesMessage();
            List<Move> moves = GameInfoService.getInstance().getAllMoves(((RequestMovesOfGameMessage) message).getGameId());
            respMsg.setMoves(moves);
            notifyClient(new Packet(respMsg));
        }
    }

    private void updateChanges(BaseMessage message) throws IOException {
        UpdateProfileMessage updateMsg = (UpdateProfileMessage)message;
        Profile existingProfile = updateMsg.getProfile();
        existingProfile.setId(userProfile.getId());
        if (regSvc.setAccountDetails(existingProfile)) {
            log.log(new MessageEvent(new UserRemovedMessage(userProfile.getUser())));

            userProfile = existingProfile;
            BaseMessage retMsg = adminFactory.createMessage(MsgTypes.SUCCESS_UPDATE_PROFILE.getType());
            //BaseMessage retMsg = new SuccessfulUpdateProfileMessage();
            ((SuccessfulUpdateProfileMessage)retMsg).setUser(userProfile.getUser());
            notifyClient(new Packet(retMsg));
            
            log.log(new MessageEvent(new UserAddedMessage(userProfile.getUser())));
        } else {
            notifyClient(new Packet(adminFactory.createMessage(MsgTypes.INVALID_PROFILE_UPDATE.getType())));
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
            BaseMessage retMsg = adminFactory.createMessage(MsgTypes.SUCCESS_REG.getType());
            ((SuccessfulRegistrationMessage)retMsg).setUser(userProfile.getUser());
            notifyClient(new Packet(retMsg));
        } else {
            notifyClient(new Packet(adminFactory.createMessage(MsgTypes.REG_ERROR.getType())));
        }
    }


    private synchronized void notifyClient(Packet packet) throws IOException {
        os.reset();
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
