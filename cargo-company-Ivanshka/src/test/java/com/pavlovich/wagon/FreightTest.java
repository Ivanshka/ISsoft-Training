package com.pavlovich.wagon;

import com.pavlovich.domain.user.Passenger;
import com.pavlovich.domain.user.Ticket;
import com.pavlovich.domain.wagon.Freight;
import org.junit.Test;

public class FreightTest {
    @Test(expected = IllegalArgumentException.class)
    public void constructorTest(){
        final int number = 1;
        final int invalidWeight = 0;
        final int invalidPrice = 0;
        new Freight(number, invalidWeight, invalidPrice);
    }
}
