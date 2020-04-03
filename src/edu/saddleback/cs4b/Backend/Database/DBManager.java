package edu.saddleback.cs4b.Backend.Database;

import java.util.ArrayList;
import java.util.List;

public class DBManager
{
    private static DBManager instance = new DBManager();

    private DBManager()
    {

    }

    public static DBManager getInstance()
    {
        return instance;
    }

    public boolean addUser()
    {
        //jdbc code

        return true;
    }

    public void deleteUser(String username)
    {
        //jdbc code


    }

    public void deleteUser(int userID)
    {
        //jdbc code


    }

    public boolean updateUser()
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
