package edu.saddleback.cs4b.Backend.Messages;

import java.io.Serializable;

public class Packet implements Serializable{
    private Serializable data;

    public Packet(Serializable newData)
    {
        setData(newData);
    }

    private void setData(Serializable data)
    {
        this.data = data;
    }

    public Serializable getData()
    {
        return data;
    }
}
