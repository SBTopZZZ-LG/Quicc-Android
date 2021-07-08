package com.sbtopzzz.quicc.API.Schemas;

import com.google.gson.annotations.SerializedName;

public class UserGet_Body {
    @SerializedName("email")
    private String emailId;

    @SerializedName("targetEmail")
    private String targetEmailId;

    public UserGet_Body(String emailId, String targetEmailId) {
        this.emailId = emailId;
        this.targetEmailId = targetEmailId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getTargetEmailId() {
        return targetEmailId;
    }

    public void setTargetEmailId(String targetEmailId) {
        this.targetEmailId = targetEmailId;
    }
}
