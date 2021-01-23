package com.recharge2mePlay.recharge2me;

public class EntryMessage {

    private String Message;
    private String Action;
    private String link;
    private String version;
    private String check;

    public EntryMessage() {}

//    public EntryMessage

    public String getCheck() {
        return check;
    }
    public String getAction() {
        return this.Action;
    }
    public String getMessage() {
        return this.Message;
    }
    public String getVersion() {
        return version;
    }
    public String getLink() {
        return link;
    }

    public void setCheck(String check) {
        this.check = check;
    }
    public void setAction(final String action) {
        this.Action = action;
    }
    public void setLink(String link) {
        this.link = link;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public void setMessage(final String message) {
        this.Message = message;
    }
}
