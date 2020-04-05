package edu.saddleback.cs4b.Backend.Messages;

public class AdminMsgFactory implements AbstractMessageFactory {
    @Override
    public  BaseMessage createMessage(String type) {
        BaseMessage message = null;
        if (type.equals(MsgTypes.ACTIVE_USER_REQ.toString())) {
            message = new ActiveUserMessage();
        } else  if (type.equals(MsgTypes.DISCONNECTION.toString())) {
            message = new DisconnectMessage();
        } else if (type.equals(MsgTypes.SIGN_IN.toString())) {
            message = new SignInMessage();
        }
        return message;
    }
}
