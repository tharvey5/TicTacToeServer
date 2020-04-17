package edu.saddleback.cs4b.Backend.Objects;

import java.util.List;

public interface Moves
{
    public Moves getMoves();
    public void setMoves(List<Move> newMoves);
    public void addMove(Move newMove);
    int getMoveId(Move move);
    int getMoveId(int index);
}

