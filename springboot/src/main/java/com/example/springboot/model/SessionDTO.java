package com.example.springboot.model;

import java.util.Date;

public class SessionDTO extends User {
    private Date date;
    private String sessionString;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSessionString() {
        return sessionString;
    }

    public void setSessionString(String sessionString) {
        this.sessionString = sessionString;
    }
}
