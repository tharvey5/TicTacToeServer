package edu.saddleback.cs4b.Backend.PubSub;

import edu.saddleback.cs4b.Backend.Messages.BaseMessage;

public class MessageEvent implements SystemEvent {
    private BaseMessage message;
    @Override
    public SystemEvent getEvent() {
        return null;
    }

    @Override
    public String getType() {
        return null;
    }
}
