package com.pavlovich.elevators.service;

import com.pavlovich.elevators.domain.controller.ElevatorsControllerSample;
import com.pavlovich.elevators.domain.elevator.Direction;
import com.pavlovich.elevators.domain.elevator.Elevator;
import com.pavlovich.elevators.domain.floor.Floor;
import com.pavlovich.elevators.domain.floor.Human;
import com.pavlovich.elevators.domain.statistics.ElevatorStatistics;
import com.pavlovich.elevators.domain.statistics.StatisticsStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.junit.jupiter.api.Assertions.*;

public class FloorServiceTest {
    final int floorsAmount = 3;
    final int currentAndTargetFloorNumber = 1;
    final int loadCapacity = 400;
    final int lastVisitedFloor = 3;

    private FloorService floorService;
    private Elevator elevator;

    @BeforeEach
    public void beforeEach() {
        UUID elevatorId = UUID.randomUUID();
        elevator = new Elevator(elevatorId, loadCapacity);

        elevator.setDirection(Direction.DOWN);
        elevator.setCurrentFloor(currentAndTargetFloorNumber);
        elevator.setTargetFloor(currentAndTargetFloorNumber);
        elevator.getTargetFloorsList().add(currentAndTargetFloorNumber);

        final int mass = 75;
        elevator.getPeople().add(new Human(mass, currentAndTargetFloorNumber));
        final int peopleInElevator = 1;
        elevator.setPeopleAmount(peopleInElevator);
        elevator.setLastVisitedFloor(lastVisitedFloor);

        floorService = new FloorService(floorsAmount, new StatisticsStorage());
        floorService.getStatistics().getElevatorStatistics().put(elevatorId, new ElevatorStatistics());
    }

    @Test
    public void letOutPeopleAndUpdateStatistics() {
        assertThrows(NullPointerException.class, () ->
                floorService.letOutPeopleAndUpdateStatistics(null));

        floorService.letOutPeopleAndUpdateStatistics(elevator);

        final ElevatorStatistics elevatorStatistics = floorService.getStatistics()
                .getElevatorStatistics().get(elevator.getElevatorId());

        final int expectedFloorsVisited = 1;
        final int actualFloorsVisited = elevatorStatistics.getFloorsVisited();

        final int expectedFloorsPassed = 2;
        final int actualFloorsPassed = elevatorStatistics.getFloorsPassed();

        final int expectedTotalPeopleCount = 1;
        final int actualTotalPeopleCount = elevatorStatistics.getTotalTransportedPeopleCount();

        assertEquals(expectedFloorsVisited, actualFloorsVisited);
        assertEquals(expectedFloorsPassed, actualFloorsPassed);
        assertEquals(expectedTotalPeopleCount, actualTotalPeopleCount);
    }

    @Test
    public void letInPeople() {
        final int defaultFloorNumber = 1;
        final Lock peopleQueueLocker = new ReentrantLock();
        final List<Human> peopleQueue = new ArrayList<>();

        final int mass = 75;
        peopleQueue.add(new Human(mass, floorsAmount));

        Floor floor = new Floor(defaultFloorNumber, peopleQueue, peopleQueueLocker,
                null, null, ElevatorsControllerSample.getTestController());

        assertThrows(NullPointerException.class, () -> floorService.letInPeople(null, floor));
        assertThrows(NullPointerException.class, () -> floorService.letInPeople(elevator, null));

        final int expectedPeopleAmount = elevator.getPeopleAmount() + 1;

        floorService.letInPeople(elevator, floor);

        final int actualPeopleAmount = elevator.getPeopleAmount();

        assertEquals(expectedPeopleAmount, actualPeopleAmount);
        assertTrue(floor.getUpQueue().isEmpty());
    }
}
