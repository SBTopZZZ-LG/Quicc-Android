package com.sbtopzzz.quicc.Activities.UserViewEventActivity_Objects;

public class Invitee {
    public final String uid;
    private String userName;
    private String emailId;

    public Invitee(String uid, String userName, String emailId) {
        this.uid = uid;
        this.userName = userName;
        this.emailId = emailId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
}
