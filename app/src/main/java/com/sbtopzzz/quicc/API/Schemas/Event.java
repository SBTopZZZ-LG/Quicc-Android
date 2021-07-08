package com.sbtopzzz.quicc.API.Schemas;

import java.util.List;

public class Event {
    public final String uid;

    private String host;
    private String title;
    private Long startDate;
    private Long endDate;
    private List<String> members;
    private List<String> visitedMembers;

    public Event(String uid) {
        this.uid = uid;
    }

    public Event(String uid, String host, String title, Long startDate, Long endDate, List<String> members, List<String> visitedMembers) {
        this.uid = uid;
        this.host = host;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.members = members;
        this.visitedMembers = visitedMembers;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public List<String> getVisitedMembers() {
        return visitedMembers;
    }

    public void setVisitedMembers(List<String> visitedMembers) {
        this.visitedMembers = visitedMembers;
    }
}
