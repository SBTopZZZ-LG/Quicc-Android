package com.sbtopzzz.quicc.Activities.UserViewEventActivity_Objects;

public class Organizer {
    private String userName;
    private String userTitle;

    public Organizer(String userName, String userTitle) {
        this.userName = userName;
        this.userTitle = userTitle.toUpperCase();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserTitle() {
        return userTitle;
    }

    public void setUserTitle(String userTitle) {
        this.userTitle = userTitle.toUpperCase();
    }
}
