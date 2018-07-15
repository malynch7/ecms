package com.beigeoranges.ecms.Dao;

import com.beigeoranges.ecms.Mapper.EventMapper;
import com.beigeoranges.ecms.Model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class EventDao extends JdbcDaoSupport {

    @Autowired
    public EventDao(DataSource dataSource) {
        this.setDataSource(dataSource);
    }
    private static List<String> eventList;

    @Autowired
    static
    JdbcTemplate jdbcTemplate;

    public void createEvent(Event form){

        String sqlCreateEvent ="INSERT INTO Events (event_name, event_time, event_address, admin_id,event_date) VALUE(?,?,?,?,?)";
        String eventName = form.getEvent_name();
        String eventTime = form.getEvent_time();
        String eventAddress = form.getEvent_address();
        int adminId  = form.getAdmin_id();
        String eventDate = form.getEvent_date();

        getJdbcTemplate().update(sqlCreateEvent, eventName, eventTime, eventAddress, adminId, eventDate);

    }
    // The below method creates a list of all the events to be used in a dropdown menu
    public static List<String> curEvents(){
        String sqlAllEvents = "SELECT event_name FROM events";

        eventList = jdbcTemplate.queryForList(sqlAllEvents, String.class);
        return eventList;
    }

    public List<Event> getAllEvents(){
        String sqlGetAllEvents = "SELECT * FROM events";

        try {
            return getJdbcTemplate().query(sqlGetAllEvents, new EventMapper());
        } catch (Exception e) {
            return null;
        }
    }
    public List<Event> getInvitedEvents(int userId){


        String sqlInvitedEventsIds = "SELECT event_id FROM registered_to WHERE RSVP = 0 AND user_id = ?";
        String sqlInvitedEvents = "SELECT * FROM events WHERE event_id = ?";
        Object[] params = new Object[] { userId };

        List<Integer> invitedEventsIds = getJdbcTemplate().queryForList(sqlInvitedEventsIds,params, Integer.class);

        System.out.println(invitedEventsIds.toString());

        List<Event> invitedEvents = new ArrayList<>();
        EventMapper mapper = new EventMapper();
        for(int eventId : invitedEventsIds){
            invitedEvents.add(getJdbcTemplate().queryForObject(sqlInvitedEvents, new Object[]{eventId}, mapper));
        }
        System.out.println(invitedEvents.toString());
        return invitedEvents;
    }

    public List<Event> getConfirmedEvents(int userId) {


        String sqlConfirmedEventsIds = "SELECT event_id FROM registered_to WHERE RSVP = 1 AND user_id = ?";
        String sqlConfirmedEvents = "SELECT * FROM events WHERE event_id = ?";

        List<Integer> confirmedEventsIds = getJdbcTemplate().queryForList(sqlConfirmedEventsIds,new Object[] { userId }, Integer.class);

        System.out.println(confirmedEventsIds.toString());

        List<Event> confirmedEvents = new ArrayList<>();
        EventMapper mapper = new EventMapper();
        for(int eventId : confirmedEventsIds){
            confirmedEvents.add(getJdbcTemplate().queryForObject(sqlConfirmedEvents, new Object[]{eventId}, mapper));
        }
        System.out.println(confirmedEvents.toString());
        return confirmedEvents;
    }

    public void RSVP(int eventId, int userId){
        String sql = "UPDATE registered_to SET RSVP = 1 WHERE event_id = ? AND user_id = ?";
        getJdbcTemplate().update(sql, new Object[] {eventId, userId});
    }

    public void Invite(int eventId, int userId){
        String sql = "INSERT INTO registered_to(event_id, user_id, RSVP) VALUES (?,?,?)";
        getJdbcTemplate().update(sql, new Object[] {eventId, userId}, 0);
    }
}

