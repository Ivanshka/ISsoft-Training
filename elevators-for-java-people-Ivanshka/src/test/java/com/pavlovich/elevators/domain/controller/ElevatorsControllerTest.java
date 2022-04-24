package com.pavlovich.elevators.domain.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ElevatorsControllerTest {
    private ElevatorsController controller;

    @BeforeEach
    public void beforeEach() {
        controller = ElevatorsControllerSample.getTestController();
    }

    @Test
    public void callElevator() {
        ElevatorCall testCall = ElevatorsControllerSample.getTestElevatorCall();
        controller.callElevator(testCall);

        final int expectedSize = 1;
        final int actualSize = ((AbstractElevatorsController)controller).getCalls().size();
        assertEquals(expectedSize, actualSize);
    }
}
