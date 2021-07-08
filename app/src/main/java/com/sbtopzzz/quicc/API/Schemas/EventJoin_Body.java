package com.sbtopzzz.quicc.API.Schemas;

import com.google.gson.annotations.SerializedName;

public class EventJoin_Body {
    @SerializedName("email")
    private String emailId;

    private String key;

    public EventJoin_Body(String emailId, String key) {
        this.emailId = emailId;
        this.key = key;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
