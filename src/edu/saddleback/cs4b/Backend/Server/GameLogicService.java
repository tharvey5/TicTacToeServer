package edu.saddleback.cs4b.Backend.Server;

/**
 * This service is responsible for managing and checking the logic of a game
 */
public class GameLogicService {
    private volatile static GameLogicService logicService = null;

    private GameLogicService() {}
    
    public static GameLogicService getInstance() {
        if (logicService == null) {
            synchronized (GameLogicService.class) {
                if (logicService == null) {
                    logicService = new GameLogicService();
                }
            }
        }
        return logicService;
    }
}
