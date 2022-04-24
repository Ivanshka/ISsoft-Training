package com.pavlovich.elevators.domain.controller;

import com.pavlovich.elevators.domain.elevator.Direction;
import lombok.Data;

@Data
public class ElevatorCall {
    private final Direction direction;
    private final int floorNumber;
}
