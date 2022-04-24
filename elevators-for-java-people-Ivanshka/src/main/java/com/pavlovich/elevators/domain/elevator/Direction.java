package com.pavlovich.elevators.domain.elevator;

import lombok.Getter;

@Getter
public enum Direction {
    UP(1),
    DOWN(-1);

    private final int direction;

    Direction(int number) {
        this.direction = number;
    }

    public Direction getOppositeDirection() {
        return direction == UP.direction ? DOWN : UP;
    }
}
