package com.beigeoranges.ecms.Model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EventTest {

    Event aEvent = new Event(12, "Halloween", "11:30", "Scary Lane", 31);

    @Before
    public void initialize(){
        aEvent = new Event(12, "Halloween", "11:30", "Scary Lane", 31);
    }
    @Test
    public void getAdmin_id() {
        assertSame(31, aEvent.getAdmin_id());
    }


    @Test
    public void setAdmin_id() {
        aEvent.setAdmin_id(30);
        assertSame(30, aEvent.getAdmin_id());
    }

    @Test
    public void getEvent_id() {
        assertSame(12, aEvent.getEvent_id());
    }

    @Test
    public void setEvent_id() {
        aEvent.setEvent_id(40);
        assertSame(40, aEvent.getEvent_id());
    }

    @Test
    public void getEvent_name() {
        assertEquals("Halloween", aEvent.getEvent_name());

    }

    @Test
    public void setEvent_name() {
        aEvent.setEvent_name("Spooky");
        assertEquals("Spooky", aEvent.getEvent_name());
    }

    @Test
    public void getEvent_time() {
        assertEquals("11:30", aEvent.getEvent_time());
    }

    @Test
    public void setEvent_time() {
        aEvent.setEvent_time("12:00");
        assertEquals("12:00", aEvent.getEvent_time());

    }

    @Test
    public void getEvent_address() {
        assertEquals("Scary Lane", aEvent.getEvent_address());


    }

    @Test
    public void setEvent_address() {
        aEvent.setEvent_address("Haunted Ave");
        assertEquals("Haunted Ave", aEvent.getEvent_address());
    }
}