package edu.saddleback.cs4b.Backend.Database;

import edu.saddleback.cs4b.Backend.Objects.Game;
import edu.saddleback.cs4b.Backend.Objects.Moves;
import edu.saddleback.cs4b.Backend.Utilitys.TTTUser;
import edu.saddleback.cs4b.Backend.Utilitys.User;

import java.sql.*;
import java.util.List;

public class SQLDatabase implements DBManager {
    private static SQLDatabase instance;
    private Connection connection = null;

    private int uniqueID  = 1;
    private int username  = 2;
    private int password  = 3;
    private int firstName = 4;
    private int lastName  = 5;
    private int status    = 6;

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

    //Will return a User if user is found, will throw an exception if no person is found

    @Override
    public User Login(String username, String password) throws Exception
    {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try
        {
            ps = connection.prepareStatement("Select * FROM USER WHERE Username = ? AND Password = ?");
            ps.setString(1, username);
            ps.setString(2, password);

            rs = ps.executeQuery();

            User user = new TTTUser(rs.getString(this.username), rs.getString(this.firstName), rs.getString(this.lastName), rs.getString(this.password));

            return user;
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            throw new Exception("incorrect Username or Password");
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
            ps = connection.prepareStatement("INSERT INTO USER(Username, Password, FirstName, LastName, Status) VALUES(?,?,?,?,?)");
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFirstName());
            ps.setString(4, user.getLastName());
            ps.setString(5, "Active");

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
    public void createNewGame(Game game) throws Exception
    {
        PreparedStatement ps = null;
        ResultSet rs = null;

        int counter = 0;

        int j;

        StringBuilder viewersString = new StringBuilder();


        for(j = 0; j < game.viewers().size(); j++ )
        {
            viewersString.append(game.viewers().get(j).getId());

            if((j + 1) != game.viewers().size())
            {
                viewersString.append(",");
            }

        }

        try
        {
            ps = connection.prepareStatement("INSERT INTO GAMES(UniqueID, Creator, Winner, Player1, Player2, Viewers, StartTime, EndTime) VALUES(?,?,?,?,?,?,?,?)");

            ps.setInt(1, Integer.parseInt(game.getGameID()));
            ps.setInt(2, Integer.parseInt(game.getCreator().getId()));
            ps.setInt(3, Integer.parseInt(game.getWinner().getId()));
            ps.setInt(4, Integer.parseInt(game.getStartPlayer().getId()));
            ps.setInt(5, Integer.parseInt(game.getOtherPlayer().getId()));
            ps.setString(6, viewersString.toString());
            ps.setString(7, game.getStartTime());
            ps.setString(8, game.getEndTime());

            counter = ps.executeUpdate();
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
        PreparedStatement ps = null;
        ResultSet rs = null;

        int counter = 0;

        int j;

        StringBuilder viewersString = new StringBuilder();

        for(j = 0; j < game.viewers().size(); j++ )
        {
            viewersString.append(game.viewers().get(j).getId());

            if((j + 1) != game.viewers().size())
            {
                viewersString.append(",");
            }
        }

        try
        {

            ps = connection.prepareStatement("UPDATE GAMES SET Creator = ? , Winner = ?, Player1 = ?, Player2 = ? , Viewers = ?, StartTime = ?, EndTime = ? WHERE UniqueID = ?");

            ps.setInt(1, Integer.parseInt(game.getCreator().getId()));
            ps.setInt(2, Integer.parseInt(game.getWinner().getId()));
            ps.setInt(3, Integer.parseInt(game.getStartPlayer().getId()));
            ps.setInt(4, Integer.parseInt(game.getOtherPlayer().getId()));
            ps.setString(5, viewersString.toString());
            ps.setString(6, game.getStartTime());
            ps.setString(7, game.getEndTime());
            ps.setInt(8, Integer.parseInt(game.getGameID()));

            counter = ps.executeUpdate();
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            throw new Exception(e.toString());
        }
    }

    @Override
    public void addViewerToGame(int id) throws Exception {

    }

    @Override
    public void addMovesToGame(Moves moves) throws Exception {

    }

    @Override
    public Game getGameInfo(int id) throws Exception {
        return null;
    }

    @Override
    public List<Game> getGamesOfPlayer(int id) throws Exception {
        return null;
    }

    @Override
    public Moves getMovesOfGame(int id) throws Exception {
        return null;
    }

//    public List<User> getAllUsers()
//    {
//
//    }
//
//    public List<User> getUsers(String filter)
//    {
//        List<User> users = new ArrayList<>();
//    }
}
