package com.pavlovich.wagon;

import com.pavlovich.domain.user.Machinist;
import com.pavlovich.domain.user.Passenger;
import com.pavlovich.domain.user.Ticket;
import com.pavlovich.domain.wagon.Freight;
import com.pavlovich.domain.wagon.FreightWagon;
import com.pavlovich.domain.wagon.Locomotive;
import com.pavlovich.domain.wagon.PassengerWagon;

public class WagonSample {
    public static final int DEFAULT_WAGON_NUMBER = 1;
    public static final int DEFAULT_CAPACITY = 5;
    public static final int DEFAULT_MAX_WEIGHT = 5;
    public static final int DEFAULT_MACHINIST_AGE = 18;
    private static final int DEFAULT_WAGON_MAX_WEIGHT = 10;

    public static FreightWagon getFreightWagon() {
        return new FreightWagon(DEFAULT_WAGON_NUMBER, DEFAULT_WAGON_MAX_WEIGHT);
    }

    public static FreightWagon getFreightWagon(int maxWeight) {
        return new FreightWagon(DEFAULT_WAGON_NUMBER, maxWeight);
    }

    public static FreightWagon getFilledFreightWagon(int freightsAmount){
        int maxWeight = 0;
        for (int i = 0; i < freightsAmount; i++) {
            maxWeight += i * 10;
        }

        FreightWagon wagon = getFreightWagon(maxWeight);

        for (int i = 1; i < freightsAmount; i++) {
            wagon.addFreight(new Freight(i, i * 10, i * 100));
        }
        return wagon;
    }

    public static Machinist getMachinist(boolean hasLicense){
        return new Machinist("testFirstName", "testLastName", DEFAULT_MACHINIST_AGE, hasLicense);
    }

    public static Locomotive getLocomotive(){
        return new Locomotive(DEFAULT_WAGON_NUMBER, getMachinist(true));
    }

    public static Passenger getPassenger(int wagonNumber, int seatNumber){
        Ticket ticket = new Ticket(wagonNumber, seatNumber);
        return new Passenger("testFirstName", "testLastName", DEFAULT_MACHINIST_AGE, ticket);
    }

    public static PassengerWagon getPassengerWagon() {
        return new PassengerWagon(DEFAULT_WAGON_NUMBER, DEFAULT_CAPACITY);
    }
}
