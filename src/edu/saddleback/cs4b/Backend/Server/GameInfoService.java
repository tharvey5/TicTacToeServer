package edu.saddleback.cs4b.Backend.Server;

import edu.saddleback.cs4b.Backend.Database.DBManager;
import edu.saddleback.cs4b.Backend.Database.SQLDatabase;
import edu.saddleback.cs4b.Backend.Messages.BaseMessage;
import edu.saddleback.cs4b.Backend.Messages.RequestAllCompletedGameMessage;
import edu.saddleback.cs4b.Backend.Messages.ReturnAllCompletedGamesMessage;
import edu.saddleback.cs4b.Backend.Objects.Game;
import edu.saddleback.cs4b.Backend.Objects.Move;
import edu.saddleback.cs4b.Backend.PubSub.MessageEvent;
import edu.saddleback.cs4b.Backend.PubSub.Observer;
import edu.saddleback.cs4b.Backend.PubSub.SystemEvent;
import edu.saddleback.cs4b.Backend.Utilitys.PublicUser;
import edu.saddleback.cs4b.UI.Utilities.UILogger;

import java.util.ArrayList;
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

    public boolean writeMove(Move move) {
        try {
            SQLDatabase.getInstance().addMoveToGame(move);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * idx 0 = wins, idx 1 = lost
     */
    public List<Integer> getWLandTotalGames(int id) {
        List<Integer> winLossTotal = new ArrayList<>();
        try {
            winLossTotal.add(database.getUserWins(id));
            winLossTotal.add(database.getUserLosses(id));
            winLossTotal.add(database.getUserTotalGames(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return winLossTotal;
    }

    public void addGameToDB(Game game) {
        try {
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
