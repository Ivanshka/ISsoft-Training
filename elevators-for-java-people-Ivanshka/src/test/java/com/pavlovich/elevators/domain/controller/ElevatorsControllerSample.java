package com.pavlovich.elevators.domain.controller;

import com.pavlovich.elevators.domain.elevator.Direction;

public class ElevatorsControllerSample {
    private final static int DEFAULT_CALL_QUEUE_CAPACITY = 1000;

    public static ElevatorsController getTestController() {
        return new ElevatorsControllerImpl(DEFAULT_CALL_QUEUE_CAPACITY);
    }

    public static ElevatorCall getTestElevatorCall() {
        final int secondFloorNumber = 2;
        return new ElevatorCall(Direction.UP, secondFloorNumber);
    }
}
