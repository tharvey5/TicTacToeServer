package edu.saddleback.cs4b.Backend.Utilitys;

import edu.saddleback.cs4b.Backend.PubSub.Observer;
import edu.saddleback.cs4b.Backend.PubSub.Subject;
import edu.saddleback.cs4b.Backend.PubSub.SystemEvent;

import java.util.ArrayList;
import java.util.List;

public class ServerLogger implements Logger, Subject {
    private static ServerLogger logger = null;
    private List<Observer> observers;

    private ServerLogger() {
        observers = new ArrayList<>();
    }

    @Override
    public void addObserver(Observer o) {

    }

    @Override
    public void removeObserver(Observer o) {

    }

    @Override
    public void notifyObserver(SystemEvent e) {

    }

    @Override
    public void log(SystemEvent se) {

    }
}
