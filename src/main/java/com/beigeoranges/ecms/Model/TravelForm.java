package com.beigeoranges.ecms.Model;

public class TravelForm{


    private int userId;
    private int eventId;
    private String airline;
    private String departureTime;
    private String arrivalTime;
    private String arrivalTerminal;
    private String departureTerminal;
    private String confirmationCode;

    public TravelForm(){

    }

    public TravelForm(int userId, int eventId, String airline,String departureTime,String arrivalTime, String arrivalTerminal,String departureTerminal, String confirmationCode){

        this.userId = userId;
        this.eventId = eventId;
        this.airline = airline;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.arrivalTerminal = arrivalTerminal;
        this.departureTerminal = departureTerminal;
        this.confirmationCode = confirmationCode;
    }

    public String getConfirmationCode() {
        return confirmationCode;
    }

    public void setConfirmationCode(String confirmationCode) {
        this.confirmationCode = confirmationCode;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getArrivalTerminal() {
        return arrivalTerminal;
    }

    public void setArrivalTerminal(String arrivalTerminal) {
        this.arrivalTerminal = arrivalTerminal;
    }

    public String getDepartureTerminal() {
        return departureTerminal;
    }

    public void setDepartureTerminal(String departureTerminal) {
        this.departureTerminal = departureTerminal;
    }
}
