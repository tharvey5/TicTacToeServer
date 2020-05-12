package edu.saddleback.cs4b.UI.Utilities;

import com.sun.javafx.collections.UnmodifiableListSet;
import edu.saddleback.cs4b.Backend.Messages.BaseMessage;
import edu.saddleback.cs4b.Backend.PubSub.MessageEvent;
import edu.saddleback.cs4b.Backend.PubSub.Observer;
import edu.saddleback.cs4b.Backend.PubSub.Subject;
import edu.saddleback.cs4b.Backend.PubSub.SystemEvent;
import edu.saddleback.cs4b.Backend.Server.UserAddedGameMessage;
import edu.saddleback.cs4b.Backend.Server.UserAddedMessage;
import edu.saddleback.cs4b.Backend.Server.UserRemovedGameMessage;
import edu.saddleback.cs4b.Backend.Server.UserRemovedMessage;

import java.util.*;

/**
 * observes the Server and is the Subject (being observed by the EventLogController
 * controller when it is instantiated
 */
public class EventLog implements Observer, Subject {
    private static EventLog log = new EventLog();
    private Set<String> events;
    private List<Observer> observers;

    private EventLog() {
        events = new HashSet<>();
        observers = new ArrayList<>();
    }

    public static EventLog getInstance() { return log; }

    public Set<String> getEvents() { return Collections.unmodifiableSet(events); }

    // this part listens to the server
    @Override
    public void update(SystemEvent e) {
        if(e instanceof MessageEvent)
        {
            handleMessage(((MessageEvent) e).getMessage());
        }
    }

    private void handleMessage(BaseMessage message)
    {
        if (message instanceof UserAddedMessage)
        {
            store(((UserAddedMessage) message).getUser().getUsername(), "");
            notifyObserver(new MessageEvent(message));
        }
        else if (message instanceof UserRemovedMessage)
        {
            String username = ((UserRemovedMessage) message).getUser().getUsername();
            String regex = "user: " + username + " .*";
            removeUser(regex);
            notifyObserver(new MessageEvent(message));
        }
        else if (message instanceof UserAddedGameMessage)
        {
            String username = ((UserAddedGameMessage) message).getUsername();
            String gameId = ((UserAddedGameMessage) message).getGameId();
            String regex = "user: " + username + " .*";
            removeUser(regex);
            store(username, gameId);
            notifyObserver(new MessageEvent(message));
        }
        else if (message instanceof UserRemovedGameMessage)
        {
            String username = ((UserRemovedGameMessage) message).getUsername();
            String regex = "user: " + username + " .*";
            removeUser(regex);
            store(username, "");
            notifyObserver(new MessageEvent(message));
        }
    }

    private void removeUser(String regex) {
        events.remove(regex);
    }

    private void store(String user, String game) {
        events.add("user: " + user + " current game: " + game);
    }


    // this part will be used to manage the EventLogController
    //todo message will just notify about the individual strings that were added or removed
    // like we did in the past
    @Override
    public void addObserver(Observer o) {
        if (o != null) observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        if (o != null) observers.remove(o);
    }

    @Override
    public void notifyObserver(SystemEvent e) {
        for (Observer ob : observers) {
            ob.update(e);
        }
    }
}
