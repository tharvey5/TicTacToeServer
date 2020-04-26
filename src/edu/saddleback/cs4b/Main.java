package edu.saddleback.cs4b;

import edu.saddleback.cs4b.Backend.Server.ConnectionService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("UI/ServerScreen.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        new Thread(()-> {
            ConnectionService svc = ConnectionService.getInstance();
            svc.start();
        }).start();

    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
