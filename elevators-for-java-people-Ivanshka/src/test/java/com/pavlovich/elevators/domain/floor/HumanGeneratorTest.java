package com.pavlovich.elevators.domain.floor;

import com.pavlovich.elevators.domain.controller.ElevatorsControllerSample;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.junit.jupiter.api.Assertions.*;

public class HumanGeneratorTest {
    private static final int DEFAULT_FLOOR_NUMBER = 1;

    private List<Human> peopleQueue;
    private Lock peopleQueueLocker;
    private Floor floor;
    private HumanGenerator generator;

    @BeforeEach
    public void beforeEach() {
        peopleQueue = new ArrayList<>();
        peopleQueueLocker = new ReentrantLock();
        floor = new Floor(DEFAULT_FLOOR_NUMBER, peopleQueue, peopleQueueLocker,
                null, null, ElevatorsControllerSample.getTestController());
        generator = FloorSample.getTestGenerator(floor);
    }

    @Test
    public void constructor() {
        final int expectedDelayTime = 500;
        assertEquals(expectedDelayTime, generator.getDelayTime());
        assertSame(generator.getQueue(), peopleQueue);
        assertSame(generator.getQueueLocker(), peopleQueueLocker);
    }

    @Test
    public void run() {
        final int minimumExpectedSize = 1;
        final int timeout = 3;

        generator.start();
        try {
            TimeUnit.SECONDS.sleep(timeout);
        } catch (InterruptedException ignored) { }
        finally {
            generator.finish();
        }
        assertTrue(minimumExpectedSize < floor.getUpQueue().size());
    }

    @Test
    public void generateHuman() {
        final Human human = generator.generateHuman();
        final int mass = human.getMass();
        assertTrue(mass >= HumanGenerator.MINIMUM_HUMAN_MASS && mass <= HumanGenerator.MAXIMUM_HUMAN_MASS,
                "Human mass is out of valid values range");
        final int expectedFloorNumber = 2;
        assertEquals(expectedFloorNumber, human.getTargetFloorNumber());
    }
}
