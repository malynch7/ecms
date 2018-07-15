package com.beigeoranges.ecms.Model;

public class Invitation {

    private int eventId;
    private String email;

    public Invitation() {

    }

    public Invitation(int eventId, String email) {
        this.eventId = eventId;
        this.email = email;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
}
