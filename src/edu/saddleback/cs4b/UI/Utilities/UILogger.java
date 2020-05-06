package edu.saddleback.cs4b.UI.Utilities;

import edu.saddleback.cs4b.Backend.PubSub.Observer;
import edu.saddleback.cs4b.Backend.PubSub.Subject;
import edu.saddleback.cs4b.Backend.PubSub.SystemEvent;
import edu.saddleback.cs4b.Backend.Server.Logger;

import java.util.ArrayList;
import java.util.List;

public class UILogger implements Logger, Subject {
    private volatile static UILogger logger = null;
    private List<Observer> observers;

    private UILogger() {
        this.observers = new ArrayList<>();
    }

    public static UILogger getInstance() {
        if (logger == null) {
            synchronized (UILogger.class) {
                if (logger == null) {
                    logger = new UILogger();
                }
            }
        }
        return logger;
    }

    @Override
    public void addObserver(Observer o) {
        if (o != null) {
            observers.add(o);
        }
    }

    @Override
    public void removeObserver(Observer o) {
        if (o != null) {
            observers.remove(o);
        }
    }

    @Override
    public void notifyObserver(SystemEvent e) {
        for (Observer ob : observers) {
            ob.update(e);
        }
    }

    @Override
    public void log(SystemEvent se) {
        notifyObserver(se);
    }
}
