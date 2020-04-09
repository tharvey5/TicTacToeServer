package edu.saddleback.cs4b.Backend.Messages;

public class RegistrationErrorMessage extends BaseMessage {
    public RegistrationErrorMessage() {
        super(MsgTypes.REG_ERROR);
    }
}
