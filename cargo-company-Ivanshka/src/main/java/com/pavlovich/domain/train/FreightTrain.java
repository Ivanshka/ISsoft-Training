package com.pavlovich.domain.train;

import com.pavlovich.domain.wagon.Freight;
import com.pavlovich.domain.wagon.FreightWagon;
import com.pavlovich.domain.wagon.Locomotive;
import com.pavlovich.domain.wagon.Wagon;
import com.pavlovich.exception.ElementAlreadyAdded;

import java.util.function.ToIntFunction;

import static com.google.common.base.Preconditions.checkNotNull;

public class FreightTrain extends AbstractTrain {

    public FreightTrain(int number, Locomotive locomotive) {
        super(number, locomotive);
    }

    @Override
    public void addWagon(Wagon wagon) {
        checkNotNull(wagon,"Wagon can't be equal null");

        if (wagon instanceof FreightWagon){
            if (!wagons.contains(wagon))
                wagons.add(wagon);
            else
                throw new ElementAlreadyAdded("Can't add specified wagon cause wagon is already exits.");
        }
        else
            throw new IllegalArgumentException("Specified wagon is not freight wagon");
    }

    public int getTotalFreightCost() {
        return getTotalValue(Freight::getPrice);
    }

    public int getTotalFreightWeight() {
        return getTotalValue(Freight::getWeight);
    }

    private int getTotalValue(ToIntFunction<? super Freight> func){
        int totalValue = 0;
        for (int i = 0; i < wagons.getSize(); i++) {
            FreightWagon wagon = (FreightWagon) getWagon(i);
            int wagonFreightsValue = wagon.getFreights()
                    .stream()
                    .mapToInt(func)
                    .sum();
            totalValue += wagonFreightsValue;
        }
        return totalValue;
    }
}
