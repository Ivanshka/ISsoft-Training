package com.pavlovich.domain.wagon;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

public class FreightWagon extends AbstractWagon {
    private final int maxWeight;
    private int currentWeight;
    private final List<Freight> freights;

    public FreightWagon(int wagonNumber, int maxWeight) {
        super(wagonNumber);

        Preconditions.checkArgument(maxWeight > 0, "Max weight should be positive.");

        this.maxWeight = maxWeight;
        this.freights = new ArrayList<>();
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public List<Freight> getFreights() {
        return List.copyOf(freights);
    }

    public void addFreight(Freight freight) {
        Preconditions.checkNotNull(freight);

        int weight = freight.getWeight();
        if (currentWeight + weight <= maxWeight) {
            freights.add(freight);
            currentWeight += weight;
        } else
            throw new IllegalArgumentException("Freight weight is too large for this wagon");
    }

    public Freight getFreight(int index){
        return freights.get(index);
    }

    public List<Freight> unloadFreights(){
        List<Freight> freightsToUnload = List.copyOf(freights);
        freights.clear();
        return freightsToUnload;
    }

    public int getFreightsAmount(){
        return freights.size();
    }
}
