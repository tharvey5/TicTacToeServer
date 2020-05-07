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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class ActiveGamesController implements Observer, Initializable
{
    private Logger logger = UILogger.getInstance();

    @FXML
    private Button refreshButton;

    @FXML
    private TableView<GameInfo> activeGamesTable;

    @FXML
    private TableColumn<GameInfo, String> idCol;

    @FXML
    private TableColumn<GameInfo, String> gameCol;

    @FXML
    private TableColumn<GameInfo, String> p1Col;

    @FXML
    private TableColumn<GameInfo, String> p2Col;

    private ObservableList<GameInfo> gameInfo = FXCollections.observableArrayList();


    public ActiveGamesController() {
        ServerLogger.getInstance().addObserver(this);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        gameCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        p1Col.setCellValueFactory(new PropertyValueFactory<>("p1"));
        p2Col.setCellValueFactory(new PropertyValueFactory<>("p2"));
    }

    // recieves information when ever the server logger gets log called
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
        if (message instanceof ReturnAllActiveGamesMessage) {
            displayToUI((ReturnAllActiveGamesMessage)message);
        }
    }

    private void displayToUI(ReturnAllActiveGamesMessage message) {
        Map<String, List<String>> map = message.getGameAndPlayers();
        GameInfo addedInfo = null;

        for (Map.Entry<String,List<String>> entry : map.entrySet()) {
            addedInfo = new GameInfo();
            addedInfo.setId(entry.getKey());
            // print the players
            List<String> players = map.get(entry.getKey());
            addedInfo.setP1(players.get(0));
            if (players.size() == 2) {
                addedInfo.setP2(players.get(1));
            }
            addedInfo.setTitle(players.get(0)+ "\'s game");
            gameInfo.add(addedInfo);
        }
        activeGamesTable.setItems(gameInfo);
    }

    @FXML
    public void handleRefreshAction()
    {
        RequestAllActiveGamesMessage reqMsg = new RequestAllActiveGamesMessage();
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
