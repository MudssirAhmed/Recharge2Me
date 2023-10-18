package com.recharge2mePlay.recharge2me.recharge.models;

public class RecTypeRMG {

    private String amount;
    private String detail;
    private String validity;
    private String talktime;

    public RecTypeRMG(String amount, String detail, String validity, String talktime) {
        this.amount = amount;
        this.detail = detail;
        this.validity = validity;
        this.talktime = talktime;
    }

    public String getAmount() {
        return amount;
    }

    public String getDetail() {
        return detail;
    }

    public String getValidity() {
        return validity;
    }

    public String getTalktime() {
        return talktime;
    }

}
