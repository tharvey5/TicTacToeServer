package edu.saddleback.cs4b.Backend.Messages;

import edu.saddleback.cs4b.Backend.Objects.Game;

import java.util.List;

public class GetAllActiveGamesMessage extends BaseMessage
{
    List<Game> games;

    public GetAllActiveGamesMessage(List<Game> newGames)
    {
        super(MsgTypes.ACTIVE_GAMES);

        games = newGames;
    }
    public GetAllActiveGamesMessage()
    {
        this(null);
    }

    public List<Game> getGames()
    {
        return games;
    }

    public void setGames(List<Game> newGames)
    {
        games = newGames;
    }
}
