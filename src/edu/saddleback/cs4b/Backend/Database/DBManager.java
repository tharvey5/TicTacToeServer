package edu.saddleback.cs4b.Backend.Database;

import edu.saddleback.cs4b.Backend.Objects.Game;
import edu.saddleback.cs4b.Backend.Objects.Move;
import edu.saddleback.cs4b.Backend.Objects.Moves;
import edu.saddleback.cs4b.Backend.Objects.Token;
import edu.saddleback.cs4b.Backend.Utilitys.PublicUser;
import edu.saddleback.cs4b.Backend.Utilitys.User;

import java.sql.SQLException;
import java.util.List;

public interface DBManager
{

    /****************************************************************
     getUsername: returns the username of the user with the given.
     unique id

     - Will throw exception if the unique id is not found in the
     system.
     ****************************************************************/
    String getUsername(int id) throws SQLException;

    /****************************************************************
     Login: returns found user with that information.

     - Will throw exception if username and password do not
     match any records.
     ****************************************************************/
    User Login(String username, String password) throws Exception;

    /****************************************************************
     getUniqueID: returns the unique id of the user given.

     - Will throw exception if not all information is correct ie
     wrong username, password, First name and/or last name.
     ****************************************************************/
    int getUniqueID(User user) throws Exception;

    /****************************************************************
     addUser: returns the unique id of the added user.

     - Will throw exception if username is not unique.
     ****************************************************************/
    int addUser(User user) throws Exception;

    /****************************************************************
     inactivateUser: void, inactivates the user w/ given unique id.

     - Will throw exception if the unique id is not found in the
     system.
     ****************************************************************/
    void inactivateUser(int id) throws Exception;

    /****************************************************************
     activateUser: void, activates the user w/ given unique id.

     - Will throw exception if the unique id is not found in the
     system.
     ****************************************************************/
    void activateUser(int id) throws Exception;

    /****************************************************************
     getUsersStatus: Returns a string of the status (Active/Inactive)
     of user w/ given unique id.

     - Will throw exception if the unique id is not found in the
     system.
     ****************************************************************/
    String getUsersStatus(int id) throws Exception;

    /****************************************************************
     updateUser: void, updates the information of user w/ given
     unique id, with all the information in the passed
     user object.

     - Will throw exception if the unique id is not found in the
     system, or the new username is not unique.
     ****************************************************************/
    void updateUser(int id, User user) throws Exception;

    void createNewGame(Game game) throws Exception;

    void updateGameInfo(Game game) throws Exception;

    void addViewerToGame(int id, PublicUser viewer) throws Exception;

    void addMovesToGame(Moves moves) throws Exception;

    void addMoveToGame(Move move, Token token) throws Exception;

    Game getGameInfo(int id) throws Exception;

    List<Game> getGamesOfPlayer(int id) throws Exception;

    Moves getMovesOfGame(int id) throws Exception;

}
