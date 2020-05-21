package edu.saddleback.cs4b.Backend.Server;

import edu.saddleback.cs4b.Backend.PubSub.Observer;

public interface ClientConnection extends Observer {
   String identifyClient();
}
