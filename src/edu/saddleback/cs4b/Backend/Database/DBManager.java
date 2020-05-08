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
     getUsername: returns the user with the given unique id.

     - Will throw exception if the unique id is not found in the
     system.
     ****************************************************************/
    User getUser(int id) throws SQLException;

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

    /****************************************************************
     getUserWins: int, gets the total wins of of a the player with
     the give ID.

     - Will throw exception if the unique id is not found in the
     system.
     ****************************************************************/
    int getUserWins(int id) throws Exception;

    /****************************************************************
     getUserLosses: int, gets the total losses of a the player with
     the give ID.

     - Will throw exception if the unique id is not found in the
     system.
     ****************************************************************/
    int getUserLosses(int id) throws Exception;

    /****************************************************************
     getUserTotalGames: int, gets the total games of a the player
     with the give ID.

     - Will throw exception if the unique id is not found in the
     system.
     ****************************************************************/
    int getUserTotalGames(int id) throws Exception;

    /****************************************************************
     createNewGame: void, will create a row on the GAMES table for
     the give game object. YOU MUST have data for gameID, creator,
     startPlayer, otherPlayer, and startTime in your game object you
     pass to this function.

     - Will throw exception if the gameID is not unique, or if any of
     the MUST HAVE data above is missing.
     ****************************************************************/
    void createNewGame(Game game) throws Exception;

    /****************************************************************
     updateGameInfo: void, will update the row that was allocated by
     createNewGame, You must have have data for gameID, creator,
     startPlayer, otherPlayer, startTime, and endTime in your game
     object you pass to this function.

     - Will throw exception if the gameID is not unique, or if any of
     the MUST HAVE data above is missing.
     ****************************************************************/
    void updateGameInfo(Game game) throws Exception;

    /****************************************************************
     addViewerToGame: void, will add a viewer to the given gameID.
     You must have the correct ID number of the user who is watching
     saved in your publicUser object.

     - Will throw exception if the gameID is not found or if the ID
     number is not stored in PublicUser.
     ****************************************************************/
    void addViewerToGame(String id, PublicUser viewer) throws Exception;

    /****************************************************************
     addMoveToGame: void, will add a row to the MOVE table, it is
     important you have to correct gameID data saved in the move
     object you pass to this function.

     - Will throw exception if there is any null data in the Move
     object that is passed to this function.
     ****************************************************************/
    void addMoveToGame(Move move) throws Exception;

    /****************************************************************
     getGameInfo: Game, will return a dataBaseGame object with all
     the saved data for the game with the given GameID passed to
     this function.

     - Will throw exception if the given gameID does not match any on
     the saved database.
     ****************************************************************/
    Game getGameInfo(String id) throws Exception;

    /****************************************************************
     getGamesOfPlayer: List<Game>, will return an Array list of game
     where the player with the given ID was apart of. This includes
     the games they were the creator, winner, startPlayer,
     otherPLayer, and/or a Viewer.

     If the user was in no games then the list size will be zero.

     - Should not throw an error.
     ****************************************************************/
    List<Game> getGamesOfPlayer(int id) throws Exception;

    /****************************************************************
     getGamesOfPlayerWhereCreator: List<Game>, will return an Array
     list of game where the player with the given ID was only the
     creator.

     If the user was in not the creator for any games then the list
     size will be zero.

     - Should not throw an error.
     ****************************************************************/
    List<Game> getGamesOfPlayerWhereCreator(int id) throws Exception;

    /****************************************************************
     getGamesOfPlayerWhereWinner: List<Game>, will return an Array
     list of game where the player with the given ID was only the
     winner.

     If the user was in not the winner for any games then the list
     size will be zero.

     - Should not throw an error.
     ****************************************************************/
    List<Game> getGamesOfPlayerWhereWinner(int id) throws Exception;

    /****************************************************************
     getGamesOfPlayerWhereStartPlayer: List<Game>, will return an
     Array list of game where the player with the given ID was
     only the startPlayer.

     If the user was in not the startPlayer for any games then the
     list size will be zero.

     - Should not throw an error.
     ****************************************************************/
    List<Game> getGamesOfPlayerWhereStartPlayer(int id) throws Exception;

    /****************************************************************
     getGamesOfPlayerWhereOtherPlayer: List<Game>, will return an
     Array list of game where the player with the given ID was
     only the otherPlayer.

     If the user was in not the otherPlayer for any games then the
     list size will be zero.

     - Should not throw an error.
     ****************************************************************/
    List<Game> getGamesOfPlayerWhereOtherPlayer(int id) throws Exception;

    /****************************************************************
     getGamesOfPlayerWhereViewer: List<Game>, will return an Array
     list of game where the player with the given ID was only a
     viewer of a game.

     If the user was in not the viewer for any games then the
     list size will be zero.

     - Should not throw an error.
     ****************************************************************/
    List<Game> getGamesOfPlayerWhereViewer(int id) throws Exception;

    /****************************************************************
     getMovesOfGame: Moves, will return a Move object with all the
     moves made with in a signal game with the given gameID.

     If there was no moves for this game the array list inside the
     moves object will have a size of zero.

     - Should not throw an error.
     ****************************************************************/
    Moves getMovesOfGame(String id) throws Exception;

    List<Game> getAllCompletedGames() throws Exception;

    List<Game> getAllActiveGames() throws Exception;

    List<Game> getAllGames() throws Exception;


}
