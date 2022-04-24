package com.pavlovich.domain.user;

import com.google.common.base.Preconditions;

import java.util.prefs.Preferences;

public class Machinist extends User {
    private static final int MINIMUM_MACHINIST_AGE = 18;

    private boolean hasLicense;
    public Machinist(String firstName, String lastName, int age, boolean hasLicense) {
        super(firstName, lastName, age);

        Preconditions.checkArgument(age >= MINIMUM_MACHINIST_AGE,
                "Machinist age can't be less than 18 years, but specified age is " + age);

        this.hasLicense = hasLicense;
    }

    public boolean hasLicense() {
        return hasLicense;
    }

    public void setLicenseAvailability(boolean hasLicense) {
        this.hasLicense = hasLicense;
    }
}
