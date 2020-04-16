package edu.saddleback.cs4b.Backend.Objects;

public interface Board
{
   void setToken(int r, int c);
   void setToken(Coordinate coordinate);
   Board getBoard();
   Token getToken(int r, int c);
}
