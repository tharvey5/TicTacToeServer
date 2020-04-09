package edu.saddleback.cs4b.Backend.Database;

import edu.saddleback.cs4b.Backend.Objects.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sqlite.core.DB;

import javax.sql.DataSource;
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
    void addUser() throws Exception
    {
        String username = "Boxhead2";
        String password = "1234";
        String firstName = "Isaac";
        String lastName = "Estrada";

        User user = new User(username, password, firstName, lastName);
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
        assertEquals(username, DBManager.getInstance().getUsername(DBManager.getInstance().getUniqueID(user)));
    }

    @Test
    @DisplayName("Test if Login will find person")
    void testLogin() throws SQLException
    {
        String username = "Boxhead2";
        String password = "1234";

        User user = null;

        String error;
        try
        {
            user = DBManager.getInstance().Login(username, password);
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
            assertEquals(username, DBManager.getInstance().getUsername(DBManager.getInstance().getUniqueID(user)));
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
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

        String username = "Boxhead";
        String password = "1234";
        String firstName = "Isaac";
        String lastName = "Estrada";

        int expectedId = 1;

        User user = new User(username, password, firstName, lastName);
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
        assertEquals(expectedId, id);
    }

    @Test
    @DisplayName("Test Get the Status")
    void testGetStatus() throws SQLException
    {
        String error;

        int id = 1;

        String expectedStatus = "Active";
        String status;

        try
        {
            status = DBManager.getInstance().getUsersStatus(id);
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
    @DisplayName("Test setting the user inactive")
    void testInactivateUser() throws SQLException
    {
        String error;
        int idOfInactivateUser = 1;

        String status;

        try
        {
            DBManager.getInstance().inactivateUser(idOfInactivateUser);
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
        assertEquals("Inactive", DBManager.getInstance().getUsersStatus(idOfInactivateUser));
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }

    @Test
    @DisplayName("Test setting the user Active")
    void testActivateUser() throws SQLException
    {
        String error;


        int idOfActivateUser = 2;

        String status;

        try
        {
            DBManager.getInstance().activateUser(idOfActivateUser);
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
            assertEquals("Active", DBManager.getInstance().getUsersStatus(idOfActivateUser));
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }

    @Test
    @DisplayName("Test updating user")
    void testUpdateUser() throws SQLException
    {

        DBManager ds = DBManager.getInstance();

        String error;

        User user = new User("JEFF", "1235", "Isaac", "Estrada");
        int idOfUpdatedUser = 1;

        String status;

        try
        {
            DBManager.getInstance().updateUser(idOfUpdatedUser, user);
            error = "didnt fail";
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            error = "failed";
        }

        assertEquals("didnt fail", error);

    }
}