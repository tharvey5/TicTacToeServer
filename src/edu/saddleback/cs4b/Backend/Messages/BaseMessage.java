package edu.saddleback.cs4b.Backend.Messages;

import edu.saddleback.cs4b.Backend.MessageType;

import java.io.Serializable;
import java.util.Date;


public abstract class BaseMessage implements Serializable {
    MessageType messageType;
    Date timeStamp;

    public BaseMessage(MessageType newMessageType)
    {git messageType
        messageType = newMessageType;
        timeStamp = new Date();
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }
}
