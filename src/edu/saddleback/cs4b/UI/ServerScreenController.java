package edu.saddleback.cs4b.UI;


import edu.saddleback.cs4b.Backend.PubSub.Observer;
import edu.saddleback.cs4b.Backend.PubSub.SystemEvent;
import edu.saddleback.cs4b.Backend.Server.Logger;
import edu.saddleback.cs4b.Backend.Server.ServerLogger;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class ServerScreenController implements Observer
{
    @FXML
    private Button eventLogButton;

    @FXML
    private Button activeGamesButton;

    @FXML
    private Button completedGamesButton;

    @FXML
    private Button playersButton;

    @FXML
    private BorderPane viewScreen;


    public ServerScreenController() {
        ServerLogger.getInstance().addObserver(this);
    }


    @Override
    public void update(SystemEvent e)
    {

    }


    /**
     * WHEN THIS METHOD IS CALLED THE 'EVENT LOG' BUTTON WILL CHANGE COLOR WHEN THE MOUSE IS HOVERING OVER IT
     */
    @FXML
    public void highlightEventLog()
    {
        eventLogButton.setOnMouseEntered(mouseEvent -> eventLogButton.setTextFill(Color.YELLOW));
    }

    /**
     * WHEN THIS METHOD IS CALLED THE 'EVENT LOG' BUTTON WILL CHANGE BACK TO THE DEFAULT TEXT COLOR WHEN THE MOUSE IS
     * NO LONGER HOVERING OVER IT
     */
    @FXML
    public void resetEventLog()
    {
        eventLogButton.setOnMouseExited(mouseEvent -> eventLogButton.setTextFill(Color.WHITE));
    }

    /**
     * WHEN THIS METHOD IS CALLED THE 'ACTIVE GAMES' BUTTON WILL CHANGE COLOR WHEN THE MOUSE IS HOVERING OVER IT
     */
    @FXML
    public void highlightActiveGames()
    {
        activeGamesButton.setOnMouseEntered(mouseEvent -> activeGamesButton.setTextFill(Color.YELLOW));
    }

    /**
     * WHEN THIS METHOD IS CALLED THE 'ACTIVE GAMES' BUTTON WILL CHANGE BACK TO THE DEFAULT TEXT COLOR WHEN THE MOUSE IS
     * NO LONGER HOVERING OVER IT
     */
    @FXML
    public void resetActiveGames()
    {
        activeGamesButton.setOnMouseExited(mouseEvent -> activeGamesButton.setTextFill(Color.WHITE));
    }

    /**
     * WHEN THIS METHOD IS CALLED THE 'COMPLETED GAMES' BUTTON WILL CHANGE COLOR WHEN THE MOUSE IS HOVERING OVER IT
     */
    @FXML
    public void highlightCompletedGames()
    {
        completedGamesButton.setOnMouseEntered(mouseEvent -> completedGamesButton.setTextFill(Color.YELLOW));
    }

    /**
     * WHEN THIS METHOD IS CALLED THE 'COMPLETED GAMES' BUTTON WILL CHANGE BACK TO THE DEFAULT TEXT COLOR WHEN THE MOUSE IS
     * NO LONGER HOVERING OVER IT
     */
    @FXML
    public void resetCompletedGames()
    {
        completedGamesButton.setOnMouseExited(mouseEvent -> completedGamesButton.setTextFill(Color.WHITE));
    }

    /**
     * WHEN THIS METHOD IS CALLED THE 'PLAYERS' BUTTON WILL CHANGE COLOR WHEN THE MOUSE IS HOVERING OVER IT
     */
    @FXML
    public void highlightPlayers()
    {
        playersButton.setOnMouseEntered(mouseEvent -> playersButton.setTextFill(Color.YELLOW));
    }

    /**
     * WHEN THIS METHOD IS CALLED THE 'PLAYERS' BUTTON WILL CHANGE BACK TO THE DEFAULT TEXT COLOR WHEN THE MOUSE IS
     * NO LONGER HOVERING OVER IT
     */
    @FXML
    public void resetPlayers()
    {
        playersButton.setOnMouseExited(mouseEvent -> playersButton.setTextFill(Color.WHITE));
    }

    @FXML
    public void handleEventLogAction(MouseEvent event)
    {
        System.out.println("You clicked me!");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("EventLogScreen");
        viewScreen.setCenter(view);
    }

    @FXML
    public void handleActiveGamesAction(MouseEvent event)
    {
        System.out.println("You clicked me!");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("ActiveGamesScreen");
        viewScreen.setCenter(view);
    }

    @FXML
    public void handleCompletedGamesAction(MouseEvent event)
    {
        System.out.println("You clicked me!");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("CompletedGamesScreen");
        viewScreen.setCenter(view);
    }

    @FXML
    public void handlePlayersAction(MouseEvent event)
    {
        System.out.println("You clicked me!");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("PlayersScreen");
        viewScreen.setCenter(view);
    }
}
