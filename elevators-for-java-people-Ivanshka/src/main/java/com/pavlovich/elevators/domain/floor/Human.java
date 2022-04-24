package com.pavlovich.elevators.domain.floor;

import lombok.Data;

@Data
public class Human {
    private final int mass;
    private final int targetFloorNumber;
}
