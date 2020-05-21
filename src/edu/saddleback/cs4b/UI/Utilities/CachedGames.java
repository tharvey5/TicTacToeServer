package edu.saddleback.cs4b.UI.Utilities;

import edu.saddleback.cs4b.Backend.Objects.Game;

import java.util.*;

public class CachedGames {
    private static CachedGames local = new CachedGames();
    private Map<String, Game> gameMap;

    private CachedGames() {
        gameMap = new Hashtable<>();
    }

    public static CachedGames getInstance() { return local; }

    public void addGame(Game game) {
        if (gameMap.containsKey(game.getGameID())) return;
        gameMap.put(game.getGameID(), game);
    }

    public Game getGame(String gameId) {
        return gameMap.get(gameId);
    }

    public Collection<Game> getGames() { return gameMap.values(); }

}
