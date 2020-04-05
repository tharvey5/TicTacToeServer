package edu.saddleback.cs4b.Backend.Messages;

import java.util.ArrayList;

public class DisconnectMessage extends BaseMessage{
    ArrayList<String> channels;
    String userName;


    /**
     * used for the message factory
     */
    DisconnectMessage()
    {
        this(null, "");
    }

    public DisconnectMessage(ArrayList<String> newChannels, String newUserName)
    {
        super(MsgTypes.DISCONNECTION);
        setChannels(newChannels);
        setUserName(newUserName);
    }



    public ArrayList<String> getChannels()
    {
        return channels;
    }

    private void setChannels(ArrayList<String> channels)
    {
        channels = channels;
    }

    public String getUserName()
    {
        return userName;
    }

    private void setUserName(String userName)
    {
        this.userName = userName;
    }
}
