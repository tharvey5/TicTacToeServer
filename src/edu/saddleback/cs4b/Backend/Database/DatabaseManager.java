package edu.saddleback.cs4b.Backend.Database;

import edu.saddleback.cs4b.Backend.Objects.User;

import java.sql.SQLException;

public interface DatabaseManager
{
    String getUsername(int id) throws SQLException;
    User Login(String username, String password) throws Exception;
    int getUniqueID(User user) throws Exception;
    int addUser(User user) throws Exception;
    void inactivateUser(int id) throws Exception;
    void activateUser(int id) throws Exception;
    String getUsersStatus(int id) throws Exception;
    void updateUser(int id, User user) throws Exception;
}
