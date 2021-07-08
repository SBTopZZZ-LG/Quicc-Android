package com.sbtopzzz.quicc.API.Schemas;

import com.google.gson.annotations.SerializedName;

public class EventDelete_Body {
    @SerializedName("email")
    private String emailId;

    private String eventUid;

    public EventDelete_Body(String emailId, String eventUid) {
        this.emailId = emailId;
        this.eventUid = eventUid;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getEventUid() {
        return eventUid;
    }

    public void setEventUid(String eventUid) {
        this.eventUid = eventUid;
    }
}
