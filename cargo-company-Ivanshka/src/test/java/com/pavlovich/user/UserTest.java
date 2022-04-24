package com.pavlovich.user;

import com.pavlovich.domain.user.Passenger;
import com.pavlovich.domain.user.Ticket;
import org.junit.Before;
import org.junit.Test;

public class UserTest {
    private static final int INVALID_AGE = 0;
    private static final int WAGON_NUMBER = 1;
    private static final int SEAT_NUMBER = 1;

    private Ticket ticket;

    @Before
    public void before() {
        ticket = new Ticket(WAGON_NUMBER, SEAT_NUMBER);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorCheckTicketSeatNumberTest() {
        new Passenger("testFirstName", "testLastName", INVALID_AGE, ticket);
    }

    @Test(expected = NullPointerException.class)
    public void constructorCheckFirstNameTest() {
        new Passenger(null, "testLastName", INVALID_AGE, ticket);
    }

    @Test(expected = NullPointerException.class)
    public void constructorCheckLastNameTest() {
        new Passenger(null, "testLastName", INVALID_AGE, ticket);
    }
}
