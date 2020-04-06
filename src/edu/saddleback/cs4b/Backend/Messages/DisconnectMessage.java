package edu.saddleback.cs4b.Backend.Messages;

import edu.saddleback.cs4b.Backend.Utilitys.MsgTypes;

import java.util.ArrayList;

public class DisconnectMessage extends BaseMessage{
    private ArrayList<String> channels;
    private String userName;



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
