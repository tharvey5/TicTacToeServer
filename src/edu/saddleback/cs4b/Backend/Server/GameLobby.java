package edu.saddleback.cs4b.Backend.Server;

/**
 * This will allow people to create games and view games
 * It will run on its own thread and thats
 */
public class GameLobby {
    private volatile static GameLobby lobby = null;

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
}
