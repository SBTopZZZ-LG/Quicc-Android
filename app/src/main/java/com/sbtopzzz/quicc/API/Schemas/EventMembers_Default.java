package com.sbtopzzz.quicc.API.Schemas;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class EventMembers_Default {
    @SerializedName("email")
    private String emailId;

    @Nullable
    @SerializedName("targetEmail")
    private String targetEmailId;

    private String eventUid;

    public EventMembers_Default(String emailId, @Nullable String targetEmailId, String eventUid) {
        this.emailId = emailId;
        this.targetEmailId = targetEmailId;
        this.eventUid = eventUid;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    @Nullable
    public String getTargetEmailId() {
        return targetEmailId;
    }

    public void setTargetEmailId(@Nullable String targetEmailId) {
        this.targetEmailId = targetEmailId;
    }

    public String getEventUid() {
        return eventUid;
    }

    public void setEventUid(String eventUid) {
        this.eventUid = eventUid;
    }
}
