package com.pavlovich.elevators.validator;

import com.pavlovich.elevators.domain.elevator.Elevator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ElevatorValidatorTest {
    private ElevatorValidator validator;

    @BeforeEach
    public void beforeEach() {
        validator = new ElevatorValidator();
    }

    @Test
    public void validate() {
        assertThrows(NullPointerException.class, () -> validator.validate(null));

        final int loadCapacity = 400;
        final int movementDelayTime = 1000;
        final int doorDelayTime = 1000;

        final int invalidLoadCapacity = 0;
        final int invalidMovementDelayTime = 0;
        final int invalidDoorDelayTime = 0;

        Elevator validElevator = new Elevator(UUID.randomUUID(), loadCapacity, movementDelayTime, doorDelayTime);
        validator.validate(validElevator);

        Elevator invalidElevator1 = new Elevator(UUID.randomUUID(),
                invalidLoadCapacity, movementDelayTime, doorDelayTime);
        assertThrows(IllegalArgumentException.class, () -> validator.validate(invalidElevator1));

        Elevator invalidElevator2 = new Elevator(UUID.randomUUID(),
                loadCapacity, invalidMovementDelayTime, doorDelayTime);
        assertThrows(IllegalArgumentException.class, () -> validator.validate(invalidElevator2));

        Elevator invalidElevator3 = new Elevator(UUID.randomUUID(),
                loadCapacity, invalidMovementDelayTime, invalidDoorDelayTime);
        assertThrows(IllegalArgumentException.class, () -> validator.validate(invalidElevator3));
    }
}
