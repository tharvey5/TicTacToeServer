package edu.saddleback.cs4b.Backend.Server;

import edu.saddleback.cs4b.Backend.Objects.Board;
import edu.saddleback.cs4b.Backend.Objects.GameRules;
import edu.saddleback.cs4b.Backend.Objects.Move;

/**
 * This service is responsible for managing and checking the logic of a game
 * This service will validate the move based on a rules can check if there was a
 * winner based on rules
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

    public boolean validMove(Board board, Move move, GameRules rules) {
        return rules.inBounds(move) && rules.acceptablePlacement(board, move);
    }

    /**
     * returns the user name of the winner or "" if no winner
     */
    public String findWinner(Board board, GameRules rules) {
        return rules.gameWinner(board);
    }
}
