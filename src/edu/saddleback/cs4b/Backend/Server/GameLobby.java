package edu.saddleback.cs4b.Backend.Server;

import edu.saddleback.cs4b.Backend.Objects.Board;
import edu.saddleback.cs4b.Backend.Objects.Game;
import edu.saddleback.cs4b.Backend.Utilitys.PublicUser;

import java.util.Hashtable;
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

    private GameLobby() {
        activeGames = new Hashtable<>();
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

    public Board createGame(PublicUser user) {
        // create the new game
        // request the id from the DB
        // assign the id to the game
        // assign the user as the observer by calling the
        // system info svc to map the user, to their system representation
        // return the board of the created game
        return null;
    }
}
