package com.sbtopzzz.quicc.Activities.UserHomeActivity_Fragments.UserHomeActivity_Fragment_Home_Objects;

import java.util.Date;

public class MyEvent {
    private String host;
    private String eventName;
    private Date startDate;
    private Date endDate;
    private String eventUid;

    public MyEvent(String host, String eventName, Date startDate, Date endDate, String eventUid) {
        this.host = host;
        this.eventName = eventName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.eventUid = eventUid;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getEventUid() {
        return eventUid;
    }

    public void setEventUid(String eventUid) {
        this.eventUid = eventUid;
    }
}
