package edu.saddleback.cs4b.Backend.Database;

import edu.saddleback.cs4b.Backend.Objects.*;
import edu.saddleback.cs4b.Backend.Utilitys.PublicUser;
import edu.saddleback.cs4b.Backend.Utilitys.TTTPublicUser;
import edu.saddleback.cs4b.Backend.Utilitys.TTTUser;
import edu.saddleback.cs4b.Backend.Utilitys.User;
import org.junit.jupiter.api.Test;

import java.sql.Array;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SQLDatabaseTest {

    @Test
    void getUsername() throws SQLException
    {
        DBManager db = SQLDatabase.getInstance();

        assertEquals("Boxhead1", db.getUsername(1));
    }

    @Test
    void login()
    {
        String username = "Boxhead2";
        String password = "1234";

        User user = null;

        DBManager db = SQLDatabase.getInstance();

        String error;
        try
        {
            user = db.Login(username, password);
            error = "Login didnt fail";
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            error = "Username or Password incorrect";
        }

        assertEquals("Login didnt fail", error);

        try
        {
            assertEquals(username, db.getUsername(db.getUniqueID(user)));
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }

    @Test
    void addUser() throws Exception {
        String username = "Boxhead3";
        String password = "1234";
        String firstName = "Isaac";
        String lastName = "Estrada";

        DBManager db = SQLDatabase.getInstance();

        User user = new TTTUser(username, password, firstName, lastName);
        String error;
        int id;

        try
        {
            id = db.addUser(user);
            error = "add user didnt fail";
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            error = "Username not unique";
        }

        assertEquals("add user didnt fail", error);
        assertEquals(username, db.getUsername(db.getUniqueID(user)));
    }

    @Test
    void inactivateUser()
    {
        String error;
        int idOfInactivateUser = 1;

        DBManager db = SQLDatabase.getInstance();

        String status;

        try
        {
            db.inactivateUser(idOfInactivateUser);
            error = "didnt fail";
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            error = "failed";
            status = "could not find status";
        }

        assertEquals("didnt fail", error);
        try
        {
            assertEquals("Inactive", db.getUsersStatus(idOfInactivateUser));
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }

    @Test
    void activateUser()
    {
        String error;


        int idOfActivateUser = 2;

        DBManager db = SQLDatabase.getInstance();

        String status;

        try
        {
            db.activateUser(idOfActivateUser);
            error = "didnt fail";
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            error = "failed";
            status = "could not find status";
        }

        assertEquals("didnt fail", error);
        try
        {
            assertEquals("Active", db.getUsersStatus(idOfActivateUser));
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }

    @Test
    void getUsersStatus()
    {
        String error;

        DBManager db = SQLDatabase.getInstance();

        int id = 1;

        String expectedStatus = "Active";
        String status;

        try
        {
            status = db.getUsersStatus(id);
            error = "didnt fail";
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            error = "failed";
            id = 0;
            status = "could not find status";
        }

        assertEquals("didnt fail", error);
        assertEquals(expectedStatus, status);
    }

    @Test
    void updateUser()
    {

        DBManager db = SQLDatabase.getInstance();

        String error;

        User user = new TTTUser("JEFF", "1235", "Isaac", "Estrada");
        int idOfUpdatedUser = 1;

        String status;

        try
        {
            db.updateUser(idOfUpdatedUser, user);
            error = "didnt fail";
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            error = "failed";
        }

        assertEquals("didnt fail", error);
    }

    @Test
    void createGame()
    {

        TTTPublicUser John = new TTTPublicUser("1","John1");
        TTTPublicUser Smith = new TTTPublicUser("2","Smith2");
        TTTPublicUser Erica = new TTTPublicUser("3","Erica3");
        TTTPublicUser Isaac = new TTTPublicUser("4","Isaac4");

        List<PublicUser> viewers = new ArrayList<PublicUser>();

        DBManager db = SQLDatabase.getInstance();

        Game game = new Game() {

            String id;

            @Override
            public void setToken(Token token, PublicUser user) {

            }

            @Override
            public Token getToken(PublicUser user) {
                return null;
            }

            @Override
            public String getStartTime() {
                return "1:20 AM";
            }

            @Override
            public void setStartTime(LocalDateTime newTime) {

            }

            @Override
            public void setEndTime(LocalDateTime endTime) {

            }

            @Override
            public String getEndTime() {
                return null;
            }

            @Override
            public PublicUser getStartPlayer() {
                return Isaac;
            }

            @Override
            public void setStartPlayer(PublicUser user) {

            }

            @Override
            public void setCreator(PublicUser user) {

            }

            @Override
            public PublicUser getCreator() {
                return Isaac;
            }

            @Override
            public PublicUser getOtherPlayer() {
                return Erica;
            }

            @Override
            public void setOtherPlayer(PublicUser user) {

            }

            @Override
            public Moves getMoves() {
                return null;
            }

            @Override
            public void setMoves(Moves newMoves) {

            }

            @Override
            public void addMove(Move newMove) {

            }

            @Override
            public PublicUser getWinner() {
                return null;
            }

            @Override
            public void setWinner(PublicUser user) {

            }

            @Override
            public String getGameID() {
                return id;
            }

            @Override
            public void setGameID(String newGameID) {
                id = newGameID;
            }

            @Override
            public Board getGameBoard() {
                return null;
            }

            @Override
            public void setGameBoard(Board newBoard) {

            }

            @Override
            public List<PublicUser> viewers() {
                return viewers;
            }

            @Override
            public void addViewer(PublicUser user) {

            }

            @Override
            public boolean playMove(Move move) {
                return false;
            }
        };

        game.setGameID("XX");

        String error;

        try
        {
            db.createNewGame(game);
            error = "didnt fail";
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            error = "Failed";
        }

        assertEquals("didnt fail", error);

    }

    @Test
    void updateGame()
    {
        TTTPublicUser Erica = new TTTPublicUser("3","Erica3");
        TTTPublicUser Isaac = new TTTPublicUser("4","Isaac4");

        List<PublicUser> viewers = new ArrayList<PublicUser>();


        DBManager db = SQLDatabase.getInstance();

        Game game = new Game() {

            String id;
            String endTime = null;
            PublicUser winner = null;

            @Override
            public void setToken(Token token, PublicUser user) {

            }

            @Override
            public Token getToken(PublicUser user) {
                return null;
            }

            @Override
            public String getStartTime() {
                return "3:20 AM";
            }

            @Override
            public void setStartTime(LocalDateTime newTime) {

            }

            @Override
            public void setEndTime(LocalDateTime endTime) {
                this.endTime = endTime.toString();
            }

            @Override
            public String getEndTime() {
                return endTime;
            }

            @Override
            public PublicUser getStartPlayer() {
                return Isaac;
            }

            @Override
            public void setStartPlayer(PublicUser user) {

            }

            @Override
            public void setCreator(PublicUser user) {

            }

            @Override
            public PublicUser getCreator() {
                return Isaac;
            }

            @Override
            public PublicUser getOtherPlayer() {
                return Erica;
            }

            @Override
            public void setOtherPlayer(PublicUser user) {

            }

            @Override
            public Moves getMoves() {
                return null;
            }

            @Override
            public void setMoves(Moves newMoves) {

            }

            @Override
            public void addMove(Move newMove) {

            }

            @Override
            public PublicUser getWinner() {
                return winner;
            }

            @Override
            public void setWinner(PublicUser user) {
                winner = user;
            }

            @Override
            public String getGameID() {
                return id;
            }

            @Override
            public void setGameID(String newGameID) {
                id = newGameID;
            }

            @Override
            public Board getGameBoard() {
                return null;
            }

            @Override
            public void setGameBoard(Board newBoard) {

            }

            @Override
            public List<PublicUser> viewers() {
                return viewers;
            }

            @Override
            public void addViewer(PublicUser user) {

            }

            @Override
            public boolean playMove(Move move) {
                return false;
            }
        };
        
        game.setGameID("XX");
        game.setEndTime(LocalDateTime.now());
        game.setWinner(Isaac);

        String error;

        try
        {
            db.updateGameInfo(game);
            error = "didnt fail";
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            error = "Failed";
        }

        assertEquals("didnt fail", error);

    }

    @Test
    void addViewerToGame()
    {

        TTTPublicUser Jeff = new TTTPublicUser("5","Jeff50");
        TTTPublicUser John = new TTTPublicUser("1","John1");
        TTTPublicUser Smith = new TTTPublicUser("2","Smith2");

        DBManager db = SQLDatabase.getInstance();

        String error;

        try
        {
            db.addViewerToGame("XX", Smith);
            error = "didnt fail";
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            error = "Failed";
        }

        assertEquals("didnt fail", error);

    }

    @Test
    void addMoveToGame()
    {

        TTTPublicUser John = new TTTPublicUser("1","John1");

        Move move = new Move() {

            String id;
            Coordinate cords = new Coordinate() {
                int x;
                int y;

                @Override
                public int getXCoord() {
                    return x;
                }

                @Override
                public void setXCoord(int newXCoord) {
                    x = newXCoord;
                }

                @Override
                public int getYCoord() {
                    return y;
                }

                @Override
                public void setYCoord(int newYCoord) {
                    y = newYCoord;
                }
            };


            @Override
            public String getGameID() {
                return id;
            }

            @Override
            public void setGameID(String newGameID) {
                id = newGameID;
            }

            @Override
            public String getStartTime() {
                return "4:00PM";
            }

            @Override
            public void setStartTime(LocalDateTime time) {

            }

            @Override
            public String getPlayerID() {
                return John.getId();
            }

            @Override
            public void setPlayerID(String newPlayerID) {

            }

            @Override
            public Coordinate getCoordinate() {
                return cords;
            }

            @Override
            public void setCoordinate(Coordinate newCoordinate) {

            }
        };

        move.setGameID("XX");

        Token token = new Token() {
            @Override
            public String getTokenID() {
                return "XX";
            }
        };

        move.getCoordinate().setXCoord(2);
        move.getCoordinate().setYCoord(1);

        DBManager db = SQLDatabase.getInstance();

        String error;

        try
        {
            db.addMoveToGame(move);
            error = "didnt fail";
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            error = "Failed";
        }

        assertEquals("didnt fail", error);

    }

    @Test
    void getGame()
    {
        DBManager db = SQLDatabase.getInstance();
        String error;

        try
        {
            Game game = db.getGameInfo("XX");

            System.out.println("Game id: " + game.getGameID() + " Creator: " + game.getCreator().getUsername() + " Time: " + game.getStartTime());

            for(int i = 0; i < game.viewers().size(); i++)
            {
                System.out.println("Viewer " + (i+1) + ": " + game.viewers().get(i).getUsername());
            }

            error = "didnt fail";
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            error = "Failed";
        }

        assertEquals("didnt fail", error);

    }

    @Test
    void getGamesForPlayer()
    {
        DBManager db = SQLDatabase.getInstance();
        String error;

        try
        {
            List<Game> games = db.getGamesOfPlayer(1);

            System.out.println(games.size());

            error = "didnt fail";
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            error = "Failed";
        }

        assertEquals("didnt fail", error);

    }

    @Test
    void getGamesForPlayerWhereWinner()
    {
        DBManager db = SQLDatabase.getInstance();
        String error;

        try
        {
            List<Game> games = db.getGamesOfPlayerWhereWinner(1);

            System.out.println(games.size());

            error = "didnt fail";
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            error = "Failed";
        }

        assertEquals("didnt fail", error);

    }

    @Test
    void getGamesForPlayerWhereViewer()
    {
        DBManager db = SQLDatabase.getInstance();
        String error;

        try
        {
            List<Game> games = db.getGamesOfPlayerWhereViewer(5);

            System.out.println(games.size());

            error = "didnt fail";

            System.out.println(games.get(0).getGameID());
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            error = "Failed";
        }

        assertEquals("didnt fail", error);
    }

    @Test
    void getGamesForPlayerWhereCreator()
    {
        DBManager db = SQLDatabase.getInstance();
        String error;

        try
        {
            List<Game> games = db.getGamesOfPlayerWhereCreator(5);

            System.out.println(games.size());

            error = "didnt fail";

            System.out.println(games.get(0).getGameID());
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            error = "Failed";
        }

        assertEquals("didnt fail", error);
    }

    @Test
    void getGamesForPlayerWhereStartPlayer()
    {
        DBManager db = SQLDatabase.getInstance();
        String error;

        try
        {
            List<Game> games = db.getGamesOfPlayerWhereStartPlayer(5);

            System.out.println(games.size());

            error = "didnt fail";

            System.out.println(games.get(0).getGameID());
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            error = "Failed";
        }

        assertEquals("didnt fail", error);
    }

    @Test
    void getGamesForPlayerWhereOtherPlayer()
    {
        DBManager db = SQLDatabase.getInstance();
        String error;

        try
        {
            List<Game> games = db.getGamesOfPlayerWhereOtherPlayer(5);

            System.out.println(games.size());

            error = "didnt fail";

            System.out.println(games.get(0).getGameID());
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            error = "Failed";
        }

        assertEquals("didnt fail", error);
    }

    @Test
    void getMovesForGame()
    {
        DBManager db = SQLDatabase.getInstance();
        String error;

        try
        {
            List<Game> games = db.getGamesOfPlayerWhereViewer(5);

            Moves moves = db.getMovesOfGame(games.get(0).getGameID());

            System.out.println(moves.getMoves().size());

            error = "didnt fail";
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            error = "Failed";
        }

        assertEquals("didnt fail", error);
    }
}