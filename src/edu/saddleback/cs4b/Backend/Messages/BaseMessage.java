package edu.saddleback.cs4b.Backend.Messages;

import edu.saddleback.cs4b.Backend.MessageType;
import edu.saddleback.cs4b.Backend.Utilitys.MsgTypes;

import java.io.Serializable;
import java.util.Date;


public abstract class BaseMessage implements Serializable {
    MsgTypes messageType;
    Date timeStamp;

    public BaseMessage(MsgTypes newMessageType)
    {
        messageType = newMessageType;
        timeStamp = new Date();
    }

    public String getMessageType()//RETURN AS STRING
    {
        return messageType.getType();
    }

    public Date getTimeStamp()  //JUST RETURN TIME AS STRING
    {
        return timeStamp;
    }
}
