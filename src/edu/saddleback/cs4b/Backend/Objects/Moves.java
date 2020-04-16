package edu.saddleback.cs4b.Backend.Objects;

import java.util.List;

public interface Moves
{
    public List<Move> getMoves();
    public void setMoves(List<Move>);
    public void addMove(Move newMove);
}
// get moves should return a list of List<Move>

}
