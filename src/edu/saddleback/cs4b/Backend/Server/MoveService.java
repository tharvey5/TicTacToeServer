package edu.saddleback.cs4b.Backend.Server;

import edu.saddleback.cs4b.Backend.Objects.Move;

/**
 * This services job is to provide the database with all of the recorded moves
 */
public class MoveService {
    private volatile static MoveService moveService = null;

    private MoveService() {}

    public static MoveService getInstance() {
        if (moveService == null) {
            synchronized (MoveService.class) {
                if (moveService == null) {
                    moveService = new MoveService();
                }
            }
        }
        return moveService;
    }

    public void recordMove(Move move) {
        // call on DB to record a move when player makes a move
    }
}
