package edu.saddleback.cs4b.Backend.Utilitys;

import edu.saddleback.cs4b.Backend.PubSub.Observer;
import edu.saddleback.cs4b.Backend.PubSub.Subject;
import edu.saddleback.cs4b.Backend.PubSub.SystemEvent;

public class ServerLogger implements Logger, Subject {
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
