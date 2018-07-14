package com.beigeoranges.ecms.Mapper;

import com.beigeoranges.ecms.Model.Event;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EventMapper implements RowMapper<Event> {

    public static final String BASE_SQL //
        = "Select e.event_id, e.event_name, e.event_time, e.event_address, e.admin_id, e.handbook_file_path, e.event_date FROM events e";

    @Override
    public Event mapRow(ResultSet rs, int rowNum) throws SQLException {
        int event_id = rs.getInt("event_id");
        String event_name = rs.getString("event_name");
        String event_time = rs.getString("event_time");
        String event_address = rs.getString("event_address");
        int admin_id  = rs.getInt("admin_id");
        String handbook_file_path = rs.getString("handbook_file_path");
        String event_date  = rs.getString("event_date");

        Event aEvent = new Event(event_id, event_name, event_time, event_address, admin_id,event_date);
        return aEvent;
    }
}
