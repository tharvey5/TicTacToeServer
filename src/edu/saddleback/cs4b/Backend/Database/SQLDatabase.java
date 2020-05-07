package edu.saddleback.cs4b.Backend.Database;

import edu.saddleback.cs4b.Backend.Objects.*;
import edu.saddleback.cs4b.Backend.Utilitys.PublicUser;
import edu.saddleback.cs4b.Backend.Utilitys.TTTPublicUser;
import edu.saddleback.cs4b.Backend.Utilitys.TTTUser;
import edu.saddleback.cs4b.Backend.Utilitys.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLDatabase implements DBManager {
    private static SQLDatabase instance;
    private Connection connection = null;

    private int uniqueID  = 1;

    //Users
    private int username  = 2;
    private int password  = 3;
    private int firstName = 4;
    private int lastName  = 5;
    private int status    = 6;
    private int wins      = 7;
    private int losses    = 8;
    private int totalGames= 9;

    //Games
    private int creator  = 2;
    private int winner   = 3;
    private int player1  = 4;
    private int player2  = 5;
    private int viewers  = 6;
    private int startTime  = 7;
    private int endTime  = 8;

    //Moves
    private int gameID  = 2;
    private int player  = 3;
    private int x       = 4;
    private int y       = 5;
    private int time    = 6;


    public static SQLDatabase getInstance()
    {
        if (SQLDatabase.instance == null)
        {
            SQLDatabase.instance = new SQLDatabase();
        }

        return SQLDatabase.instance;
    }



    private Connection getConnection()
    {
        try
        {
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection("jdbc:sqlite:TicTacToeDatabase.db"); // connecting to our database
            System.out.println("Connected!");
        }
        catch (ClassNotFoundException | SQLException e )
        {
            // TODO Auto-generated catch block
            System.out.println(e.toString());
        }

        return connection;
    }

    private SQLDatabase()
    {
        this.getConnection();
    }

    @Override
    public String getUsername(int id) throws SQLException
    {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try
        {
            ps = connection.prepareStatement("SELECT * FROM USER WHERE UniqueID=?");
            ps.setInt(1, id);

            rs = ps.executeQuery();


        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
//        finally
//        {
//            if (ps!=null)
//            {
//                ps.close();
//            }
//            if(connection!=null)
//            {
//                connection.close();
//            }
//        }

        return rs.getString(this.username);
    }

    @Override
    public User getUser(int id) throws SQLException
    {
        PreparedStatement ps = null;
        ResultSet rs = null;

        String username;
        String fn;
        String ln;
        String password;

        TTTUser user = null;

        try
        {
            ps = connection.prepareStatement("SELECT * FROM USER WHERE UniqueID=?");
            ps.setInt(1, id);

            rs = ps.executeQuery();

            username = rs.getString(this.username);
            fn = rs.getString(this.firstName);
            ln = rs.getString(this.lastName);
            password = rs.getString(this.password);

            user = new TTTUser(username,fn, ln, password);

        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }

        return user;
    }

    //Will return a User if user is found, will throw an exception if no person is found

    @Override
    public User Login(String username, String password) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement("Select * FROM USER WHERE Username = ? AND Password = ?");
            ps.setString(1, username);
            ps.setString(2, password);

            rs = ps.executeQuery();

            if (rs.getString(this.status).equals("Inactive"))
            {
                throw new Exception("User is Inactive");
            }

            User user = new TTTUser(rs.getString(this.username), rs.getString(this.firstName), rs.getString(this.lastName), rs.getString(this.password));

            return user;
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
            throw new Exception("incorrect Username or Password or user is Inactive");
        }
    }

    @Override
    public int getUniqueID(User user) throws Exception
    {
        PreparedStatement ps = null;
        ResultSet rs = null;

        int uniqueID;

        try
        {
            ps = connection.prepareStatement("Select * FROM USER WHERE Username = ? AND Password = ? AND FirstName = ? AND LastName = ?");
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFirstName());
            ps.setString(4, user.getLastName());

            rs = ps.executeQuery();

            return rs.getInt(this.uniqueID);
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            throw new Exception("User Does not exist");
        }

    }

    @Override
    public int addUser(User user) throws Exception
    {
        PreparedStatement ps = null;
        ResultSet rs = null;

        int counter = 0;

        try
        {
            ps = connection.prepareStatement("INSERT INTO USER(Username, Password, FirstName, LastName, Status, Wins, Losses, TotalGames) VALUES(?,?,?,?,?,?,?,?)");
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFirstName());
            ps.setString(4, user.getLastName());
            ps.setString(5, "Active");
            ps.setInt(6, 0);
            ps.setInt(7, 0);
            ps.setInt(8, 0);


            counter = ps.executeUpdate();

            return this.getUniqueID(user);
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            throw new Exception("User name was not unique");
        }

    }

    @Override
    public void inactivateUser(int id) throws Exception {
        PreparedStatement ps = null;

        try
        {
            ps = connection.prepareStatement("UPDATE USER SET Status = ? WHERE UniqueID = ?");
            ps.setString(1, "Inactive");
            ps.setInt(2, id);
            ps.executeUpdate();
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            throw new Exception("No User found with id: " + String.valueOf(id));
        }
    }

    @Override
    public void activateUser(int id) throws Exception {
        PreparedStatement ps = null;

        try
        {
            ps = connection.prepareStatement("UPDATE USER SET Status = ? WHERE UniqueID = ?");
            ps.setString(1, "Active");
            ps.setInt(2, id);
            ps.executeUpdate();
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            throw new Exception("No User found with id: " + String.valueOf(id));
        }
    }

    @Override
    public String getUsersStatus(int id) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try
        {
            ps = connection.prepareStatement("Select * FROM USER WHERE UniqueID = ?");
            ps.setInt(1, id);

            rs = ps.executeQuery();
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            throw new Exception("No User found with id: " + String.valueOf(id));
        }

        return rs.getString(this.status);
    }

    @Override
    public void updateUser(int id, User user) throws Exception {

        PreparedStatement ps = null;
        ResultSet rs = null;

        try
        {
            ps = connection.prepareStatement("UPDATE USER SET Username = ?, Password = ?, FirstName = ?, LastName = ? WHERE UniqueID = ?");
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFirstName());
            ps.setString(4, user.getLastName());
            ps.setInt(5, id);

            ps.executeUpdate();
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            throw new Exception("Was not a unique username");
        }
    }

    @Override
    public int getUserWins(int id) throws Exception
    {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try
        {
            ps = connection.prepareStatement("Select * FROM USER WHERE UniqueID = ?");
            ps.setInt(1, id);

            rs = ps.executeQuery();
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            throw new Exception("No User found with id: " + String.valueOf(id));
        }

        return rs.getInt(this.wins);
    }

    @Override
    public int getUserLosses(int id) throws Exception
    {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try
        {
            ps = connection.prepareStatement("Select * FROM USER WHERE UniqueID = ?");
            ps.setInt(1, id);

            rs = ps.executeQuery();
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            throw new Exception("No User found with id: " + String.valueOf(id));
        }

        return rs.getInt(this.losses);
    }

    @Override
    public int getUserTotalGames(int id) throws Exception
    {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try
        {
            ps = connection.prepareStatement("Select * FROM USER WHERE UniqueID = ?");
            ps.setInt(1, id);

            rs = ps.executeQuery();
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            throw new Exception("No User found with id: " + String.valueOf(id));
        }

        return rs.getInt(this.totalGames);
    }


    private void addWin(int id) throws Exception
    {
        PreparedStatement ps = null;
        ResultSet rs = null;

        int totalWins;

        try
        {
            totalWins = instance.getUserWins(id);

            totalWins++;

            ps = connection.prepareStatement("UPDATE USER SET Wins = ? WHERE UniqueID = ?");
            ps.setInt(1, totalWins);
            ps.setInt(2, id);

            ps.executeUpdate();
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            throw new Exception("No User found with id: " + String.valueOf(id));
        }
    }


    private void addLoss(int id) throws Exception
    {
        PreparedStatement ps = null;
        ResultSet rs = null;

        int totalLosses;

        try
        {
            totalLosses = instance.getUserLosses(id);

            totalLosses++;

            ps = connection.prepareStatement("UPDATE USER SET Losses = ? WHERE UniqueID = ?");
            ps.setInt(1, totalLosses);
            ps.setInt(2, id);

            ps.executeUpdate();
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            throw new Exception("No User found with id: " + String.valueOf(id));
        }
    }


    private void addTotalGame(int id) throws Exception
    {
        PreparedStatement ps = null;
        ResultSet rs = null;

        int totalGames;

        try
        {
            System.out.println(id);

            totalGames = instance.getUserTotalGames(id);

            totalGames++;

            ps = connection.prepareStatement("UPDATE USER SET TotalGames = ? WHERE UniqueID = ?");
            ps.setInt(1, totalGames);
            ps.setInt(2, id);

            ps.executeUpdate();
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            throw new Exception("No User found with id: " + String.valueOf(id));
        }
    }



    @Override
    public void createNewGame(Game game) throws Exception
    {

        //Add Games to the player including who won and who loss


        if(game.getWinner() != null || game.getEndTime() != null)
        {
            instance.addTotalGame(Integer.parseInt(game.getStartPlayer().getId()));
            instance.addTotalGame(Integer.parseInt(game.getOtherPlayer().getId()));
            instance.addWin(Integer.parseInt(game.getWinner().getId()));

            if(game.getWinner().getId() == game.getStartPlayer().getId())
            {
                instance.addLoss(Integer.parseInt(game.getOtherPlayer().getId()));
            }
            else
            {
                instance.addLoss(Integer.parseInt(game.getStartPlayer().getId()));
            }
        }
        PreparedStatement ps = null;
        ResultSet rs = null;

        int counter = 0;

        int j;

        try
        {

            if(game.getWinner() != null || game.getEndTime() != null) {
                ps = connection.prepareStatement("INSERT INTO GAMES(UniqueID, Creator, Winner, Player1, Player2, Viewers, StartTime, EndTime) VALUES(?,?,?,?,?,?,?,?)");

                ps.setString(1, game.getGameID());
                ps.setInt(2, Integer.parseInt(game.getCreator().getId()));
                ps.setInt(3, Integer.parseInt(game.getWinner().getId()));
                ps.setInt(4, Integer.parseInt(game.getStartPlayer().getId()));
                ps.setInt(5, Integer.parseInt(game.getOtherPlayer().getId()));
                ps.setString(6, ",");
                ps.setString(7, game.getStartTime());
                ps.setString(8, game.getEndTime());

                counter = ps.executeUpdate();
            }
            else if (game.getWinner() != null)
            {
                ps = connection.prepareStatement("INSERT INTO GAMES(UniqueID, Creator, Winner, Player1, Player2, Viewers, StartTime, EndTime) VALUES(?,?,?,?,?,?,?,?)");

                ps.setString(1, game.getGameID());
                ps.setInt(2, Integer.parseInt(game.getCreator().getId()));
                ps.setInt(3, -1);
                ps.setInt(4, Integer.parseInt(game.getStartPlayer().getId()));
                ps.setInt(5, Integer.parseInt(game.getOtherPlayer().getId()));
                ps.setString(6, ",");
                ps.setString(7, game.getStartTime());
                ps.setString(8, game.getEndTime());

                counter = ps.executeUpdate();
            }
            else{
                ps = connection.prepareStatement("INSERT INTO GAMES(UniqueID, Creator, Winner, Player1, Player2, Viewers, StartTime, EndTime) VALUES(?,?,?,?,?,?,?,?)");

                ps.setString(1, game.getGameID());
                ps.setInt(2, Integer.parseInt(game.getCreator().getId()));
                ps.setInt(3, -1);
                ps.setInt(4, Integer.parseInt(game.getStartPlayer().getId()));
                ps.setInt(5, Integer.parseInt(game.getOtherPlayer().getId()));
                ps.setString(6, ",");
                ps.setString(7, game.getStartTime());
                ps.setString(8, "");

                counter = ps.executeUpdate();
            }
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            throw new Exception(e.toString());
        }
    }

    @Override
    public void updateGameInfo(Game game) throws Exception
    {

        //Add Games to the player including who won and who loss
        instance.addTotalGame(Integer.parseInt(game.getStartPlayer().getId()));

        instance.addTotalGame(Integer.parseInt(game.getOtherPlayer().getId()));

        if(game.getWinner() != null)
        {
            instance.addWin(Integer.parseInt(game.getWinner().getId()));

            if(game.getWinner().getId() == game.getStartPlayer().getId())
            {
                instance.addLoss(Integer.parseInt(game.getOtherPlayer().getId()));
            }
            else
            {
                instance.addLoss(Integer.parseInt(game.getStartPlayer().getId()));
            }
        }


        PreparedStatement ps = null;
        ResultSet rs = null;

        int counter = 0;

        int j;


        try
        {
            ps = connection.prepareStatement("UPDATE GAMES SET Creator = ? , Winner = ?, Player1 = ?, Player2 = ? , StartTime = ?, EndTime = ? WHERE UniqueID = ?");

            ps.setInt(1, Integer.parseInt(game.getCreator().getId()));
            ps.setInt(2, Integer.parseInt(game.getWinner().getId()));
            ps.setInt(3, Integer.parseInt(game.getStartPlayer().getId()));
            ps.setInt(4, Integer.parseInt(game.getOtherPlayer().getId()));
            ps.setString(5, game.getStartTime());
            ps.setString(6, game.getEndTime());
            ps.setString(7, game.getGameID());

            counter = ps.executeUpdate();
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            throw new Exception(e.toString());
        }
    }

    @Override
    public void addViewerToGame(String id, PublicUser viewer) throws Exception
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        int val;

        StringBuilder viewersString = new StringBuilder();

        try
        {
            viewersString.append(getCurrentViewers(id));

            viewersString.append(viewer.getId());

            viewersString.append(",");


            ps = connection.prepareStatement("UPDATE GAMES SET Viewers = ? WHERE UniqueID = ?");
            ps.setString(1, viewersString.toString());
            ps.setString(2, id);

            val = ps.executeUpdate();

        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            throw new Exception("1No User found with id: " + String.valueOf(id));
        }
    }

    private String getCurrentViewers(String id) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;


        try
        {
            ps = connection.prepareStatement("Select Viewers FROM GAMES WHERE UniqueID = ?");
            ps.setString(1, id);

            rs = ps.executeQuery();

            return rs.getString(1);

        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            throw new Exception("2No User found with id: " + String.valueOf(id));
        }
    }

    @Override
    public void addMoveToGame(Move move) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;

        int counter = 0;

        try {
            ps = connection.prepareStatement("INSERT INTO MOVES(GameID, Player, XLocation, YLocation, Time) VALUES(?,?,?,?,?)");

            ps.setString(1, move.getGameID());
            ps.setInt(2, Integer.parseInt(move.getPlayerID()));
            ps.setInt(3, move.getCoordinate().getXCoord());
            ps.setInt(4, move.getCoordinate().getYCoord());
            ps.setString(5, move.getStartTime());

            counter = ps.executeUpdate();
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
            throw new Exception(e.toString());
        }
    }

    @Override
    public Game getGameInfo(String id) throws Exception
    {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try
        {
            ps = connection.prepareStatement("Select * FROM GAMES WHERE UniqueID = ?");
            ps.setString(1, id);

            rs = ps.executeQuery();

            PublicUser startPlayer = new TTTPublicUser(String.valueOf(rs.getInt(this.player1)), instance.getUsername(rs.getInt(this.player1)));
            PublicUser otherPlayer = new TTTPublicUser(String.valueOf(rs.getInt(this.player2)), instance.getUsername(rs.getInt(this.player2)));
            PublicUser creator = new TTTPublicUser(String.valueOf(rs.getInt(this.creator)), instance.getUsername(rs.getInt(this.creator)));

            PublicUser dataWinner;

            if(rs.getInt(this.winner) == -1)
            {
                dataWinner = null;
            }
            else
            {
                dataWinner = new TTTPublicUser(String.valueOf(rs.getInt(this.winner)), instance.getUsername(rs.getInt(this.winner)));
            }

            List<PublicUser> viewers = new ArrayList<>();

            String delimiter = ",";

            String[] stringID = rs.getString(this.viewers).split(delimiter);

            for (int i = 1; i < stringID.length; i++)
            {
                PublicUser user = new TTTPublicUser(stringID[i], getInstance().getUsername(Integer.parseInt(stringID[i])));
                viewers.add(user);
            }

            String dataEndTime;

            if(rs.getString(this.endTime) == "")
            {
                dataEndTime = null;
            }
            else
            {
                dataEndTime = rs.getString(this.endTime);
            }

            Game game = new DatabaseGame(rs.getString(this.startTime), dataEndTime, startPlayer, otherPlayer, creator, dataWinner, String.valueOf(id), viewers );

            return game;
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            throw new Exception("No Game found with id: " + String.valueOf(id));
        }
    }

    @Override
    public List<Game> getGamesOfPlayer(int id) throws Exception {

        PreparedStatement ps = null;
        ResultSet rs = null;

        List<Game> games = new ArrayList<>();

        try
        {
            String viewerID = "%," + id + ",%";



            ps = connection.prepareStatement("Select * FROM GAMES WHERE Creator = ? OR Winner = ? OR Player1 = ? OR Player2 = ? OR (Viewers LIKE ?)");
            ps.setInt(1, id);
            ps.setInt(2, id);
            ps.setInt(3, id);
            ps.setInt(4, id);
            ps.setString(5, viewerID);


            rs = ps.executeQuery();

           while(rs.next())
           {
               games.add(instance.getGameInfo(rs.getString(this.uniqueID)));
           }

           return games;
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            throw new Exception("No Game(s) found for user with id: " + String.valueOf(id));
        }
    }

    @Override
    public List<Game> getGamesOfPlayerWhereCreator(int id) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<Game> games = new ArrayList<>();

        try
        {

            ps = connection.prepareStatement("Select * FROM GAMES WHERE Creator = ? ");
            ps.setInt(1, id);

            rs = ps.executeQuery();

            while(rs.next())
            {
                games.add(instance.getGameInfo(rs.getString(this.uniqueID)));
            }

            return games;
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            throw new Exception("No Game(s) found for user with id: " + String.valueOf(id));
        }
    }

    @Override
    public List<Game> getGamesOfPlayerWhereWinner(int id) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<Game> games = new ArrayList<>();

        try
        {

            ps = connection.prepareStatement("Select * FROM GAMES WHERE Winner = ?");
            ps.setInt(1, id);

            rs = ps.executeQuery();

            while(rs.next())
            {
                games.add(instance.getGameInfo(rs.getString(this.uniqueID)));
            }

            return games;
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            throw new Exception("No Game(s) found for user with id: " + String.valueOf(id));
        }
    }

    @Override
    public List<Game> getGamesOfPlayerWhereStartPlayer(int id) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<Game> games = new ArrayList<>();

        try
        {
            ps = connection.prepareStatement("Select * FROM GAMES WHERE Player1 = ?");
            ps.setInt(1, id);

            rs = ps.executeQuery();

            while(rs.next())
            {
                games.add(instance.getGameInfo(rs.getString(this.uniqueID)));
            }

            return games;
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            throw new Exception("No Game(s) found for user with id: " + String.valueOf(id));
        }
    }

    @Override
    public List<Game> getGamesOfPlayerWhereOtherPlayer(int id) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<Game> games = new ArrayList<>();

        try
        {

            ps = connection.prepareStatement("Select * FROM GAMES WHERE Player2 = ?");
            ps.setInt(1, id);

            rs = ps.executeQuery();

            while(rs.next())
            {
                games.add(instance.getGameInfo(rs.getString(this.uniqueID)));
            }

            return games;
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            throw new Exception("No Game(s) found for user with id: " + String.valueOf(id));
        }
    }

    @Override
    public List<Game> getGamesOfPlayerWhereViewer(int id) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<Game> games = new ArrayList<>();

        try
        {
            String viewerID = "%," + id + ",%";

            ps = connection.prepareStatement("Select * FROM GAMES WHERE (Viewers LIKE ?)");
            ps.setString(1, viewerID);

            rs = ps.executeQuery();

            while(rs.next())
            {
                games.add(instance.getGameInfo(rs.getString(this.uniqueID)));
            }

            return games;
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            throw new Exception("No Game(s) found for user with id: " + String.valueOf(id));
        }
    }

    @Override
    public Moves getMovesOfGame(String id) throws Exception
    {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try
        {
            ps = connection.prepareStatement("Select * FROM MOVES WHERE GameID = ?");
            ps.setString(1, id);

            rs = ps.executeQuery();

            List<Move> movesList = new ArrayList<>();

            while(rs.next())
            {
                Coordinate cord = new TTTPosition(rs.getInt(this.x), rs.getInt(this.y));

                Move move = new TTTMove(id, String.valueOf(rs.getInt(this.player)), cord);

                movesList.add(move);
            }

            return new DatabaseMoves(movesList);
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            throw new Exception("No Move(s) found for user with id: " + String.valueOf(id));
        }
    }

}
