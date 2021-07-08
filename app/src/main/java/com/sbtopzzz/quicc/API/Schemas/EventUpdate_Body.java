package com.sbtopzzz.quicc.API.Schemas;

import com.google.gson.annotations.SerializedName;

public class EventUpdate_Body {
    @SerializedName("email")
    private String emailId;

    private String eventUid;
    private Event event;

    public EventUpdate_Body(String emailId, String eventUid, Event event) {
        this.emailId = emailId;
        this.eventUid = eventUid;
        this.event = event;
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

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
