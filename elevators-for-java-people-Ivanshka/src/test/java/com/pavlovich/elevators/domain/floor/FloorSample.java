package com.pavlovich.elevators.domain.floor;

import com.pavlovich.elevators.domain.elevator.Direction;

public class FloorSample {
    private static final int DEFAULT_GENERATING_INTENSITY = 2;
    private static final int DEFAULT_FLOORS_AMOUNT = 2;

    public static HumanGenerator getTestGenerator(Floor floor) {
        return new HumanGenerator(DEFAULT_GENERATING_INTENSITY, DEFAULT_FLOORS_AMOUNT,
                floor, Direction.UP);
    }
}
