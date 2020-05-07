package edu.saddleback.cs4b.UI;

import edu.saddleback.cs4b.Backend.Messages.*;
import edu.saddleback.cs4b.Backend.PubSub.MessageEvent;
import edu.saddleback.cs4b.Backend.PubSub.Observer;
import edu.saddleback.cs4b.Backend.PubSub.SystemEvent;
import edu.saddleback.cs4b.Backend.Server.*;
import edu.saddleback.cs4b.UI.Utilities.UILogger;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ActiveGamesController implements Observer
{
    private Logger logger = UILogger.getInstance();

    @FXML
    private Button refreshButton;

    @FXML
    private TableView activeGamesTable;

    @FXML
    private TableColumn idCol;

    @FXML
    private TableColumn gameCol;

    @FXML
    private TableColumn p1Col;

    @FXML
    private TableColumn p2Col;


    public ActiveGamesController() {
        ServerLogger.getInstance().addObserver(this);
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

        for (Map.Entry<String,List<String>> entry : map.entrySet()) {
           //id.setCellFactory(new PropertyValueFactory<String, List<String>>("id"));
            System.out.println(entry.getKey());

            // print the players
            for (String player : map.get(entry.getKey())) {
                System.out.println(player);
            }
        }
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
