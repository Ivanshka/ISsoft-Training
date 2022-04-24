package com.pavlovich.elevators.domain.elevator;

import com.pavlovich.elevators.domain.Building;
import com.pavlovich.elevators.domain.BuildingSample;
import com.pavlovich.elevators.domain.statistics.ElevatorStatistics;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ElevatorTest {
    @Test
    public void run() {
        Building building = BuildingSample.getTestBuilding();

        final int timeout = 10;

        try {
            building.startBuilding();
            TimeUnit.SECONDS.sleep(timeout);
        } catch (InterruptedException ignored) {
        } finally {
            building.stopBuilding();
        }

        UUID key = building.getElevators().get(0).getElevatorId();
        final ElevatorStatistics elevatorStatistics = building.getStatistics().getElevatorStatistics().get(key);

        final int minimumPeopleCount = 2;
        final int minimumFloorsVisited = 2;
        final int minimumFloorsPassed = 2;

        final int actualPeopleCount = elevatorStatistics.getTotalTransportedPeopleCount();
        final int actualFloorsVisited = elevatorStatistics.getFloorsVisited();
        final int actualFloorsPassed = elevatorStatistics.getFloorsPassed();

        assertTrue(minimumPeopleCount < actualPeopleCount);
        assertTrue(minimumFloorsVisited < actualFloorsVisited);
        assertTrue(minimumFloorsPassed < actualFloorsPassed);
    }
}
