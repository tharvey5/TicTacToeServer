package edu.saddleback.cs4b.Backend.Database;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class SQLDatabaseTest {

    @Test
    void getUsername() throws SQLException
    {
        DatabaseManager db = SQLDatabase.getInstance();

        assertEquals("Boxhead", db.getUsername(3));
    }

    @Test
    void login()
    {

    }

    @Test
    void getUniqueID()
    {

    }

    @Test
    void addUser()
    {

    }

    @Test
    void inactivateUser()
    {

    }

    @Test
    void activateUser()
    {

    }

    @Test
    void getUsersStatus()
    {

    }

    @Test
    void updateUser()
    {

    }
}