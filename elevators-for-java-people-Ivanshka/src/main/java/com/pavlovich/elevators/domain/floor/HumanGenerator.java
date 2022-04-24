package com.pavlovich.elevators.domain.floor;

import com.google.common.base.Preconditions;
import com.pavlovich.elevators.domain.elevator.Direction;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

@Slf4j
@Getter
@Setter
public class HumanGenerator extends Thread {
    public final static int MINIMUM_HUMAN_MASS = 60;
    public final static int MAXIMUM_HUMAN_MASS = 120;

    private final int floorsAmount;
    private final int floorNumber;
    private boolean needRun;
    private final int delayTime;
    private final Floor floor;
    private final List<Human> queue;
    private final Lock queueLocker;
    private final Direction humanDirection;
    private final Random random;

    public HumanGenerator(float peoplePerSecond, int floorsAmountInBuilding,
                          Floor floor, Direction humanDirection) {
        Preconditions.checkArgument(peoplePerSecond > 0, "Generating intensity should be positive");
        Preconditions.checkArgument(floorsAmountInBuilding > 1,
                "Amount of floors should be greater than or equals to two");
        Preconditions.checkNotNull(floor, "Floor can't be null");

        this.floorsAmount = floorsAmountInBuilding;
        this.floorNumber = floor.getButtonPanel().getFloorNumber();
        this.needRun = true;
        final int oneSecond = 1000;
        this.delayTime = (int) (oneSecond / peoplePerSecond);
        this.floor = floor;
        this.queue = humanDirection == Direction.UP ?
                floor.getUpQueue() : floor.getDownQueue();
        this.queueLocker = humanDirection == Direction.UP ?
                floor.getUpQueueLocker() : floor.getDownQueueLocker();
        this.humanDirection = humanDirection;
        this.random = new Random();
    }

    @SneakyThrows
    @Override
    public void run() {
        log.info("Generator of " + floorNumber + " floor with direction " + humanDirection + " was started");

        while (needRun) {
            Human human = generateHuman();
            log.info("Human was generated");

            queueLocker.lock();
            queue.add(human);
            queueLocker.unlock();

            if (humanDirection == Direction.UP)
                floor.getButtonPanel().pressUpButton();
            else
                floor.getButtonPanel().pressDownButton();

            log.info("Human was added to queue");

            TimeUnit.MILLISECONDS.sleep(delayTime);
        }

        log.info("Generator of " + floorNumber + " floor with direction " + humanDirection + " was stopped");
    }

    public void finish() {
        needRun = false;
    }

    public Human generateHuman() {
        final int firstFloorNumber = 1;
        int targetFloorNumber = floorNumber;

        while (targetFloorNumber == floorNumber) {
            targetFloorNumber = getRandomInteger(firstFloorNumber, floorsAmount);
        }

        int mass = getRandomInteger(MINIMUM_HUMAN_MASS, MAXIMUM_HUMAN_MASS);

        return new Human(mass, targetFloorNumber);
    }

    private int getRandomInteger(int min, int max) {
        final int bound = (max - min) + 1;
        return random.nextInt(bound) + min;
    }
}


