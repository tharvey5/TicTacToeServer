package edu.saddleback.cs4b.Backend.Server;

import edu.saddleback.cs4b.Backend.Objects.Board;
import edu.saddleback.cs4b.Backend.Objects.Game;
import edu.saddleback.cs4b.Backend.Utilitys.PublicUser;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * This will allow people to create games and view games
 * It will run on its own thread and thats
 */
public class GameLobby {
    private volatile static GameLobby lobby = null;
    private Map<String, Game> activeGames;  // key = gameID, value = Game
                                            // don't return game but maybe
                                            // return who is playing the game

                                            // Convert to a list to demo
                                            // that we can show all active games

    private Map<String, Game> playableGames; // lists all games that are able to
                                             // be played and will be removed once
                                             // there are two players assigned

    private GameLobby() {
        activeGames = new Hashtable<>();
        playableGames = new Hashtable<>();
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

    public Board createGame(PublicUser player) {
        // create the new game
        // request the id from the DB
        // assign the id to the game
        // assign the user as the observer by calling the
        // system info svc to map the user, to their system representation
        // return the board of the created game
        return null;
    }

    public Board joinGame(PublicUser player, String gameId) {
        Game game = playableGames.get(gameId);
        if (game != null) {
            // call system service to get their connection and set as observer
            game.setOtherPlayer(player);
            playableGames.remove(gameId);
            return game.getGameBoard();
        } else {
            return null;
        }
    }

    public Board viewGame(PublicUser viewer, String gameId) {
        Game game = activeGames.get(gameId);
        // call system service to get their connection and set as observer
        return game.getGameBoard();
    }

    public Map<String, List<String>> getAllGames(){
        Map<String, List<String>> games = new Hashtable<>();
        for (String id : activeGames.keySet()) {
            String p1 = activeGames.get(id).getStartPlayer().getUsername();
            String p2 = activeGames.get(id).getOtherPlayer().getUsername();
            games.put(id, List.of(p1, p2));
        }
        return games;
    }

    public void getAllPlayableGames(){}
}
