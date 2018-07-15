package com.beigeoranges.ecms.Dao;

import com.beigeoranges.ecms.Mapper.EventMapper;
import com.beigeoranges.ecms.Model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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

        String sqlCreateEvent ="INSERT INTO Events (event_name, event_time, event_address, admin_id, event_date, archive) VALUE(?,?,?,?,?,0)";
        String eventName = form.getEvent_name();
        String eventTime = form.getEvent_time();
        String eventAddress = form.getEvent_address();
        int adminId  = form.getAdmin_id();
        String eventDate = form.getEvent_date();
        //int eventId = getMaxEventId() + 1;

        getJdbcTemplate().update(sqlCreateEvent, eventName, eventTime, eventAddress, adminId, eventDate);

    }

    public List<Event> getAllEvents(){
        String sqlGetAllEvents = "SELECT * FROM events WHERE archive = 0";

        try {
            return getJdbcTemplate().query(sqlGetAllEvents, new EventMapper());
        } catch (Exception e) {
            return null;
        }
    }
    public List<Event> getInvitedEvents(int userId){


        String sqlInvitedEventsIds = "SELECT event_id FROM registered_to WHERE RSVP = 0 AND user_id = ?";
        String sqlInvitedEvents = "SELECT * FROM events WHERE event_id = ? AND archive = 0";
        Object[] params = new Object[] { userId };

        List<Integer> invitedEventsIds = getJdbcTemplate().queryForList(sqlInvitedEventsIds,params, Integer.class);

        System.out.println(invitedEventsIds.toString());

        List<Event> invitedEvents = new ArrayList<>();
        EventMapper mapper = new EventMapper();
        for(int eventId : invitedEventsIds){

            try {
                invitedEvents.add(getJdbcTemplate().queryForObject(sqlInvitedEvents, new Object[]{eventId}, mapper));

            } catch (EmptyResultDataAccessException e) {

            }
        }
        System.out.println(invitedEvents.toString());
        return invitedEvents;
    }

    public List<Event> getConfirmedEvents(int userId) {


        String sqlConfirmedEventsIds = "SELECT event_id FROM registered_to WHERE RSVP = 1 AND user_id = ?";
        String sqlConfirmedEvents = "SELECT * FROM events WHERE event_id = ? AND archive = 0";

        List<Integer> confirmedEventsIds = getJdbcTemplate().queryForList(sqlConfirmedEventsIds,new Object[] { userId }, Integer.class);

        System.out.println(confirmedEventsIds.toString());

        List<Event> confirmedEvents = new ArrayList<>();
        EventMapper mapper = new EventMapper();
        for(int eventId : confirmedEventsIds){

            try {
                confirmedEvents.add(getJdbcTemplate().queryForObject(sqlConfirmedEvents, new Object[]{eventId}, mapper));
            } catch (EmptyResultDataAccessException e) {

            }
        }
        System.out.println(confirmedEvents.toString());
        return confirmedEvents;
    }

    public void RSVP(int eventId, int userId){
        String sql = "UPDATE registered_to SET RSVP = 1 WHERE event_id = ? AND user_id = ?";
        getJdbcTemplate().update(sql, new Object[] {eventId, userId});
    }

    public void Invite(int eventId, int userId){
        int eventid;

        String sqlCheck = "SELECT RSVP FROM registered_to WHERE event_id = ? AND user_id = ?";
        int result;
        try {
            result = getJdbcTemplate().queryForObject(sqlCheck, new Object[] {eventId, userId}, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            result=5;
        }
        if(result != 0 && result != 1){
            String sql = "INSERT INTO registered_to(event_id, user_id, RSVP) VALUES (?,?,?)";
            getJdbcTemplate().update(sql, new Object[] {eventId, userId, 0});
        }

    }

    public Event getEventById(int eventId){

        String sql = "SELECT * FROM events WHERE event_id = ?";
        return getJdbcTemplate().queryForObject(sql, new Object[] {eventId}, new EventMapper());
    }
}

