package com.pavlovich;

import com.pavlovich.domain.train.*;
import com.pavlovich.domain.user.Machinist;
import com.pavlovich.domain.user.Passenger;
import com.pavlovich.domain.user.Ticket;
import com.pavlovich.domain.train.PassengerTrain;
import com.pavlovich.domain.wagon.Locomotive;
import com.pavlovich.domain.wagon.PassengerWagon;
import com.pavlovich.list.List;

public class Runner {
    public static void main(String[] args) {

        Machinist machinist = new Machinist("Ivan", "Pavlovich", 20, true);
        Locomotive locomotive = new Locomotive(1, machinist);
        locomotive.setMachinist(machinist);
        Train train = new PassengerTrain(123, locomotive);
        var pasCarriage = new PassengerWagon(2, 5);

        Ticket artemTicket = new Ticket(2, 1);
        Ticket unknownTicket = new Ticket(2, 2);
        Passenger artemPass = new Passenger("Artem", "Pavlovich", 22, artemTicket);
        Passenger unknownPass = new Passenger("Unknown", "Unknown", 1, unknownTicket);

        pasCarriage.addPassenger(artemPass);
        pasCarriage.addPassenger(unknownPass);

        train.addWagon(locomotive);
        train.addWagon(pasCarriage);
    }
}
