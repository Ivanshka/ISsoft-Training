package com.pavlovich.train;

import com.pavlovich.domain.train.PassengerTrain;
import com.pavlovich.domain.wagon.FreightWagon;
import com.pavlovich.domain.wagon.PassengerWagon;
import com.pavlovich.domain.wagon.Wagon;
import com.pavlovich.exception.ElementAlreadyAdded;
import com.pavlovich.wagon.WagonSample;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PassengerTrainTest {
    private PassengerTrain train;

    @Before
    public void before(){
        train = TrainSample.getPassengerTrain();
    }

    @Test(expected = ElementAlreadyAdded.class)
    public void addPassengerWagonTest(){
        PassengerWagon expected = new PassengerWagon(WagonSample.DEFAULT_WAGON_NUMBER, WagonSample.DEFAULT_CAPACITY);
        train.addWagon(expected);
        Wagon actual = train.getWagon(0);

        assertEquals("Passenger wagon can be not added Can't add passenger wagon", expected, actual);

        train.addWagon(expected);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addNotPassengerWagonTest(){
        FreightWagon wagon = new FreightWagon(WagonSample.DEFAULT_WAGON_NUMBER, WagonSample.DEFAULT_MAX_WEIGHT);
        train.addWagon(wagon);
    }

    @Test
    public void getPassengersCountTest(){
        final int wagonsAmount = 10;
        int expectedPassengerAmount = TrainSample.getTrainWithFilledPassengerWagonsExpectedValue(wagonsAmount);

        train = TrainSample.getTrainWithFilledPassengerWagons(wagonsAmount);
        int actualTotalCost = train.getPassengersCount();

        assertEquals("Total passengers counter is not equal to expected.", expectedPassengerAmount, actualTotalCost);
    }
}
