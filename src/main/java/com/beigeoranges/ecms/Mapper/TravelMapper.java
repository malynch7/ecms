package com.beigeoranges.ecms.Mapper;

import com.beigeoranges.ecms.Model.TravelForm;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TravelMapper implements RowMapper<TravelForm> {

    public static final String BASE_SQL //
            = "Select * From travel_information";

    @Override
    public TravelForm mapRow(ResultSet rs, int rowNum) throws SQLException {

        int userId = rs.getInt("user_Id");
        int eventId = rs.getInt("event_Id");
        String airline = rs.getString("flight_airline");
        String departureTime = rs.getString("flight_depart");
        String arrivalTime = rs.getString("flight_arrival");
        String arrivalTerminal = rs.getString("arrival_terminal");
        String departureTerminal = rs.getString("departure_terminal");
        String confirmationCode = rs.getString("flight_confirmation");


        return new TravelForm(userId, eventId, airline, departureTime, arrivalTime, arrivalTerminal, departureTerminal, confirmationCode);
    }
}
