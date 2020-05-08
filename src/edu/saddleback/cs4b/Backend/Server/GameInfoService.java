package edu.saddleback.cs4b.Backend.Server;

import edu.saddleback.cs4b.Backend.Database.DBManager;
import edu.saddleback.cs4b.Backend.Database.SQLDatabase;
import edu.saddleback.cs4b.Backend.Objects.Game;

import java.util.List;

/**
 * This service will return information about completed games
 */
public class GameInfoService {
    private volatile static GameInfoService gameInfoService = null;
    private DBManager database;

    private GameInfoService() {
        this.database = SQLDatabase.getInstance();
    }

    public static GameInfoService getInstance() {
        if (gameInfoService == null) {
            synchronized (GameInfoService.class) {
                if (gameInfoService == null) {
                    gameInfoService = new GameInfoService();
                }
            }
        }
        return gameInfoService;
    }

    public void addGameToDB(Game game) {
        try {
            SQLDatabase.getInstance().createNewGame(game);
        } catch (Exception e) {
            // this will throw if the game id isn't unique
            e.printStackTrace();
        }
    }

    public void saveGameResultToDB(Game game) {
        try {
            SQLDatabase.getInstance().updateGameInfo(game);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Game> completeGames() {
        try {
            return database.getAllCompletedGames();
        } catch (Exception e) {
            // thrown if a query fails
            e.printStackTrace();
        }
        return null;
    }

    public List<Game> activeGames() {
        try {
            return database.getAllActiveGames();
        } catch (Exception e) {
            // thrown if a query fails
            e.printStackTrace();
        }
        return null;
    }
}
