package edu.saddleback.cs4b.Backend.PubSub;

/**
 * This will be used for all system events and UI
 * events to force certain names
 */
public enum EventType {
    CONNECTION("Connection");

    private EventType(String type) { this.type = type; }
    private String type;
    public String getType() { return type; }
}
