package edu.saddleback.cs4b.UI.Utilities;

import edu.saddleback.cs4b.Backend.Objects.Game;

import java.util.List;

public class CachedGames {
    private volatile static CachedGames cachedGames = new CachedGames();
    private List<Game> games;

    private CachedGames() {}


    public static CachedGames getInstance() {
        return cachedGames;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }
}
