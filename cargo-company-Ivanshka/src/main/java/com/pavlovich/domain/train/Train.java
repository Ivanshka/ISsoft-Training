package com.pavlovich.domain.train;

import com.pavlovich.domain.wagon.Locomotive;
import com.pavlovich.domain.wagon.Wagon;

public interface Train {
    int getNumber();

    int getWagonAmount();

    void setLocomotive(Locomotive locomotive);

    Locomotive getLocomotive();

    void addWagon(Wagon wagon);

    void removeWagon(int wagonNumber);

    Wagon getWagon(int wagonNumber);

    boolean contains(Wagon wagon);
}
