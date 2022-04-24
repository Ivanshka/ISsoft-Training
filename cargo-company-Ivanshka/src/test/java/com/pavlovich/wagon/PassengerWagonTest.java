package com.pavlovich.wagon;

import com.pavlovich.domain.user.Passenger;
import com.pavlovich.domain.wagon.FreightWagon;
import com.pavlovich.domain.wagon.PassengerWagon;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PassengerWagonTest {
    private PassengerWagon wagon;

    @Before
    public void before(){
        wagon = WagonSample.getPassengerWagon();
    }

    @Test
    public void isPlaceEmptyTest(){
        final int freeSeatNumber = 1;
        Passenger passenger = WagonSample.getPassenger(WagonSample.DEFAULT_WAGON_NUMBER, freeSeatNumber);
        wagon.addPassenger(passenger);

        assertFalse("Seat number has been already occupied.", wagon.isPlaceEmpty(freeSeatNumber));
    }

    @Test(expected = IllegalArgumentException.class)
    public void addPassengerTest(){
        final int freeSeatNumber = 1;
        Passenger passenger = WagonSample.getPassenger(WagonSample.DEFAULT_WAGON_NUMBER, freeSeatNumber);
        wagon.addPassenger(passenger);
        final int expected = 1;
        final int actual = wagon.getPassengersAmount();
        assertEquals("Passenger was not added",expected, actual);

        passenger = WagonSample.getPassenger(WagonSample.DEFAULT_WAGON_NUMBER, freeSeatNumber);
        wagon.addPassenger(passenger);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorTest(){
        final int invalidCapacity = 0;
        new PassengerWagon(WagonSample.DEFAULT_WAGON_NUMBER, invalidCapacity);
    }
}
