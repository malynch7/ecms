package com.beigeoranges.ecms.Dao;

import com.beigeoranges.ecms.Model.TravelForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.sql.DataSource;

@Repository
@Transactional
public class TravelDao extends JdbcDaoSupport {

    @Autowired
    public TravelDao(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    @Autowired
    static
    JdbcTemplate jdbcTemplate;

    public void createFlight(TravelForm form){

        String sqlCreateFlight ="INSERT INTO Travel_Information (flight_airline, flight_depart, flight_arrival, arrival_terminal, flight_confirmation, user_id, event_id, departure_terminal) VALUE(?,?,?,?,?,?,?,?)";
        String flightAirline = form.getAirline();
        String flightDepart = form.getDepartureTime();
        String flightArrival = form.getArrivalTime();
        String arrivalTerminal = form.getArrivalTerminal();
        String flightConfirmation = form.getConfirmationCode();
        int userId = form.getUserId();
        int eventId = form.getEventId();
        String departureTerminal = form.getDepartureTerminal();


        getJdbcTemplate().update(sqlCreateFlight, flightAirline, flightDepart, flightArrival, arrivalTerminal, flightConfirmation, userId, eventId, departureTerminal);

    }

}