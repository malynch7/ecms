package com.beigeoranges.ecms.Dao;

import com.beigeoranges.ecms.Model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.beigeoranges.ecms.Model.ArchivedEvent;

import javax.sql.DataSource;

@Repository
@Transactional
public class ArchivedEventDao extends JdbcDaoSupport {

    //SQL Queries
    @Autowired
    public ArchivedEventDao(DataSource dataSource) {this.setDataSource(dataSource);}

    public void createArchivedEvent(Event aEvent){
        //should this method also delete this event from events table?
        String sqlCreateEvent ="INSERT INTO Archived_Events (event_id, event_name, event_time, event_address, admin_id) VALUE(?,?,?,?,?)";
        int eventid = aEvent.getEvent_id();
        String eventName = aEvent.getEvent_name();
        String eventTime = aEvent.getEvent_time();
        String eventAddress = aEvent.getEvent_address();
        int adminId = aEvent.getAdmin_id();

        getJdbcTemplate().update(sqlCreateEvent, eventid, eventName, eventTime, eventAddress, adminId);

    }
}
