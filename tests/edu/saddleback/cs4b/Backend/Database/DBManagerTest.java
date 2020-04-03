package edu.saddleback.cs4b.Backend.Database;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DBManagerTest
{
    @Test
    @DisplayName("Test if database has username")
    void getUsername() throws SQLException {
        assertEquals("zerohezitation", DBManager.getInstance().getUsername(1));
    }
}