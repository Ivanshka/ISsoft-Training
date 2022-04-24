package com.pavlovich.domain.wagon;

import com.pavlovich.domain.user.Machinist;

import static com.google.common.base.Preconditions.checkNotNull;

public class Locomotive extends AbstractWagon {
    private Machinist machinist;

    public Locomotive(int wagonNumber, Machinist machinist) {
        super(wagonNumber);

        checkMachinist(machinist);

        this.machinist = machinist;
    }

    public Machinist getMachinist() {
        return machinist;
    }

    public void setMachinist(Machinist machinist) {
        checkMachinist(machinist);

        this.machinist = machinist;
    }

    private void checkMachinist(Machinist machinist){
        checkNotNull(machinist, "Machinist can't be equal null");

        if (!machinist.hasLicense())
            throw new IllegalArgumentException("Specified machinist has not a license");
    }
}
