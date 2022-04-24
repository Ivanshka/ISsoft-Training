package com.pavlovich.elevators.validator;

import com.google.common.base.Preconditions;
import com.pavlovich.elevators.domain.elevator.Elevator;

public class ElevatorValidator {
    public void validate(Elevator elevator) {
        Preconditions.checkNotNull(elevator, "Elevator can't be null");

        final int loadCapacity = elevator.getLoadCapacity();
        final int movementSpeed = elevator.getMovementDelayTime();
        final int doorSpeed = elevator.getDoorDelayTime();

        if (loadCapacity < 1)
            throw new IllegalArgumentException("Load capacity must be positive");
        if (movementSpeed < 1)
            throw new IllegalArgumentException("Movement speed must be positive");
        if (doorSpeed < 1)
            throw new IllegalArgumentException("Door speed must be positive");
    }
}
