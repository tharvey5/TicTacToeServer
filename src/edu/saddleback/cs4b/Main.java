package edu.saddleback.cs4b;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.SQLException;

public class Main extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("UI/ServerScreen.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) throws SQLException {
        //User myUser = new User("zerohezitation", "yourMom", "Jeffrey", "Adams");
        //DBManager.getInstance().addUser(myUser);
        //DBManager.getInstance().getUsername(1);
        launch(args);

    }
}
