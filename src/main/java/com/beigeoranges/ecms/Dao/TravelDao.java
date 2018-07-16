package com.beigeoranges.ecms.Dao;

import com.beigeoranges.ecms.Mapper.TravelMapper;
import com.beigeoranges.ecms.Model.TravelForm;
import com.beigeoranges.ecms.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
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

        String sqlCreateFlight ="INSERT INTO travel_information (flight_airline, flight_depart, flight_arrival, " +
                "arrival_terminal, flight_confirmation, user_id, event_id, departure_terminal) VALUE(?,?,?,?,?,?,?,?)";
        String flightAirline = form.getAirline();
        String flightDepart = form.getDepartureTime();
        String flightArrival = form.getArrivalTime();
        String arrivalTerminal = form.getArrivalTerminal();
        String flightConfirmation = form.getConfirmationCode();
        int userId = form.getUserId();
        int eventId = form.getEventId();
        String departureTerminal = form.getDepartureTerminal();

        try{
            getJdbcTemplate().update(sqlCreateFlight, flightAirline, flightDepart, flightArrival, arrivalTerminal,
                    flightConfirmation, userId, eventId, departureTerminal);

        }catch(DuplicateKeyException e){
            String sql = "UPDATE travel_information SET flight_airline=?, flight_depart=?, flight_arrival=?, " +
                    "arrival_terminal=?, flight_confirmation=?, departure_terminal=? WHERE user_id = ? AND event_id = ?";

            getJdbcTemplate().update(sql, new Object[] {flightAirline, flightDepart, flightArrival, arrivalTerminal,
                    flightConfirmation, departureTerminal, userId, eventId});
        }
    }

    public TravelForm getTravelInfo(int eventid, int userid){

        String sql = "Select * From travel_information WHERE event_id = ? AND user_id = ?";
        try {
            TravelForm travelForm = this.getJdbcTemplate().queryForObject(sql, new Object[] { eventid, userid } ,
                    new TravelMapper());
            return travelForm;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

}