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
        assertEquals("Boxhead", DBManager.getInstance().getUsername(1));
    }

    @Test
    @DisplayName("Test if database has username")
    void addUser() throws SQLException
    {
        User user = new User("Boxhead", "1234", "Isaac", "Estrada");

        DBManager.getInstance().addUser(user);

        assertEquals("Boxhead", DBManager.getInstance().getUsername(2));
    }

    @Test
    @DisplayName("Test if Log in will find person")
    void testLogin() throws SQLException
    {
        User user;

        user = DBManager.getInstance().Login("Boxhead", "1234");

        assertEquals("Boxhead", user.getUsername());
    }


}