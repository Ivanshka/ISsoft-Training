package com.pavlovich.domain.train;

import com.pavlovich.domain.wagon.Locomotive;
import com.pavlovich.domain.wagon.PassengerWagon;
import com.pavlovich.domain.wagon.Wagon;
import com.pavlovich.exception.ElementAlreadyAdded;

import static com.google.common.base.Preconditions.checkNotNull;

public class PassengerTrain extends AbstractTrain {
    public PassengerTrain(int number, Locomotive locomotive) {
        super(number, locomotive);
    }

    @Override
    public void addWagon(Wagon wagon) {
        checkNotNull(wagon,"Wagon can't be equal null.");

        if (wagon instanceof PassengerWagon) {
            if (!wagons.contains(wagon))
                wagons.add(wagon);
            else
                throw new ElementAlreadyAdded("Can't add specified wagon: wagon is already exits.");
        }
        else
            throw new IllegalArgumentException("Specified wagon is not passenger wagon.");
    }

    public int getPassengersCount() {
        int totalPassengersCount = 0;

        for (int i = 0; i < wagons.getSize(); i++) {
            PassengerWagon wagon = (PassengerWagon) getWagon(i);
            totalPassengersCount += wagon.getPassengersAmount();
        }

        return totalPassengersCount;
    }
}
