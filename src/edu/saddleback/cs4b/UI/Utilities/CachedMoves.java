package edu.saddleback.cs4b.UI.Utilities;

import edu.saddleback.cs4b.Backend.Objects.Game;
import edu.saddleback.cs4b.Backend.Objects.Move;

import java.util.List;

public class CachedMoves {
    private volatile static CachedMoves cachedGames = new CachedMoves();
    private List<Move> moves;

    private CachedMoves() {}


    public static CachedMoves getInstance() {
        return cachedGames;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }
}
