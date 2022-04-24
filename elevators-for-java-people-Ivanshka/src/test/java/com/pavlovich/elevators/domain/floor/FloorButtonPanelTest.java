package com.pavlovich.elevators.domain.floor;

import com.pavlovich.elevators.domain.controller.AbstractElevatorsController;
import com.pavlovich.elevators.domain.controller.ElevatorsControllerSample;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FloorButtonPanelTest {
    private FloorButtonPanel panel;
    private AbstractElevatorsController controller;

    @BeforeEach
    public void beforeEach() {
        final int floorNumber = 2;
        controller = (AbstractElevatorsController) ElevatorsControllerSample.getTestController();
        panel = new FloorButtonPanel(controller, floorNumber);
    }

    @Test
    public void pressUpButton() {
        final int expectedCallsAmount = 1;
        panel.pressUpButton();
        final int actualCallsAmount = controller.getCalls().size();

        assertTrue(panel.isUpButtonPressed());
        assertEquals(expectedCallsAmount, actualCallsAmount);
    }

    @Test
    public void pressDownButton() {
        final int expectedCallsAmount = 1;
        panel.pressDownButton();
        final int actualCallsAmount = controller.getCalls().size();

        assertTrue(panel.isDownButtonPressed());
        assertEquals(expectedCallsAmount, actualCallsAmount);
    }

    @Test
    public void resetUpButton() {
        panel.pressUpButton();
        panel.resetUpButton();
        assertFalse(panel.isUpButtonPressed());
    }

    @Test
    public void resetDownButton() {
        panel.pressDownButton();
        panel.resetDownButton();
        assertFalse(panel.isDownButtonPressed());
    }
}
