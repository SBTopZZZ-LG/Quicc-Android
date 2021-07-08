package com.sbtopzzz.quicc.API.Schemas;

import com.google.gson.annotations.SerializedName;

public class User {
    public final String uid;

    private String name;

    @SerializedName("email")
    private String emailId;

    public User(String uid) {
        this.uid = uid;
    }

    public User(String uid, String name, String emailId) {
        this.uid = uid;
        this.name = name;
        this.emailId = emailId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
}
