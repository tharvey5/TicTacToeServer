package edu.saddleback.cs4b.UI;

import edu.saddleback.cs4b.Backend.Messages.*;
import edu.saddleback.cs4b.Backend.Objects.Game;
import edu.saddleback.cs4b.Backend.Objects.Move;
import edu.saddleback.cs4b.Backend.Objects.TTTPosition;
import edu.saddleback.cs4b.Backend.PubSub.MessageEvent;
import edu.saddleback.cs4b.Backend.PubSub.Observer;
import edu.saddleback.cs4b.Backend.PubSub.SystemEvent;
import edu.saddleback.cs4b.Backend.Server.*;
import edu.saddleback.cs4b.UI.Utilities.CachedGames;
import edu.saddleback.cs4b.UI.Utilities.CachedMoves;
import edu.saddleback.cs4b.UI.Utilities.GameInfo;
import edu.saddleback.cs4b.UI.Utilities.UILogger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class CompletedGamesController implements Observer, Initializable
{
    private Logger logger = UILogger.getInstance();

    @FXML
    Button refreshButton;

    @FXML
    Button showGameDetailsBtn;

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
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        gameCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        p1Col.setCellValueFactory(new PropertyValueFactory<>("p1"));
        p2Col.setCellValueFactory(new PropertyValueFactory<>("p2"));

        completedGamesTable.getItems().clear();
        RequestAllCompletedGameMessage reqMsg = new RequestAllCompletedGameMessage();
        GameInfoService.getInstance();
        logger.log(new MessageEvent(reqMsg));
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
        if (message instanceof ReturnAllCompletedGamesMessage)
        {
            displayToUI((ReturnAllCompletedGamesMessage)message);
        }
        else if (message instanceof RespondMovesMessage)
        {
            List<Move> moves = ((RespondMovesMessage) message).getMoves();
            CachedMoves.getInstance().setMoves(moves);
        }
    }

    private void displayToUI(ReturnAllCompletedGamesMessage message)
    {
        List<Game> games = message.getGames();
        for (Game g : games)
        {
            GameInfo info = new GameInfo();
            CachedGames.getInstance().addGame(g);
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

    @FXML
    public void handleShowGameDetailsAction() throws IOException
    {
        swapShowGameDetails("/edu/saddleback/cs4b/UI/Server.fxml", showGameDetailsBtn);
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
     * WHEN THIS METHOD IS CALLED THE 'SHOW GAME DETAILS' BUTTON WILL CHANGE COLOR WHEN THE MOUSE IS HOVERING OVER IT
     */
    @FXML
    public void highlightShowGameDetails()
    {
        showGameDetailsBtn.setOnMouseEntered(mouseEvent -> showGameDetailsBtn.setTextFill(Color.valueOf("#FFD700")));
    }

    /**
     * WHEN THIS METHOD IS CALLED THE 'SHOW GAME DETAILS' BUTTON WILL CHANGE BACK TO THE DEFAULT TEXT COLOR WHEN THE MOUSE IS
     * NO LONGER HOVERING OVER IT
     */
    @FXML
    public void resetShowGameDetails()
    {
        showGameDetailsBtn.setOnMouseExited(mouseEvent -> showGameDetailsBtn.setTextFill(Color.BLACK));
    }

    @FXML
    public void onRowClicked()
    {
        if(completedGamesTable.getSelectionModel().getSelectedItem() != null)
        {
            showGameDetailsBtn.setDisable(false);
            logger.log(new MessageEvent(new RequestMovesOfGameMessage(completedGamesTable.getSelectionModel().getSelectedItem().getId())));
        }
    }

    public void swapShowGameDetails(String sceneLocation, Button button) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(sceneLocation));
        Parent root = loader.load();
        Scene scene  = new Scene(root);
        Stage window = (Stage)(button).getScene().getWindow();

        ServerController crtl = loader.getController();
        crtl.handleShowGameDetailsAction();

        Platform.runLater(()->
        {
            window.setScene(scene);
            window.show();
        });
    }

}
