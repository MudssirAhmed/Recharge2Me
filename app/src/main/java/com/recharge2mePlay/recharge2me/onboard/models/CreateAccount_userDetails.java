package com.recharge2mePlay.recharge2me.onboard.models;

public class CreateAccount_userDetails {

    private String Name;
    private String Email;
    private String Rewards;
    private String Number;

    public CreateAccount_userDetails() {}

    public CreateAccount_userDetails(String name, String email, String rewards, String number) {
        Name = name;
        Email = email;
        Rewards = rewards;
        Number = number;
    }

    public void setNumber(String number) {
        Number = number;
    }
    public void setName(String name) {
        Name = name;
    }
    public void setEmail(String email) {
        Email = email;
    }
    public void setRewards(String rewards) {
        Rewards = rewards;
    }


    public String getNumber() {
        return Number;
    }
    public String getName() {
        return Name;
    }
    public String getEmail() {
        return Email;
    }
    public String getRewards() {
        return Rewards;
    }
}
