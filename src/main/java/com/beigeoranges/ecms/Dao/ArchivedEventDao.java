package com.beigeoranges.ecms.Dao;

import com.beigeoranges.ecms.Mapper.EventMapper;
import com.beigeoranges.ecms.Model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class ArchivedEventDao extends JdbcDaoSupport {

    @Autowired
    public ArchivedEventDao(DataSource dataSource) {this.setDataSource(dataSource);}

<<<<<<< HEAD
    public void createArchivedEvent(Event aEvent){
        //should this method also delete this event from events table?
        String sqlCreateEvent ="INSERT INTO Archived_Events (event_id, event_name, event_time, event_address, admin_id) VALUE(?,?,?,?,?)";
        int eventid = aEvent.getEvent_id();
        String eventName = aEvent.getEvent_name();
        String eventTime = aEvent.getEvent_time();
        String eventAddress = aEvent.getEvent_address();
        int adminId = aEvent.getAdmin_id();
=======
    public void archiveEvent(Event event){
        String sql ="INSERT INTO Archived_Events (event_id, event_name, event_time, event_address, admin_id, event_date) VALUE(?,?,?,?,?,?)";
        int eventId = event.getEvent_id();
        String eventName = event.getEvent_name();
        String eventTime = event.getEvent_time();
        String eventAddress = event.getEvent_address();
        int adminId = event.getAdmin_id();
        String eventDate = event.getEvent_date();

        getJdbcTemplate().update(sql, eventId, eventName, eventTime, eventAddress, adminId, eventDate);

        deleteEvent(event);
    }

    private void deleteEvent(Event event) {
        String sql ="DELETE FROM events WHERE event_id = ?";

        getJdbcTemplate().update(sql, event.getEvent_id());
    }
>>>>>>> e3ff97fa98c9c951a054f5467530267d7cbc6762

    public List<Event> getArchivedEvents() {
        String sql = "SELECT * FROM Archived_Events";

        try {
            return getJdbcTemplate().query(sql, new EventMapper());
        } catch (Exception e) {
            return null;
        }
    }

    public List<Event> getPlayersArchivedEvents(int userId) {
        String sqlArchivedEventsIds = "SELECT event_id FROM registered_to WHERE user_id = ?";
        String sqlArchivedEvents = "SELECT * FROM archived_events WHERE event_id = ?";
        Object[] params = new Object[]{userId};

        List<Integer> archivedEventsIds = getJdbcTemplate().queryForList(sqlArchivedEventsIds, params, Integer.class);

        List<Event> archivedEvents = new ArrayList<>();
        EventMapper mapper = new EventMapper();

        for (int eventId : archivedEventsIds) {
            try {
                archivedEvents.add(getJdbcTemplate().queryForObject(sqlArchivedEvents, new Object[]{eventId}, mapper));

            } catch (EmptyResultDataAccessException e) {

            }
        }
        return archivedEvents;
    }
}

