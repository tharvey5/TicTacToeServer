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

        try
        {
            DBManager.getInstance().addUser(user);
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


}