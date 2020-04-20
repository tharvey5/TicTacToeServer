package edu.saddleback.cs4b.Backend.Server;

import edu.saddleback.cs4b.Backend.Messages.BaseMessage;
import edu.saddleback.cs4b.Backend.Messages.MsgTypes;
import edu.saddleback.cs4b.Backend.Utilitys.PublicUser;

public class UserAddedMessage extends BaseMessage {
    private PublicUser user;

    public UserAddedMessage() {
        this(null);
    }

    public UserAddedMessage(PublicUser user) {
        super(MsgTypes.ADDED_USER);
        this.user = user;
    }

    public PublicUser getUser() {
        return user;
    }

    public void setUser(PublicUser user) {
        this.user = user;
    }
}
