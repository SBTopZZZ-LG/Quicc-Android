package com.sbtopzzz.quicc.API.Schemas;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class UserGet_Body {
    @SerializedName("email")
    private String emailId;

    @Nullable
    @SerializedName("targetEmail")
    private String targetEmailId;

    @Nullable
    private String targetUserId;

    public UserGet_Body(String emailId, @Nullable String targetEmailId, @Nullable String targetUserId) {
        this.emailId = emailId;
        this.targetEmailId = targetEmailId;
        this.targetUserId = targetUserId;
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

    @Nullable
    public String getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(@Nullable String targetUserId) {
        this.targetUserId = targetUserId;
    }
}
