package com.beigeoranges.ecms.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "Events")
public class ArchivedEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "id") //not sure about this line
    @JsonIgnore

    @NotBlank(message = "Empty")
    @Column(name = "Events", nullable = false)

    //getters and setters
    private int event_id;
    private String event_name;
    private String event_time;
    private String event_address;
    private int admin_id;
    // to be added
    //private String handbook_file_path;

    public ArchivedEvent(){}

    public ArchivedEvent(Event anEvent){
        event_id = anEvent.getEvent_id();
        event_name = anEvent.getEvent_name();
        event_time = anEvent.getEvent_time();
        event_address = anEvent.getEvent_time();
        admin_id = anEvent.getAdmin_id();
        // add to constructor as well
        //this.handbook_file_path = handbook_file_path;

    }

    /*public int getAdmin_id() {
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
    }*/


}
