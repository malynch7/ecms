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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Repository
@Transactional
public class ArchivedEventDao extends JdbcDaoSupport {

    @Autowired
    public ArchivedEventDao(DataSource dataSource) {this.setDataSource(dataSource);}

    public void archiveEvent(Event event){
        String sql ="UPDATE events SET archive = 1 WHERE event_id = ?";
        int eventId = event.getEvent_id();
        getJdbcTemplate().update(sql, new Object[] {eventId});
    }

    public List<Event> getArchivedEvents() {
        String sql = "SELECT * FROM events WHERE archive = 1";
        try {
            List<Event> archivedEvents = getJdbcTemplate().query(sql, new EventMapper());
            archivedEvents.sort(Comparator.comparing(Event::toDateObject));
            Collections.reverse(archivedEvents);
            return archivedEvents;
        } catch (Exception e) {
            return null;
        }

    }

    public List<Event> getPlayersArchivedEvents(int userId) {
        String sqlArchivedEventsIds = "SELECT event_id FROM registered_to WHERE user_id = ?";
        String sqlArchivedEvents = "SELECT * FROM events WHERE event_id = ? AND archive = 1";
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
        archivedEvents.sort(Comparator.comparing(Event::toDateObject));
        Collections.reverse(archivedEvents);
        return archivedEvents;
    }
}

