package com.beigeoranges.ecms.Dao;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import com.beigeoranges.ecms.Model.Event;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class EventDaoTest {
    EventDao eventDao;
    Event event1 = new Event(1, "OfficeMeeting", "8:00", "Dunder Mifflin", 1);
    Event event2 = new Event(2, "BirthdayParty", "8:00", "Dunder Mifflin", 2);
    Event event3 = new Event(3, "ChristmasParty", "8:00", "Dunder Mifflin", 3);

    @Before
    public void createUserDao(){
        eventDao= mock(EventDao.class);
        eventDao.createEvent(event1);
        eventDao.createEvent(event2);
    }
    @Test
    public void createEvent() {

        eventDao.createEvent(event3);
        Event resultEvent= eventDao.getAllEvents().get(eventDao.getAllEvents().size()-1); //get last event in list of events
        assertEquals("'createEvent' creates an event in list of events: ",resultEvent, event3);
    }

    @Test
    public void curEvents() {
        //how do i make sure that createEvent tests before curEvents? bc the expectedEvents list depends on the createEvent test being executed before curEvents test.
        List<String> resultEvents = eventDao.curEvents();
        List<String> expectedEvents = new ArrayList<String>(){{ add("OfficeMeeting"); add("BirthdayParty"); add("ChristmasParty");}};
        assertEquals("curEvents(current events list) is formulated properly: ", expectedEvents, resultEvents);
    }

    @Test
    public void getAllEvents() {
        List<Event> expectedEvents = new ArrayList<Event>(){{ add(event1); add(event2); add(event3);}};
        List<Event> resultEvents = eventDao.getAllEvents();
        assertEquals("getAllEvents gets all events listed in db", expectedEvents, resultEvents);
    }

    @Test
    public void getInvitedEvents() {
        //make sure user w user id 2 A PLAYER and is invited to events with event1d 1 and 2
        List<Event> resultEvents = eventDao.getInvitedEvents(2);
        List<Event> expectedEvents = new ArrayList<Event>(){{ add(event1); add(event2);}};
        assertEquals("events player is invited to: ", expectedEvents, resultEvents);
    }

    @Test
    public void getConfirmedEvents() {
        //make sure user 2 is confirmed to event with eventid 1
        List<Event> resultEvents = eventDao.getConfirmedEvents(2);
        List<Event> expectedEvents = new ArrayList<Event>(){{ add(event1);}};
    }
}