package edu.saddleback.cs4b.UI;

import edu.saddleback.cs4b.Backend.Messages.BaseMessage;
import edu.saddleback.cs4b.Backend.PubSub.MessageEvent;
import edu.saddleback.cs4b.Backend.PubSub.Observer;
import edu.saddleback.cs4b.Backend.PubSub.SystemEvent;
import edu.saddleback.cs4b.Backend.Server.ServerLogger;
import edu.saddleback.cs4b.Backend.Server.UserAddedMessage;
import edu.saddleback.cs4b.Backend.Server.UserRemovedMessage;
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
            displayUser(((UserAddedMessage) message).getUser());
        }
        else if (message instanceof UserRemovedMessage)
        {
            removeUser(((UserRemovedMessage) message).getUser());
        }
    }

    private void removeUser(PublicUser user)
    {
        Platform.runLater(()-> eventLog.getItems().remove("user: " + user.getUsername()));
    }

    private void displayUser(PublicUser user)
    {
        Platform.runLater(()-> eventLog.getItems().add("user: " + user.getUsername()));
    }

}
