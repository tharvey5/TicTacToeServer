package edu.saddleback.cs4b.Backend.Messages;

import edu.saddleback.cs4b.Backend.Utilitys.Profile;

public class ProfileMessage extends BaseMessage {
    private Profile profile;

    /**
     * only to be used by the message factory
     */
    ProfileMessage() {
        this(null);
    }

    public ProfileMessage(Profile profile) {
        super(MsgTypes.PROFILE);
        setProfile(profile);
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    // todo should this be immutable?
    public Profile getProfile() { return profile; }
}
