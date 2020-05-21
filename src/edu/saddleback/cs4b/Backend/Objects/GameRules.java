package edu.saddleback.cs4b.Backend.Objects;

public interface GameRules {
    boolean acceptablePlacement(Board board, Move move);
    boolean inBounds(Move move);
    String gameWinner(Board board);
}
