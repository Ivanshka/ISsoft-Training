package com.pavlovich.user;

import com.pavlovich.domain.user.Ticket;
import com.pavlovich.wagon.WagonSample;
import org.junit.Before;
import org.junit.Test;

public class TicketTest {
    @Test(expected = IllegalArgumentException.class)
    public void constructorTest(){
        final int invalidSeatNumber = 0;
        new Ticket(WagonSample.DEFAULT_WAGON_NUMBER, invalidSeatNumber);
    }
}
