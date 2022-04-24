package com.pavlovich.train;

import com.pavlovich.domain.train.FreightTrain;
import com.pavlovich.domain.train.PassengerTrain;
import com.pavlovich.domain.train.Train;
import com.pavlovich.domain.user.Machinist;
import com.pavlovich.domain.user.Passenger;
import com.pavlovich.domain.user.Ticket;
import com.pavlovich.domain.wagon.Freight;
import com.pavlovich.domain.wagon.FreightWagon;
import com.pavlovich.domain.wagon.Locomotive;
import com.pavlovich.domain.wagon.PassengerWagon;
import com.pavlovich.wagon.WagonSample;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class TrainSample {

    private static final int DEFAULT_TRAIN_NUMBER = 1;
    private static final int DEFAULT_PASSENGER_TRAIN_CAPACITY = 10;

    public static Machinist getInvalidMachinist(){
        return new Machinist("TestFirstName", "TestLastName",
                WagonSample.DEFAULT_MACHINIST_AGE,false);
    }

    public static Machinist getValidMachinist(){
        return new Machinist("TestFirstName", "TestLastName",
                WagonSample.DEFAULT_MACHINIST_AGE,true);
    }

    public static Train getTrain() {
        return getPassengerTrain();
    }

    public static PassengerTrain getPassengerTrain(){
        var machinist = getValidMachinist();
        var locomotive = new Locomotive(WagonSample.DEFAULT_WAGON_NUMBER, machinist);
        return new PassengerTrain(DEFAULT_TRAIN_NUMBER, locomotive);
    }

    public static PassengerTrain getPassengerTrainWithWagon(){
        PassengerTrain train = getPassengerTrain();
        train.addWagon(new PassengerWagon(WagonSample.DEFAULT_WAGON_NUMBER, DEFAULT_PASSENGER_TRAIN_CAPACITY));
        return train;
    }

    public static FreightTrain getFreightTrain(){
        var machinist = getValidMachinist();
        var locomotive = new Locomotive(WagonSample.DEFAULT_WAGON_NUMBER, machinist);
        return new FreightTrain(DEFAULT_TRAIN_NUMBER, locomotive);
    }

    public static FreightWagon getFilledFreightWagon(int wagonNumber, int... freightWeightsAndPrices) {
        int testMaxWagonWeight = Arrays.stream(freightWeightsAndPrices).sum();
        FreightWagon wagon = new FreightWagon(wagonNumber, testMaxWagonWeight);
        for (int weightPrice : freightWeightsAndPrices) {
            wagon.addFreight(new Freight(wagonNumber, weightPrice, weightPrice));
        }
        return wagon;
    }

    public static PassengerWagon getPassengerWagonWithPassengers(int wagonNumber, int passengersAmount) {
        PassengerWagon wagon = new PassengerWagon(wagonNumber, passengersAmount);
        final int age = 18;
        String firstName = "testFirstName";
        String lastName = "testLastName";
        for (int i = 1; i <= passengersAmount; i++) {
            final Ticket ticket = new Ticket(wagonNumber, i);
            wagon.addPassenger(new Passenger(firstName, lastName, age, ticket));
        }
        return wagon;
    }

    public static FreightTrain getTrainWithFilledFreightWagons(int wagonsAmount){
        FreightTrain train = getFreightTrain();

        for (int i = 0; i < wagonsAmount; i++) {
            int weightPrice1 = i + 10;
            int weightPrice2 = i + 20;
            int weightPrice3 = i + 30;
            FreightWagon wagon = TrainSample.getFilledFreightWagon(i, weightPrice1, weightPrice2, weightPrice3);
            train.addWagon(wagon);
        }

        return train;
    }

    public static int getTrainWithFilledFreightWagonsExpectedValue(int wagonsAmount) {
        int expectedTotalWeightPrice = 0;
        final int totalOffset = 10 + 20 + 30;

        for (int i = 0; i < wagonsAmount; i++) {
            expectedTotalWeightPrice += 3 * i + totalOffset;
        }

        return expectedTotalWeightPrice;
    }

    public static PassengerTrain getTrainWithFilledPassengerWagons(int wagonsAmount) {
        PassengerTrain train = getPassengerTrain();
        for (int i = 0; i < wagonsAmount; i++) {
            int testPassengersAmount = i + 10;
            PassengerWagon wagon = TrainSample.getPassengerWagonWithPassengers(i, testPassengersAmount);
            train.addWagon(wagon);
        }

        return train;
    }

    public static int getTrainWithFilledPassengerWagonsExpectedValue(int wagonsAmount){
        final int totalOffset = 10;
        int expectedPassengerAmount = 0;

        for (int i = 0; i < wagonsAmount; i++) {
            expectedPassengerAmount += i + totalOffset;
        }

        return expectedPassengerAmount;
    }
}
