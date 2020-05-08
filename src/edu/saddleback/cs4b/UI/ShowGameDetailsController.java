package edu.saddleback.cs4b.UI;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class ShowGameDetailsController
{
    @FXML
    Button refreshButton;

    @FXML
    Button showCompletedGamesBtn;

    @FXML
    public void handleShowCompletedGamesAction() throws IOException
    {
        swapShowCompletedGames("/edu/saddleback/cs4b/UI/CompletedGames.fxml", showCompletedGamesBtn);
    }

    /**
     * WHEN THIS METHOD IS CALLED THE 'REFRESH' BUTTON WILL CHANGE COLOR WHEN THE MOUSE IS HOVERING OVER IT
     */
    @FXML
    public void highlightRefresh()
    {
        refreshButton.setOnMouseEntered(mouseEvent -> refreshButton.setTextFill(Color.valueOf("#FFD700")));
    }

    /**
     * WHEN THIS METHOD IS CALLED THE 'REFRESH' BUTTON WILL CHANGE BACK TO THE DEFAULT TEXT COLOR WHEN THE MOUSE IS
     * NO LONGER HOVERING OVER IT
     */
    @FXML
    public void resetRefresh()
    {
        refreshButton.setOnMouseExited(mouseEvent -> refreshButton.setTextFill(Color.BLACK));
    }

    /**
     * WHEN THIS METHOD IS CALLED THE 'SHOW COMPLETED GAMES' BUTTON WILL CHANGE COLOR WHEN THE MOUSE IS HOVERING OVER IT
     */
    @FXML
    public void highlightShowCompletedGames()
    {
        showCompletedGamesBtn.setOnMouseEntered(mouseEvent -> showCompletedGamesBtn.setTextFill(Color.valueOf("#FFD700")));
    }

    /**
     * WHEN THIS METHOD IS CALLED THE 'SHOW COMPLETED GAMES' BUTTON WILL CHANGE COLOR WHEN THE MOUSE IS HOVERING OVER IT
     */
    @FXML
    public void resetShowCompletedGames()
    {
        showCompletedGamesBtn.setOnMouseExited(mouseEvent -> showCompletedGamesBtn.setTextFill(Color.BLACK));
    }

    public void swapShowCompletedGames(String sceneLocation, Button button) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(sceneLocation));
        Parent root = loader.load();
        Scene scene  = new Scene(root);
        Stage window = (Stage)(button).getScene().getWindow();

        ServerController crtl = loader.getController();
        crtl.handleShowCompletedGamesAction();

        Platform.runLater(()->
        {
            window.setScene(scene);
            window.show();
        });
    }
}
