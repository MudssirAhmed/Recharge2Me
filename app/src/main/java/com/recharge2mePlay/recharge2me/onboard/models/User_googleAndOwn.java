package com.recharge2mePlay.recharge2me.onboard.models;

import com.recharge2mePlay.recharge2me.onboard.models.CreateAccount_userDetails;
import com.recharge2mePlay.recharge2me.onboard.models.Google_User_Details;

public class User_googleAndOwn {

    private Google_User_Details Google;
    private CreateAccount_userDetails user_details;
    private String uid;
    private Integer Transaction;

    public User_googleAndOwn() {}

    public User_googleAndOwn(Google_User_Details google, CreateAccount_userDetails user_details, String uid, Integer Transaction) {
        this.Google = google;
        this.user_details = user_details;
        this.uid = uid;
        this.Transaction = Transaction;
    }


    public void setTransaction(Integer transaction) {
        Transaction = transaction;
    }
    public void setGoogle(Google_User_Details google) {
        Google = google;
    }
    public void setUser_details(CreateAccount_userDetails user_details) {
        this.user_details = user_details;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getTransaction() {
        return Transaction;
    }
    public String getUid() {
        return uid;
    }
    public Google_User_Details getGoogle() {
        return Google;
    }
    public CreateAccount_userDetails getUser_details() {
        return user_details;
    }
}
