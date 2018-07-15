package com.beigeoranges.ecms.Dao;

import com.beigeoranges.ecms.Mapper.ArchivedMapper;
import com.beigeoranges.ecms.Model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.beigeoranges.ecms.Model.ArchivedEvent;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Transactional
public class ArchivedEventDao extends JdbcDaoSupport {

    @Autowired
    public ArchivedEventDao(DataSource dataSource) {this.setDataSource(dataSource);}

    public void createArchivedEvent(Event event){
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

    public List<ArchivedEvent> getArchivedEvents() {
        String sql = "SELECT * FROM Archived_Events";

        try {
            return getJdbcTemplate().query(sql, new ArchivedMapper());
        } catch (Exception e) {
            return null;
        }
    }
}
