package com.recharge2mePlay.recharge2me;

public class EntryMessage {

    private String Message;
    private String Action;

    public EntryMessage() {}

//    public EntryMessage


    public String getAction() {
        return this.Action;
    }
    public String getMessage() {
        return this.Message;
    }



    public void setAction(final String action) {
        this.Action = action;
    }
    public void setMessage(final String message) {
        this.Message = message;
    }
}
