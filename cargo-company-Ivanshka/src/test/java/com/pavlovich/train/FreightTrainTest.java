package com.pavlovich.train;

import com.pavlovich.domain.train.FreightTrain;
import com.pavlovich.domain.wagon.FreightWagon;
import com.pavlovich.domain.wagon.PassengerWagon;
import com.pavlovich.domain.wagon.Wagon;
import com.pavlovich.exception.ElementAlreadyAdded;
import com.pavlovich.wagon.WagonSample;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FreightTrainTest {
    private FreightTrain train;

    @Before
    public void before(){
        train = TrainSample.getFreightTrain();
    }

    @Test
    public void addFreightWagonTest() {
        FreightWagon expected = WagonSample.getFreightWagon();
        train.addWagon(expected);
        Wagon actual = train.getWagon(0);

        assertEquals(expected, actual);
    }

    @Test(expected = ElementAlreadyAdded.class)
    public void addSeveralSameWagonsTest() {
        FreightWagon expected = WagonSample.getFreightWagon();
        train.addWagon(expected);
        train.addWagon(expected);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addNotFreightWagonTest(){
        PassengerWagon wagon = new PassengerWagon(WagonSample.DEFAULT_WAGON_NUMBER, WagonSample.DEFAULT_CAPACITY);
        train.addWagon(wagon);
    }

    @Test
    public void getTotalFreightWeightTest(){
        final int wagonsAmount = 10;
        final int expectedTotalWeight = TrainSample.getTrainWithFilledFreightWagonsExpectedValue(wagonsAmount);

        train = TrainSample.getTrainWithFilledFreightWagons(wagonsAmount);
        int actualTotalWeight = train.getTotalFreightWeight();

        assertEquals("Total freight weight is not equal to expected.", expectedTotalWeight, actualTotalWeight);
    }

    @Test
    public void getTotalFreightCostTest(){
        final int wagonsAmount = 10;
        final int expectedTotalCost = TrainSample.getTrainWithFilledFreightWagonsExpectedValue(wagonsAmount);

        train = TrainSample.getTrainWithFilledFreightWagons(wagonsAmount);
        int actualTotalCost = train.getTotalFreightCost();

        assertEquals("Total freight cost is not equal to expected.", expectedTotalCost, actualTotalCost);
    }
}
