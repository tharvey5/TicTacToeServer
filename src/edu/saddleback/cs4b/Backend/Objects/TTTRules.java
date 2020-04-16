package edu.saddleback.cs4b.Backend.Objects;

public class TTTRules implements GameRules {
    @Override
    public boolean acceptablePlacement(Board board, Move move) {
        return false;
    }

    @Override
    public boolean inBounds(Move move) {
        return false;
    }

    @Override
    public String gameWinner(Board board) {
        return null;
    }
}
