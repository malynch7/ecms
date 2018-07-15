package com.beigeoranges.ecms.Dao;

import com.beigeoranges.ecms.Mapper.EventMapper;
import com.beigeoranges.ecms.Model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.beigeoranges.ecms.Model.ArchivedEvent;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class ArchivedEventDao extends JdbcDaoSupport {

    //SQL Queries
    @Autowired
    public ArchivedEventDao(DataSource dataSource) {this.setDataSource(dataSource);}

    public void createArchivedEvent(Event aEvent){
        String sqlCreateEvent ="INSERT INTO Archived_Events (event_id, event_name, event_time, event_address, admin_id, event_date) VALUE(?,?,?,?,?,?)";
        int eventid = aEvent.getEvent_id();
        String eventName = aEvent.getEvent_name();
        String eventTime = aEvent.getEvent_time();
        String eventAddress = aEvent.getEvent_address();
        int adminId = aEvent.getAdmin_id();
        String eventDate = aEvent.getEvent_date();

        getJdbcTemplate().update(sqlCreateEvent, eventid, eventName, eventTime, eventAddress, adminId);

    }

    public List<Event> getArchivedEvents(int userId){
        String sqlArchivedEventsIds = "SELECT event_id FROM registered_to WHERE user_id = ?";
        String sqlArchivedEvents = "SELECT * FROM archived_events WHERE event_id = ?";
        Object[] params = new Object[] { userId };

        List<Integer> archivedEventsIds = getJdbcTemplate().queryForList(sqlArchivedEventsIds,params, Integer.class);

        List<Event> archivedEvents = new ArrayList<>();
        EventMapper mapper = new EventMapper();

        for(int eventId : archivedEventsIds){
            try {
                archivedEvents.add(getJdbcTemplate().queryForObject(sqlArchivedEvents, new Object[]{eventId}, mapper));

            } catch (EmptyResultDataAccessException e) {

            }
        }
        return archivedEvents;
    }
}
