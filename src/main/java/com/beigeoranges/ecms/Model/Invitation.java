package com.beigeoranges.ecms.Model;

public class Invitation {

    private Event event;
    private String email;

    public Invitation() {

    }

    public Invitation(Event event, String email) {
        this.event = event;
        this.email = email;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
