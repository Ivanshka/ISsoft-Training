package com.pavlovich.domain.user;

import static com.google.common.base.Preconditions.checkNotNull;

public class Passenger extends User {
    private final Ticket ticket;

    public Passenger(String firstName, String lastName, int age, Ticket ticket) {
        super(firstName, lastName, age);

        checkNotNull(ticket, "Ticket can't be equal null");

        this.ticket = ticket;
    }

    public Ticket getTicket() {
        return ticket;
    }
}
