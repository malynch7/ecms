package com.beigeoranges.ecms.Dao;

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

    public void createArchivedEvent(ArchivedEvent form){
        String sqlCreateEvent ="INSERT INTO Archived_Events (event_name, event_time, event_address, admin_id) VALUE(?,?,?,?)";
        String eventName = form.getEvent_name();
        String eventTime = form.getEvent_time();
        String eventAddress = form.getEvent_address();
        int adminId = form.getAdmin_id();

        getJdbcTemplate().update(sqlCreateEvent, eventName, eventTime, eventAddress, adminId);

    }
}
