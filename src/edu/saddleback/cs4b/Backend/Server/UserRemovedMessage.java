package edu.saddleback.cs4b.Backend.Server;

import edu.saddleback.cs4b.Backend.Messages.BaseMessage;
import edu.saddleback.cs4b.Backend.Messages.MsgTypes;
import edu.saddleback.cs4b.Backend.Utilitys.PublicUser;

/**
 * Sent to the UI when user has been removed
 */
public class UserRemovedMessage extends BaseMessage {
    private PublicUser user;

    public UserRemovedMessage() {
        this(null);
    }

    public UserRemovedMessage(PublicUser user) {
        super(MsgTypes.REMOVED_USER);
        this.user = user;
    }

    public PublicUser getUser() {
        return user;
    }

    public void setUser(PublicUser user) {
        this.user = user;
    }
}
