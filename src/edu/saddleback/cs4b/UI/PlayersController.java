package edu.saddleback.cs4b.UI;

import edu.saddleback.cs4b.Backend.Messages.BaseMessage;
import edu.saddleback.cs4b.Backend.Messages.RequestAllCompletedGameMessage;
import edu.saddleback.cs4b.Backend.PubSub.MessageEvent;
import edu.saddleback.cs4b.Backend.PubSub.Observer;
import edu.saddleback.cs4b.Backend.PubSub.SystemEvent;
import edu.saddleback.cs4b.Backend.Server.*;
import edu.saddleback.cs4b.UI.Utilities.GameInfo;
import edu.saddleback.cs4b.UI.Utilities.PlayerInfo;
import edu.saddleback.cs4b.UI.Utilities.UILogger;
import javafx.beans.property.Property;
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
import java.util.ResourceBundle;

public class PlayersController implements Observer, Initializable
{
    private Logger logger = UILogger.getInstance();

    @FXML
    Button refreshButton;

    @FXML
    private TableView<PlayerInfo> playersTable;

    @FXML
    private TableColumn<PlayerInfo, String> idCol;

    @FXML
    private TableColumn<PlayerInfo, String> usernameCol;

    @FXML
    private TableColumn<PlayerInfo, String> firstNameCol;

    @FXML
    private TableColumn<PlayerInfo, String> lastNameCol;

    @FXML
    private TableColumn<PlayerInfo, String> statusCol;

    private ObservableList<PlayerInfo> playerInfo = FXCollections.observableArrayList();

    public PlayersController()
    {
        ServerLogger.getInstance().addObserver(this);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
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
        if (message instanceof UserAddedMessage)
        {

        }
        else if (message instanceof UserRemovedMessage)
        {

        }
    }

    public void displayToTable()
    {



        playersTable.setItems(playerInfo);
    }

    @FXML
    public void handleRefreshAction()
    {
        playersTable.getItems().clear();
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
