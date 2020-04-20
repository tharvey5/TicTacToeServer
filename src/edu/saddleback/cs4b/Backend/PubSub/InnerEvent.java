package edu.saddleback.cs4b.Backend.PubSub;

/**
 * This event can be used for all communication within the server system
 */
public class InnerEvent implements SystemEvent{


    @Override
    public SystemEvent getEvent() { return this; }

    @Override
    public String getType() { return EventType.INNER_EVENT.getType(); }
}
