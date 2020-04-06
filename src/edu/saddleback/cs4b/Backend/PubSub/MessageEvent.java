package edu.saddleback.cs4b.Backend.PubSub;

import edu.saddleback.cs4b.Backend.Messages.BaseMessage;

public class MessageEvent implements SystemEvent {
    private BaseMessage message;

    public MessageEvent(BaseMessage message) {
        setMessage(message);
    }

    private void setMessage(BaseMessage message) { this.message = message; }

    @Override
    public SystemEvent getEvent() {
        return null;
    }

    @Override
    public String getType() { return EventType.MESSAGE_EVENT.getType(); }
}
