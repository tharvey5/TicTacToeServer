package edu.saddleback.cs4b.Backend.Server;

/**
 * This service will return information about completed games
 */
public class GameInfoService {
    private volatile static GameInfoService gameInfoService = null;

    private GameInfoService() {}

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
}
