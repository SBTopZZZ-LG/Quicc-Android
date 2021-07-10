package com.sbtopzzz.quicc.Activities.UserSearchActivity_Objects;

public class Search {
    public final String uid;

    private String userName;
    private String userEmailId;

    public Search(String uid, String userName, String userEmailId) {
        this.uid = uid;

        this.userName = userName;
        this.userEmailId = userEmailId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmailId() {
        return userEmailId;
    }

    public void setUserEmailId(String userEmailId) {
        this.userEmailId = userEmailId;
    }
}
