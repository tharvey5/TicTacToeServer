package edu.saddleback.cs4b.Backend.Database;

import edu.saddleback.cs4b.Backend.Objects.User;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

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

        User user = new User(username, password, firstName, lastName);
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

        User user = new User("JEFF", "1235", "Isaac", "Estrada");
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
}