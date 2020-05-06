package edu.saddleback.cs4b.UI;

import edu.saddleback.cs4b.Backend.Messages.BaseMessage;
import edu.saddleback.cs4b.Backend.PubSub.MessageEvent;
import edu.saddleback.cs4b.Backend.PubSub.Observer;
import edu.saddleback.cs4b.Backend.PubSub.SystemEvent;
import edu.saddleback.cs4b.Backend.Server.*;
import edu.saddleback.cs4b.Backend.Utilitys.PublicUser;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class EventLogController implements Observer
{
    @FXML
    private ListView<String> eventLog;

    public EventLogController()
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
        if (message instanceof UserAddedMessage)
        {
            displayUser(((UserAddedMessage) message).getUser().getUsername(), "");
        }
        else if (message instanceof UserRemovedMessage)
        {
            String username = ((UserRemovedMessage) message).getUser().getUsername();
            String regex = "user: " + username + " .*";
            removeUser(regex);

        }
        else if (message instanceof UserAddedGameMessage)
        {
            String username = ((UserAddedGameMessage) message).getUsername();
            String gameId = ((UserAddedGameMessage) message).getGameId();
            String regex = "user: " + username + " .*";
            removeUser(regex);
            displayUser(username, gameId);
        }
        else if (message instanceof UserRemovedGameMessage)
        {
            String username = ((UserRemovedGameMessage) message).getUsername();
            String regex = "user: " + username + " .*";
            removeUser(regex);
            displayUser(username, "");
        }
    }

    private void removeUser(String regex) {
        Platform.runLater(()-> eventLog.getItems().removeIf(s->s.matches(regex)));
    }

    private void displayUser(String user, String game) {
        Platform.runLater(()-> eventLog.getItems().add("user: " + user + " current game: " + game));
    }

}
