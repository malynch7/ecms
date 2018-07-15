package com.beigeoranges.ecms.Mapper;

import com.beigeoranges.ecms.Model.ArchivedEvent;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ArchivedMapper implements RowMapper<ArchivedEvent> {

    public static final String BASE_SQL //
            = "Select * FROM Archived_Events";

    @Override
    public ArchivedEvent mapRow(ResultSet rs, int rowNum) throws SQLException {
        int event_id = rs.getInt("event_id");
        String event_name = rs.getString("event_name");
        String event_time = rs.getString("event_time");
        String event_address = rs.getString("event_address");
        int admin_id  = rs.getInt("admin_id");
        String event_date  = rs.getString("event_date");

        ArchivedEvent aEvent = new ArchivedEvent(event_id, event_name, event_time, event_address, admin_id, event_date);
        return aEvent;
    }
}
