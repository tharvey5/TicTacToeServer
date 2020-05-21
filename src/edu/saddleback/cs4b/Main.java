package edu.saddleback.cs4b;

import edu.saddleback.cs4b.Backend.Server.ConnectionService;
import edu.saddleback.cs4b.UI.ServerController;
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UI/Server.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        ServerController crtl = loader.getController();
        crtl.handleEventLogAction();

        primaryStage.setScene(scene);
        primaryStage.show();

        new Thread(()->
        {
            ConnectionService svc = ConnectionService.getInstance();
            svc.start();
        }).start();

    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
