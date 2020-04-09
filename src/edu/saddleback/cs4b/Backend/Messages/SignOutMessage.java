package edu.saddleback.cs4b.Backend.Messages;

public class SignOutMessage extends BaseMessage {
    public SignOutMessage() {
        super(MsgTypes.SIGN_OUT);
    }
}
