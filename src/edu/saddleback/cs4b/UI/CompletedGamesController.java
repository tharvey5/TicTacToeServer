package edu.saddleback.cs4b.UI;

import edu.saddleback.cs4b.Backend.Messages.*;
import edu.saddleback.cs4b.Backend.Objects.Game;
import edu.saddleback.cs4b.Backend.PubSub.MessageEvent;
import edu.saddleback.cs4b.Backend.PubSub.Observer;
import edu.saddleback.cs4b.Backend.PubSub.SystemEvent;
import edu.saddleback.cs4b.Backend.Server.*;
import edu.saddleback.cs4b.UI.Utilities.GameInfo;
import edu.saddleback.cs4b.UI.Utilities.UILogger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class CompletedGamesController implements Observer, Initializable
{
    private Logger logger = UILogger.getInstance();

    @FXML
    Button refreshButton;

    @FXML
    private TableView<GameInfo> completedGamesTable;

    @FXML
    private TableColumn<GameInfo, String> idCol;

    @FXML
    private TableColumn<GameInfo, String> gameCol;

    @FXML
    private TableColumn<GameInfo, String> p1Col;

    @FXML
    private TableColumn<GameInfo, String> p2Col;

    private ObservableList<GameInfo> gameInfo = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        gameCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        p1Col.setCellValueFactory(new PropertyValueFactory<>("p1"));
        p2Col.setCellValueFactory(new PropertyValueFactory<>("p2"));
    }

    public CompletedGamesController()
    {
        ServerLogger.getInstance().addObserver(this);
    }

    @Override
    public void update(SystemEvent e)
    {
        if(e instanceof MessageEvent)
        {
            handleMessage(((MessageEvent) e).getMessage());
        }
    }

    private void handleMessage(BaseMessage message)
    {
        if (message instanceof ReturnAllCompletedGamesMessage) {
            displayToUI((ReturnAllCompletedGamesMessage)message);
        }
    }

    private void displayToUI(ReturnAllCompletedGamesMessage message) {
        List<Game> games = message.getGames();
        for (Game g : games) {
            GameInfo info = new GameInfo();
            info.setId(g.getGameID());
            info.setP1(g.getStartPlayer().getUsername());
            info.setP2(g.getOtherPlayer().getUsername());
            info.setTitle(g.getCreator().getUsername() + " \'s game");
            gameInfo.add(info);
        }

        completedGamesTable.setItems(gameInfo);
    }

    @FXML
    public void handleRefreshAction()
    {
        completedGamesTable.getItems().clear();
        RequestAllCompletedGameMessage reqMsg = new RequestAllCompletedGameMessage();
        GameInfoService.getInstance();
        logger.log(new MessageEvent(reqMsg));
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
}
