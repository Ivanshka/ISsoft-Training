package com.pavlovich.train;

import com.pavlovich.domain.train.FreightTrain;
import com.pavlovich.domain.train.PassengerTrain;
import com.pavlovich.domain.train.Train;
import com.pavlovich.domain.wagon.Locomotive;
import com.pavlovich.domain.wagon.PassengerWagon;
import com.pavlovich.domain.wagon.Wagon;
import com.pavlovich.wagon.WagonSample;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TrainTest {
    private final int DEFAULT_WAGON_NUMBER = 0;
    private final int DEFAULT_CAPACITY = 10;
    private Train train;

    @Before
    public void before(){
        train = TrainSample.getTrain();
    }

    @Test(expected = NullPointerException.class)
    public void setLocomotiveTest(){
        train.setLocomotive(null);
    }

    @Test
    public void addWagonTest(){
        train = TrainSample.getPassengerTrain();
        train.addWagon(new PassengerWagon(DEFAULT_WAGON_NUMBER, DEFAULT_CAPACITY));
        final int expectedAmount = 1;
        final int wagonAmount = train.getWagonAmount();

        assertEquals("Got actual wagon amount is not equal expected", expectedAmount, wagonAmount);
    }

    @Test
    public void removeWagonTest(){
        final int firstWagonIndex = 0;
        final int emptyTrainWagonAmount = 0;
        train = TrainSample.getPassengerTrain();
        train.addWagon(new PassengerWagon(DEFAULT_WAGON_NUMBER, DEFAULT_CAPACITY));
        train.removeWagon(firstWagonIndex);

        assertEquals("Wagon was not removed!", emptyTrainWagonAmount, train.getWagonAmount());
    }

    @Test
    public void getWagonTest(){
        final int firstWagonIndex = 0;
        train = TrainSample.getPassengerTrain();
        Wagon expectedWagon = new PassengerWagon(DEFAULT_WAGON_NUMBER, DEFAULT_CAPACITY);
        train.addWagon(expectedWagon);
        Wagon actualWagon = train.getWagon(firstWagonIndex);

        assertEquals("Got actual wagon is not equal expected", expectedWagon, actualWagon);
    }

    @Test(expected = NullPointerException.class)
    public void constructorTest(){
        new PassengerTrain(WagonSample.DEFAULT_WAGON_NUMBER, null);
    }
}
