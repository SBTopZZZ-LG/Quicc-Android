package com.sbtopzzz.quicc.API.Schemas;

import com.google.gson.annotations.SerializedName;

public class UserSearch_Default {
    @SerializedName("email")
    private String emailId;

    private String expression;

    public UserSearch_Default(String emailId, String expression) {
        this.emailId = emailId;
        this.expression = expression;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }
}
