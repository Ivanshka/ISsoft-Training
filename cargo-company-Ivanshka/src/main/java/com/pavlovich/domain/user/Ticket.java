package com.pavlovich.domain.user;

import com.google.common.base.Preconditions;

public class Ticket {
    private final int wagonNumber;
    private final int seatNumber;

    public Ticket(int wagonNumber, int seatNumber) {
        Preconditions.checkArgument(seatNumber > 0, "Seat number can't be less than 0.");

        this.wagonNumber = wagonNumber;
        this.seatNumber = seatNumber;
    }

    public int getWagonNumber() {
        return wagonNumber;
    }

    public int getSeatNumber() {
        return seatNumber;
    }
}
