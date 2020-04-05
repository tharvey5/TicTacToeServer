package edu.saddleback.cs4b.Backend.Messages;

public interface MessageFactory {
    BaseMessage createMessage(String type);
}
