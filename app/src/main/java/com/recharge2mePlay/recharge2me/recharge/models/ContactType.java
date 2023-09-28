package com.recharge2mePlay.recharge2me.recharge.models;

public class ContactType {

    public String name;
    public String number;

    public ContactType(String name, String number) {
        this.name = name;
        this.number =  number;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

}
