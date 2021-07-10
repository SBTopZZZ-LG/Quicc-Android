package com.sbtopzzz.quicc.API.Schemas;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class UserFriends_Default {
    @SerializedName("email")
    private String emailId;

    @Nullable
    @SerializedName("targetEmail")
    private String targetEmailId;

    public UserFriends_Default(String emailId, @Nullable String targetEmailId) {
        this.emailId = emailId;
        this.targetEmailId = targetEmailId;
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
}
