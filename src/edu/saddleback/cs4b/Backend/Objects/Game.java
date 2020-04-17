package edu.saddleback.cs4b.Backend.Objects;

import java.time.LocalDateTime;
import java.util.List;

public interface Game
{
    public String getStartTime();
    public void setStartTime(LocalDateTime newTime);

    public String getStartPlayer();
    public void setStartPlayer(String username);

    void setCreator(String username);
    String getCreator();

    public String getOtherPlayer();
    public void setOtherPlayer (String username);

    public Moves getMoves();
    public void setMoves(Moves newMoves);
    public void addMove(Move newMove);

    public String getWinner();

    public String getGameID();
    public void setGameID(String newGameID);

    public Board getGameBoard();
    public void setGameBoard(Board newBoard);

}
