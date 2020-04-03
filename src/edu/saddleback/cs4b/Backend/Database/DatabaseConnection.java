package edu.saddleback.cs4b.UI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection
{
    public static Connection connect()
    {
        Connection con = null;
        try
        {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:TicTacToeDatabase.db"); // connecting to our database
            System.out.println("Connected!");
        }
        catch (ClassNotFoundException | SQLException e )
        {
            // TODO Auto-generated catch block
            System.out.println(e+"");
        }

        return con;
    }
}
