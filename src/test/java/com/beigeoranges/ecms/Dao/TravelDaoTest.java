package com.beigeoranges.ecms.Dao;

import com.beigeoranges.ecms.Model.TravelForm;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TravelDaoTest {
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void createFlight() {
        //not sure how to test it bc it is just inputting in db and no outputting method is provided.
        TravelForm travelForm = new TravelForm(1, 1, "American", "8:00", "10:00", "G1", "G2", "123" );

    }
}