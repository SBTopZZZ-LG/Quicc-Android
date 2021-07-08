package com.sbtopzzz.quicc.API.Schemas;

import com.google.gson.annotations.SerializedName;

public class EventCreate_Body {
    @SerializedName("email")
    private String emailId;

    private Event event;

    public EventCreate_Body(String emailId, Event event) {
        this.emailId = emailId;
        this.event = event;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
