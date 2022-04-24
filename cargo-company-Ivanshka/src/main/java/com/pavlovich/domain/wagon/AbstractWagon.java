package com.pavlovich.domain.wagon;

public abstract class AbstractWagon implements Wagon{
    protected final int number;

    protected AbstractWagon(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
