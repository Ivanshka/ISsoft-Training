package com.pavlovich.wagon;

import com.pavlovich.domain.wagon.Freight;
import com.pavlovich.domain.wagon.FreightWagon;
import com.pavlovich.domain.wagon.Locomotive;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class FreightWagonTest {
    private FreightWagon wagon;

    @Before
    public void before(){
        wagon = WagonSample.getFreightWagon();
    }

    @Test
    public void addFreightTest(){
        final int defaultFreightNumber = 0;
        final int weight = 10;
        final int price = 100;
        Freight expected = new Freight(defaultFreightNumber, weight, price);
        wagon.addFreight(expected);
        Freight actual = wagon.getFreight(0);

        assertEquals("Freight was not added", expected, actual);
    }

    @Test
    public void unloadFreightsTest(){
        final int freightsAmountInEmptyWagon = 0;
        final int freightsAmount = 10;
        wagon = WagonSample.getFilledFreightWagon(freightsAmount);
        wagon.unloadFreights();

        assertEquals("Wagon should be empty.", freightsAmountInEmptyWagon, wagon.getFreightsAmount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorTest(){
        final int invalidMaxWeight = 0;
        new FreightWagon(WagonSample.DEFAULT_WAGON_NUMBER, invalidMaxWeight);
    }
}
