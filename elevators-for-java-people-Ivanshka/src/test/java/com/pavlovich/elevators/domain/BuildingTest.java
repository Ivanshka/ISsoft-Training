package com.pavlovich.elevators.domain;

import com.pavlovich.elevators.domain.controller.AbstractElevatorsController;
import com.pavlovich.elevators.domain.elevator.Elevator;
import com.pavlovich.elevators.domain.floor.HumanGenerator;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class BuildingTest {
    @Test
    public void startStopElevators() {
        final int timeoutForThreadStart = 2;
        final int timeoutForThreadStop = 5;
        Building building = BuildingSample.getTestBuilding();

        building.startElevators();
        try {
            TimeUnit.SECONDS.sleep(timeoutForThreadStart);
        } catch (InterruptedException ignored) {
        }

        for (Elevator elevator : building.getElevators()) {
            final Thread.State state = elevator.getState();
            assertNotSame(state, Thread.State.NEW, "Thread is not started");
        }

        building.stopElevators();
        try {
            TimeUnit.SECONDS.sleep(timeoutForThreadStop);
        } catch (InterruptedException ignored) {
        }

        for (Elevator elevator : building.getElevators()) {
            assertSame(elevator.getState(), Thread.State.TERMINATED);
        }
    }

    @Test
    public void startStopGenerators() {
        final int timeoutForThreadStart = 2;
        final int timeoutForThreadStop = 5;
        Building building = BuildingSample.getTestBuilding();

        building.startGenerators();
        try {
            TimeUnit.SECONDS.sleep(timeoutForThreadStart);
        } catch (InterruptedException ignored) {
        }

        for (HumanGenerator generator : building.getGenerators()) {
            final Thread.State state = generator.getState();
            assertNotSame(state, Thread.State.NEW, "Thread is not started");
        }

        building.stopGenerators();
        try {
            TimeUnit.SECONDS.sleep(timeoutForThreadStop);
        } catch (InterruptedException ignored) {
        }

        for (HumanGenerator generator : building.getGenerators()) {
            assertSame(generator.getState(), Thread.State.TERMINATED);
        }
    }

    @Test
    public void startStopController() {
        final int timeoutForThreadStart = 2;
        final int timeoutForThreadStop = 2;
        Building building = BuildingSample.getTestBuilding();

        final AbstractElevatorsController controller = building.getController();
        building.startController();

        try {
            TimeUnit.SECONDS.sleep(timeoutForThreadStart);
        } catch (InterruptedException ignored) {
        }

        Thread.State state = controller.getState();
        assertNotSame(state, Thread.State.NEW, "Thread is not started");


        building.stopController();
        try {
            TimeUnit.SECONDS.sleep(timeoutForThreadStop);
        } catch (InterruptedException ignored) {
        }

        state = controller.getState();
        assertSame(state, Thread.State.TERMINATED);
    }
}
