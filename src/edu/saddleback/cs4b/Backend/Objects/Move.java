package edu.saddleback.cs4b.Backend.Objects;

import edu.saddleback.cs4b.Backend.Utilitys.PublicUser;

import java.time.LocalDateTime;

public interface Move
{
    public String getGameID();
    public void setGameID(String newGameID);

    public String getStartTime();
    public void setStartTime(LocalDateTime time);

    public PublicUser getPlayerID();
    public void setPlayerID(PublicUser newPlayerID);

    public Coordinate getCoordinate();
    public void setCoordinate(Coordinate newCoordinate);
}
