package com.pavlovich.elevators.domain.elevator;

import com.pavlovich.elevators.domain.Building;
import com.pavlovich.elevators.domain.controller.AbstractElevatorsController;
import com.pavlovich.elevators.domain.floor.Floor;
import com.pavlovich.elevators.domain.floor.Human;
import com.pavlovich.elevators.service.FloorService;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@EqualsAndHashCode(callSuper = true)
@Data
public class Elevator extends Thread {
    private static final int DEFAULT_DOOR_DELAY_TIME = 1000;
    private static final int DEFAULT_MOVEMENT_DELAY_TIME = 1000;

    private final UUID elevatorId;
    private final int loadCapacity;
    private final List<Human> people;
    private final Set<Integer> targetFloorsList;
    private final Lock targetFloorsLocker;
    private final Lock awaitStateLocker;
    private final Condition awaitCondition;
    private Building building;
    private int peopleAmount;
    private int doorDelayTime;
    private int movementDelayTime;
    private int targetFloor;
    private int currentFloor;
    private int lastVisitedFloor;
    private boolean needRun;
    private Direction direction;
    private ElevatorState elevatorState;
    private AbstractElevatorsController elevatorsController;

    public Elevator(UUID elevatorId, int loadCapacity) {
        this.elevatorId = elevatorId;
        this.loadCapacity = loadCapacity;
        this.doorDelayTime = DEFAULT_DOOR_DELAY_TIME;
        this.movementDelayTime = DEFAULT_MOVEMENT_DELAY_TIME;
        this.currentFloor = 1;
        this.targetFloor = 1;
        this.direction = Direction.UP;
        this.elevatorState = ElevatorState.WAIT;
        this.people = new ArrayList<>();
        this.targetFloorsList = new HashSet<>();
        this.needRun = true;
        this.targetFloorsLocker = new ReentrantLock();
        this.awaitStateLocker = new ReentrantLock();
        this.awaitCondition = awaitStateLocker.newCondition();
    }

    public Elevator(UUID elevatorId, int loadCapacity, int movementDelayTime, int doorDelayTime) {
        this(elevatorId, loadCapacity);
        this.doorDelayTime = doorDelayTime;
        this.movementDelayTime = movementDelayTime;
    }

    @Override
    public void run() {
        log.info("Elevator was started");

        while (needRun) {
            transportPeople();
        }

        log.info("Elevator was stopped");
    }

    public void finish() {
        needRun = false;
    }

    @SneakyThrows
    private void transportPeople() {
        if (targetFloorsList.isEmpty()) {
            elevatorState = ElevatorState.WAIT;

            log.info("Elevator was switched to WAIT state");

            awaitStateLocker.lock();
            final long halfSecondInNanos = 500_000_000;
            awaitCondition.awaitNanos(halfSecondInNanos);
            awaitStateLocker.unlock();

            return;
        }

        log.info("Elevator was switched to MOVING state by controller");

        Optional<Integer> nearestTargetFloor = findNearestTargetFloor();

        if (nearestTargetFloor.isPresent()) {
            targetFloor = nearestTargetFloor.get();
        } else {
            log.warn("Elevator couldn't find the nearest target floor");
            return;
        }

        if (currentFloor == targetFloor) {
            elevatorState = ElevatorState.DOOR;
            log.info("Elevator was switched to DOOR state");
            TimeUnit.MILLISECONDS.sleep(doorDelayTime);

            elevatorState = ElevatorState.PEOPLE_EXCHANGING;
            log.info("Elevator was switched to PEOPLE_EXCHANGING state");

            FloorService floorService = building.getFloorService();
            Floor currentFloor = building.getFloors().get(this.currentFloor - 1);
            floorService.letOutPeopleAndUpdateStatistics(this);
            floorService.letInPeople(this, currentFloor);

            elevatorState = ElevatorState.DOOR;
            log.info("Elevator was switched to DOOR state");

            TimeUnit.MILLISECONDS.sleep(doorDelayTime);

            elevatorState = ElevatorState.MOVING;
            log.info("Elevator was switched to MOVING state");
        }

        nearestTargetFloor = findNearestTargetFloorAfterExchangingAndSetDirection(false);
        if (nearestTargetFloor.isPresent()) {
            targetFloor = nearestTargetFloor.get();
        } else {
            return;
        }

        TimeUnit.MILLISECONDS.sleep(movementDelayTime);
        currentFloor += direction.getDirection();
    }

    /***
     *  This method is needed to check if elevator was called on a closer floor (compared to current target floor,
     *  to which elevator is moving now). So method checks current floor too.
     * @return The nearest floor that elevator need move to  for exchanging people
     */
    private Optional<Integer> findNearestTargetFloor() {
        Optional<Integer> nearestFloorNumber;

        targetFloorsLocker.lock();
        if (direction == Direction.UP) {
            nearestFloorNumber = targetFloorsList.stream()
                    .filter(floor -> floor >= currentFloor)
                    .min(Integer::compare);
        } else {
            nearestFloorNumber = targetFloorsList.stream()
                    .filter(floor -> floor <= currentFloor)
                    .max(Integer::compare);
        }
        targetFloorsLocker.unlock();

        return nearestFloorNumber;
    }

    /***
     * This method is needed to find the next target floor after people exchanging, so it doesn't check current
     * floor like 'findNearestTargetFloor'.
     * @return The nearest floor that elevator need to move for people exchanging
     * @param isStartedAgain Flag of recursive call uses inside, default value is false
     */
    private Optional<Integer> findNearestTargetFloorAfterExchangingAndSetDirection(boolean isStartedAgain) {
        Optional<Integer> nearestFloorNumber;

        targetFloorsLocker.lock();
        if (direction == Direction.UP) {
            nearestFloorNumber = targetFloorsList.stream()
                    .filter(floor -> floor > currentFloor)
                    .min(Integer::compare);
        } else {
            nearestFloorNumber = targetFloorsList.stream()
                    .filter(floor -> floor < currentFloor)
                    .max(Integer::compare);
        }
        targetFloorsLocker.unlock();

        if (!isStartedAgain) {
            if (nearestFloorNumber.isEmpty()) {
                direction = direction.getOppositeDirection();
                nearestFloorNumber = findNearestTargetFloorAfterExchangingAndSetDirection(true);
            }
        }

        return nearestFloorNumber;
    }

    @Override
    public String toString() {
        return direction + ", " + elevatorState + ", people: " + people.size() + ", cur floor: " + currentFloor +
                ", target floors: " + targetFloorsList;
    }
}
