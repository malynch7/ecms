package com.beigeoranges.ecms.Dao;

import com.beigeoranges.ecms.Model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@Repository
@Transactional
public class EventDao extends JdbcDaoSupport {

    @Autowired
    public EventDao(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public void createEvent(Event form){

        String sqlCreateEvent ="INSERT INTO Events (event_name, event_time, event_address, admin_id) VALUE(?,?,?,?)";
        String eventName = form.getEvent_name();
        String eventTime = form.getEvent_time();
        String eventAddress = form.getEvent_address();
        int adminId  = form.getAdmin_id();

        getJdbcTemplate().update(sqlCreateEvent, eventName, eventTime, eventAddress, adminId);

    }
}
