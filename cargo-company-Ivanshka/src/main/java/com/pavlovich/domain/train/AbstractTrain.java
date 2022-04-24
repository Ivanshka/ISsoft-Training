package com.pavlovich.domain.train;

import com.google.common.base.Preconditions;
import com.pavlovich.domain.wagon.Locomotive;
import com.pavlovich.domain.wagon.Wagon;
import com.pavlovich.list.List;

import static com.google.common.base.Preconditions.checkNotNull;

public abstract class AbstractTrain implements Train{
    private final int number;
    private Locomotive locomotive;
    protected List<Wagon> wagons;

    protected AbstractTrain(int number, Locomotive locomotive) {
        checkNotNull(locomotive, "Locomotive can't be equal null");

        this.number = number;
        this.locomotive = locomotive;
        this.wagons = new List<>();
    }

    public int getNumber() {
        return number;
    }

    public int getWagonAmount() {
        return wagons.getSize();
    }

    public abstract void addWagon(Wagon wagon);

    public void removeWagon(int wagonNumber) {
        wagons.removeAt(wagonNumber);
    }

    public Wagon getWagon(int wagonIndex){
        return wagons.get(wagonIndex);
    }

    @Override
    public Locomotive getLocomotive() {
        return locomotive;
    }

    @Override
    public void setLocomotive(Locomotive locomotive) {
        Preconditions.checkNotNull(locomotive, "Locomotive can't be equal null");

        this.locomotive = locomotive;
    }

    @Override
    public boolean contains(Wagon wagon){
        return wagons.contains(wagon);
    }
}
