package com.pavlovich.domain.user;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public abstract class User {
    private final String firstName;
    private final String lastName;
    private final int age;

    protected User(String firstName, String lastName, int age) {
        checkNotNull(firstName, "Firstname can't be equal null.");
        checkNotNull(lastName, "Lastname can't be equal null.");
        checkArgument(age > 0, "User age should be positive.");

        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }
}
