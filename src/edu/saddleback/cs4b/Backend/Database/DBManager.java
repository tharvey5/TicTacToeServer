package edu.saddleback.cs4b.Backend.Database;

import edu.saddleback.cs4b.Backend.Objects.User;

import java.sql.*;

public class DBManager
{
    private static DBManager instance;
    private Connection connection = null;

    private DBManager()
    {
        this.getConnection();
    }

    public static DBManager getInstance()
    {
        if (instance == null)
        {
            instance = new DBManager();
        }

        return instance;
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

        return rs.getString(2);
    }

    //Will return a User if user is found, will throw an exception if no person is found

    public User Login(String username, String password ) throws Exception
    {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try
        {
            ps = connection.prepareStatement("Select * FROM USER WHERE Username = ? AND Password = ?");
            ps.setString(1, username);
            ps.setString(2, password);

            rs = ps.executeQuery();

            User user = new User(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));

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

    public int addUser(User user) throws Exception
    {
        PreparedStatement ps = null;
        ResultSet rs = null;

        int recordCounter = 0;

        try
        {
            ps = connection.prepareStatement("INSERT INTO USER(Username, Password, FirstName, LastName, Status) VALUES(?,?,?,?,?)");
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFirstName());
            ps.setString(4, user.getLastName());
            ps.setString(5, "Active");
            recordCounter = ps.executeUpdate();
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            throw new Exception("User name was not unique");
        }
//        finally
//        {
//            if (ps!=null)
//            {
//                ps.close();
//            }
//            if(connection!=null)
//            {
//            connection.close();
//            }
//        }

        return recordCounter;
    }

    public void deleteUser(String username)
    {
        //jdbc code


    }

    public void deleteUser(int userID)
    {
        //jdbc code


    }

    public boolean updateUser(User user)
    {
        //jdbc code

        return true;
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
