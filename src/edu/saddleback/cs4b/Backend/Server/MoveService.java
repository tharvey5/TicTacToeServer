package edu.saddleback.cs4b.Backend.Server;

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
}
