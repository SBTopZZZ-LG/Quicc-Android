package com.sbtopzzz.quicc.API.Schemas;

public class UserFriend {
    public enum UserFriendStatus {
        SENT,
        RECEIVED,
        SENDER_ACCEPTED,
        RECEIVER_ACCEPTED
    }

    public final String uid;
    private final double status;

    public UserFriend(String uid, double status) {
        this.uid = uid;
        this.status = status;
    }

    public UserFriendStatus getStatus() {
        return status == 0 ? UserFriendStatus.SENT :
                (status == 1.0 ? UserFriendStatus.RECEIVED :
                        (status == 2.0 ? UserFriendStatus.SENDER_ACCEPTED :
                                UserFriendStatus.RECEIVER_ACCEPTED));
    }
}
