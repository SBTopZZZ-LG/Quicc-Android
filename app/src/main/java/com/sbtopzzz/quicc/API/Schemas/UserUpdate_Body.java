package com.sbtopzzz.quicc.API.Schemas;

import com.google.gson.annotations.SerializedName;

public class UserUpdate_Body {
    @SerializedName("email")
    private String emailId;

    private User user;

    public UserUpdate_Body(String emailId, User user) {
        this.emailId = emailId;
        this.user = user;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
