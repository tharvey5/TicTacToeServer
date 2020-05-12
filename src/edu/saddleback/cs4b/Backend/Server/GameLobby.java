package edu.saddleback.cs4b.Backend.Server;

import edu.saddleback.cs4b.Backend.Messages.BaseMessage;
import edu.saddleback.cs4b.Backend.Messages.GameResultMessage;
import edu.saddleback.cs4b.Backend.Messages.RequestAllActiveGamesMessage;
import edu.saddleback.cs4b.Backend.Messages.ReturnAllActiveGamesMessage;
import edu.saddleback.cs4b.Backend.Objects.Board;
import edu.saddleback.cs4b.Backend.Objects.DatabaseGame;
import edu.saddleback.cs4b.Backend.Objects.Game;
import edu.saddleback.cs4b.Backend.PubSub.MessageEvent;
import edu.saddleback.cs4b.Backend.PubSub.Observer;
import edu.saddleback.cs4b.Backend.PubSub.SystemEvent;
import edu.saddleback.cs4b.Backend.Utilitys.IDGenerator;
import edu.saddleback.cs4b.Backend.Utilitys.Identifiable;
import edu.saddleback.cs4b.Backend.Utilitys.PublicUser;
import edu.saddleback.cs4b.UI.Utilities.UILogger;

import java.util.*;

/**
 * This will allow people to create games and view games
 * It will run on its own thread and thats
 */
public class GameLobby implements Observer {
    private volatile static GameLobby lobby = null;
    private Map<String, Game> activeGames;  // key = gameID, value = Game
                                            // don't return game but maybe
                                            // return who is playing the game

                                            // Convert to a list to demo
                                            // that we can show all active games

    private Map<String, Game> playableGames; // lists all games that are able to
                                             // be played and will be removed once
                                             // there are two players assigned
    private SystemInfoService sysInfo = SystemInfoService.getInstance();
    private Identifiable idGenerator;

    private GameLobby() {
        activeGames = new Hashtable<>();
        playableGames = new Hashtable<>();
        idGenerator = new IDGenerator();
        UILogger.getInstance().addObserver(this);
        System.out.println("Lobby init");
    }

    public static GameLobby getInstance() {
        if (lobby == null) {
            synchronized (GameLobby.class) {
                if (lobby == null) {
                    lobby = new GameLobby();
                }
            }
        }
        return lobby;
    }

    // what if they want to play against the AI
    public Game createGame(PublicUser player) {
        TTTGame newGame = new TTTGame(player);
        Thread gameThread = new Thread(newGame);
        gameThread.start();

        newGame.setGameID(idGenerator.getId());

        activeGames.put(newGame.getGameID(), newGame);
        playableGames.put(newGame.getGameID(), newGame);
        newGame.addObserver(sysInfo.getConnection(player.getUsername()));
        newGame.addObserver(this);
        return newGame;
    }

    public Game joinGame(PublicUser player, String gameId) {
        Game game = playableGames.get(gameId);
        if (game != null) {
            // call system service to get their connection and set as observer

            game.addObserver(sysInfo.getConnection(player.getUsername()));
            game.setOtherPlayer(player);
            playableGames.remove(gameId);
            return game;
        } else {
            return null;
        }
    }

    public Game viewGame(PublicUser viewer, String gameId) {
        Game game = activeGames.get(gameId);

        game.addObserver(sysInfo.getConnection(viewer.getUsername()));
        GameInfoService.getInstance().writeViewer(gameId, viewer);

        return game;
    }

    public List<Game> getActiveGames() {
        List<Game> games = new ArrayList<>();
        for (Map.Entry<String,Game> entry : activeGames.entrySet()) {
            games.add(new DatabaseGame(entry.getValue()));
        }
        return games;
    }

    public Map<String, List<String>> getAllGames(){
        Map<String, List<String>> games = new Hashtable<>();
        for (String id : activeGames.keySet()) {
            String p1 = activeGames.get(id).getStartPlayer().getUsername();
//            String p2 = activeGames.get(id).getOtherPlayer().getUsername();
//            games.put(id, List.of(p1));

            if (activeGames.get(id).getOtherPlayer() != null) {
                String p2 = activeGames.get(id).getOtherPlayer().getUsername();
                games.put(id, List.of(p1, p2));
            } else {
                games.put(id, List.of(p1));
            }
        }
        return games;
    }

    public Map<String, List<String>> getAllPlayableGames(){
        Map<String, List<String>> games = new Hashtable<>();
        for (String id : playableGames.keySet()) {
            games.put(id, List.of(playableGames.get(id).getCreator().getUsername()));
        }
        return games;
    }

    // listening to internal system events such as games and the UI windows
    @Override
    public void update(SystemEvent e) {
        if (e instanceof MessageEvent) {
            BaseMessage bm = ((MessageEvent) e).getMessage();
            if (bm instanceof GameResultMessage) {
                String id = ((GameResultMessage) bm).getGameId();

                ServerLogger.getInstance().notifyObserver(
                        new MessageEvent(
                        new UserRemovedGameMessage(activeGames.get(id).getStartPlayer().getUsername())));

                ServerLogger.getInstance().notifyObserver(
                        new MessageEvent(
                        new UserRemovedGameMessage(activeGames.get(id).getOtherPlayer().getUsername())));

                // todo include this for viewers as well

                GameInfoService.getInstance().saveGameResultToDB(activeGames.get(id));
                activeGames.remove(id);

                System.out.println(id + " HAS BEEN REMOVED");
            } else if (bm instanceof RequestAllActiveGamesMessage) {

                ReturnAllActiveGamesMessage retMsg = new ReturnAllActiveGamesMessage();
                retMsg.setGameAndPlayers(getAllGames());
                ServerLogger.getInstance().notifyObserver(new MessageEvent(retMsg));
            }
        }
    }
}
