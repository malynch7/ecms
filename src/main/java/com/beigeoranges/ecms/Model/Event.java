package com.beigeoranges.ecms.Model;

public class Event {
    private int event_id;
    private String event_name;
    private String event_time;
    private String event_address;
    private int admin_id;

    public Event() {
    }

    public Event(int event_id, String event_name, String event_time, String event_address, int admin_id) {
        this.event_id = event_id;
        this.event_name = event_name;
        this.event_time = event_time;
        this.event_address = event_address;
        this.admin_id = admin_id;
    }

    public int getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getEvent_time() {
        return event_time;
    }

    public void setEvent_time(String event_time) {
        this.event_time = event_time;
    }

    public String getEvent_address() {
        return event_address;
    }

    public void setEvent_address(String event_address) {
        this.event_address = event_address;
    }
}
