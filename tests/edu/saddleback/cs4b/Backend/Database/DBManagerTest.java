package edu.saddleback.cs4b.Backend.Database;

import edu.saddleback.cs4b.Backend.Objects.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DBManagerTest
{
    @Test
    @DisplayName("Test if database has username")
    void getUsername() throws SQLException
    {
        assertEquals("Boxhead", DBManager.getInstance().getUsername(2));
    }

    @Test
    @DisplayName("Test if database has username")
    void addUser() throws SQLException
    {
        User user = new User("Boxhead", "1234", "Isaac", "Estrada");
        String error;
        int id;

        try
        {
            id = DBManager.getInstance().addUser(user);
            error = "add user didnt fail";
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            error = "Username not unique";
        }

        assertEquals("add user didnt fail", error);
        assertEquals("Boxhead", DBManager.getInstance().getUsername(2));
    }

    @Test
    @DisplayName("Test if Login will find person")
    void testLogin() throws SQLException
    {
        User user;
        String error;
        try
        {
            user = DBManager.getInstance().Login("Boxhead", "1234");
            error = "Login didnt fail";
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            error = "Username or Password incorrect";
        }

        assertEquals("Login didnt fail", error);
    }

    @Test
    @DisplayName("Test if Bad Login will throw exception")
    void testBadLogin() throws SQLException
    {
        User user;
        String error;

        try
        {
            user = DBManager.getInstance().Login("Boxhead", "1235");
            error = "Login didnt fail";
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            error = "Username or Password incorrect";
        }

        assertEquals("Username or Password incorrect", error);
    }

    @Test
    @DisplayName("Test Get Unique ID")
    void testGetUniqueID() throws SQLException
    {
        String error;

        User user = new User("Boxhead", "1234", "Isaac", "Estrada");
        int id;

        try
        {
             id = DBManager.getInstance().getUniqueID(user);
            error = "didnt fail";
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            error = "failed";
            id = 0;
        }

        assertEquals("didnt fail", error);
        assertEquals(2, id);
    }

    @Test
    @DisplayName("Test Get the Status")
    void testGetStatus() throws SQLException
    {
        String error;

        User user = new User("Boxhead", "1234", "Isaac", "Estrada");
        int id;

        String status;

        try
        {
            status = DBManager.getInstance().getUsersStatus( DBManager.getInstance().getUniqueID(user));
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
        assertEquals("Inactive", status);
    }

    @Test
    @DisplayName("Test setting the user inactive")
    void testInactivateUser() throws SQLException
    {
        String error;

        User user = new User("Boxhead", "1234", "Isaac", "Estrada");
        int id;

        String status;

        try
        {
            DBManager.getInstance().inactivateUser(DBManager.getInstance().getUniqueID(user));
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
        try
        {
        assertEquals("Inactive", DBManager.getInstance().getUsersStatus(DBManager.getInstance().getUniqueID(user)));
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }
}