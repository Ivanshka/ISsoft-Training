package com.pavlovich.elevators.domain.statistics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ElevatorStatisticsTest {
    private ElevatorStatistics statistics;

    @BeforeEach
    public void beforeEach() {
        statistics = new ElevatorStatistics();
    }

    @Test
    public void addToTotalTransportedPeopleCount() {
        final int testValue1 = 132;
        final int testValue2 = 34;
        final int testValue3 = 55;

        statistics.addToTotalTransportedPeopleCount(testValue1);
        statistics.addToTotalTransportedPeopleCount(testValue2);
        statistics.addToTotalTransportedPeopleCount(testValue3);

        final int expected = testValue1 + testValue2 + testValue3;

        assertEquals(expected, statistics.getTotalTransportedPeopleCount());
    }

    @Test
    public void incrementFloorsVisited() {
        final int expected = 2;

        statistics.incrementFloorsVisited();
        statistics.incrementFloorsVisited();

        assertEquals(expected, statistics.getFloorsVisited());
    }

    @Test
    public void incrementFloorsPassed() {
        final int testValue1 = 12763;
        final int testValue2 = 768123;
        final int testValue3 = 6123;

        statistics.addToPassedFloors(testValue1);
        statistics.addToPassedFloors(testValue2);
        statistics.addToPassedFloors(testValue3);

        final int expected = testValue1 + testValue2 + testValue3;

        assertEquals(expected, statistics.getFloorsPassed());
    }
}
