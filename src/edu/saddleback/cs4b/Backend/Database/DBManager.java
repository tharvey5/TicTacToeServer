package edu.saddleback.cs4b.Backend.Database;

import edu.saddleback.cs4b.Backend.Objects.User;

import java.sql.*;

public class DBManager
{
    private static DBManager instance;
    private Connection connection = null;

    private int uniqueID  = 1;
    private int username  = 2;
    private int password  = 3;
    private int firstName = 4;
    private int lastName  = 5;
    private int status    = 6;


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

        return rs.getString(this.username);
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

            User user = new User(rs.getString(this.username), rs.getString(this.password), rs.getString(firstName), rs.getString(lastName));

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


    public int addUser(User user) throws Exception
    {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try
        {
            ps = connection.prepareStatement("INSERT INTO USER(Username, Password, FirstName, LastName, Status) VALUES(?,?,?,?,?)");
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFirstName());
            ps.setString(4, user.getLastName());
            ps.setString(5, "Active");

            rs = ps.executeQuery();

            return this.getUniqueID(user);
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            throw new Exception("User name was not unique");
        }

    }

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

    public boolean updateUser(int id, User user)
    {



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
