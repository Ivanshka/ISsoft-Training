package com.pavlovich.domain.wagon;

import com.google.common.base.Preconditions;
import com.pavlovich.domain.user.Passenger;
import com.pavlovich.domain.user.Ticket;

import java.util.*;

public class PassengerWagon extends AbstractWagon {
    private final Map<Integer, Passenger> passengers;
    private final int capacity;

    public PassengerWagon(int wagonNumber, int capacity) {
        super(wagonNumber);

        Preconditions.checkArgument(capacity > 0, "Capacity should be positive.");

        this.passengers = new HashMap<>(capacity);
        this.capacity = capacity;
    }

    public Set<Map.Entry<Integer, Passenger>> getPassengers() {
        return Set.copyOf(passengers.entrySet());
    }

    public void addPassenger(Passenger passenger) {
        Preconditions.checkNotNull(passenger, "Specified passenger is null");

        Ticket ticket = passenger.getTicket();

        if (ticket.getWagonNumber() == number) {
            int seatNumber = ticket.getSeatNumber();
            if (isPlaceEmpty(seatNumber))
                passengers.put(seatNumber, passenger);
            else
                throw new IllegalArgumentException("Seat is already taken");
        }
        else
            throw new IllegalArgumentException("Wagon number is incorrect");
    }

    public boolean isPlaceEmpty(int seatNumber) {
        Passenger currentPassenger = passengers.get(seatNumber);
        return currentPassenger == null;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getPassengersAmount() {
        return passengers.size();
    }
}
