package com.pavlovich.user;

import com.pavlovich.domain.user.Passenger;
import com.pavlovich.domain.user.Ticket;
import com.pavlovich.wagon.WagonSample;
import org.junit.Test;

public class PassengerTest {
    @Test(expected = NullPointerException.class)
    public void constructorTest(){
        final int age = 1;
        new Passenger("testFirstName", "testLastName", age, null);
    }
}
