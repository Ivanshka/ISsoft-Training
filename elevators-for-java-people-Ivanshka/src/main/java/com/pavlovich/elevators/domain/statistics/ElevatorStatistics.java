package com.pavlovich.elevators.domain.statistics;

import lombok.Getter;

@Getter
public class ElevatorStatistics {
    private int totalTransportedPeopleCount;
    private int floorsPassed;
    private int floorsVisited;

    public void addToTotalTransportedPeopleCount(int value) {
        totalTransportedPeopleCount += value;
    }

    public void addToPassedFloors(int value) {
        floorsPassed += value;
    }

    public void incrementFloorsVisited() {
        floorsVisited++;
    }
}
