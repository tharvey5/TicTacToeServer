package edu.saddleback.cs4b.Backend.Server;

import edu.saddleback.cs4b.Backend.Database.DBManager;
import edu.saddleback.cs4b.Backend.Database.SQLDatabase;
import edu.saddleback.cs4b.Backend.Messages.BaseMessage;
import edu.saddleback.cs4b.Backend.Messages.RequestAllCompletedGameMessage;
import edu.saddleback.cs4b.Backend.Messages.ReturnAllCompletedGamesMessage;
import edu.saddleback.cs4b.Backend.Objects.Game;
import edu.saddleback.cs4b.Backend.PubSub.MessageEvent;
import edu.saddleback.cs4b.Backend.PubSub.Observer;
import edu.saddleback.cs4b.Backend.PubSub.SystemEvent;
import edu.saddleback.cs4b.Backend.Utilitys.PublicUser;
import edu.saddleback.cs4b.UI.Utilities.UILogger;

import java.util.List;

/**
 * This service will return information about completed games
 */
public class GameInfoService implements Observer {
    private volatile static GameInfoService gameInfoService = new GameInfoService();
    private DBManager database;

    private GameInfoService() {
        this.database = SQLDatabase.getInstance();
        UILogger.getInstance().addObserver(this);
    }

    @Override
    public void update(SystemEvent e) {
        if (e instanceof MessageEvent) {
            BaseMessage bm = ((MessageEvent) e).getMessage();
            if (bm instanceof RequestAllCompletedGameMessage) {
                ReturnAllCompletedGamesMessage retGames = new ReturnAllCompletedGamesMessage();
                List<Game> games = completeGames();
                retGames.setGames(games);
                ServerLogger.getInstance().log(new MessageEvent(retGames));
            }
        }
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
            System.out.println(game.getGameID());
            System.out.println(game.getStartPlayer());
            System.out.println(game.getOtherPlayer());
            System.out.println(game.getCreator());
            System.out.println(game.getStartTime());
            database.createNewGame(game);
        } catch (Exception e) {
            // this will throw if the game id isn't unique
            e.printStackTrace();
        }
    }

    public void saveGameResultToDB(Game game) {
        try {
            database.updateGameInfo(game);
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

    public Game getGame(String gameId) {
        try {
            return database.getGameInfo(gameId);
        } catch (Exception e) {
            // thrown if the id doesn't match any of the given
            e.printStackTrace();
        }
        return null;
    }

    public List<Game> gameHistory(int playerId) {
        try {
            return database.getGamesOfPlayerWhereStartPlayerOrOtherPlayer(playerId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
