package com.pavlovich.elevators.domain.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class AbstractElevatorsControllerTest {
    @Test
    public void constructor() {
        final int invalidCapacity = AbstractElevatorsController.MINIMUM_QUEUE_CAPACITY - 1;

        assertThrows(IllegalArgumentException.class, () -> {
            new AbstractElevatorsController(invalidCapacity) {
                @Override
                protected void processCalls() { }
            };
        });
    }
}
