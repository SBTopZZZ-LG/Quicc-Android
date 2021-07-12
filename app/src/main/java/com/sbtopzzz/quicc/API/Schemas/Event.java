package com.sbtopzzz.quicc.API.Schemas;

import androidx.annotation.Nullable;

import java.util.List;

public class Event {
    @Nullable
    public final String uid;

    @Nullable
    private String host;
    private String title;
    private Long startDate;
    private Long endDate;
    private List<String> members;
    private List<String> visitedMembers;

    public Event(@Nullable String uid) {
        this.uid = uid;
    }

    public Event(@Nullable String uid, @Nullable String host, String title, Long startDate, Long endDate, List<String> members, List<String> visitedMembers) {
        this.uid = uid;
        this.host = host;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        reformatDates();
        this.members = members;
        this.visitedMembers = visitedMembers;
    }

    public Event(String title, Long startDate, Long endDate, List<String> members, List<String> visitedMembers) {
        uid = null;
        host = null;

        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.members = members;
        this.visitedMembers = visitedMembers;
    }

    public Event(String title, Long startDate, Long endDate) {
        uid = null;
        host = null;

        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getHost() {
        return host;
    }

    public void setHost(@Nullable String host) {
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
        reformatDates();
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
        reformatDates();
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

    private void reformatDates() {
        if (startDate.toString().length() < 13)
            startDate *= Long.parseLong(String.valueOf(Math.pow(10, 13 - startDate.toString().length())).split("\\.")[0]);
        if (endDate.toString().length() < 13)
            endDate *= Long.parseLong(String.valueOf(Math.pow(10, 13 - endDate.toString().length())).split("\\.")[0]);
    }
}
