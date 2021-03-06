package edu.saddleback.cs4b.Backend.Objects;

import java.io.Serializable;

public class TTTPosition implements Coordinate, Serializable
{
    private int xCoord;
    private int yCoord;

    public TTTPosition()
    {
        this(0,0);
    }
    public TTTPosition(int newX, int newY)
    {
        xCoord = newX;
        yCoord = newY;
    }

    public int getXCoord()
    {
        return xCoord;
    }

    public void setXCoord(int newXCoord)
    {
        this.xCoord = xCoord;
    }

    public int getYCoord()
    {
        return yCoord;
    }

    public void setYCoord(int newYCoord)
    {
        this.yCoord = yCoord;
    }

    public String getPositionAsString() { return toString(); }

    public String toString()
    {
        return "(" + xCoord + ", " + yCoord + ")";
    }
}
