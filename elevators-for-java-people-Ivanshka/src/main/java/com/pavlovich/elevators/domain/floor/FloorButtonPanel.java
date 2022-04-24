package com.pavlovich.elevators.domain.floor;

import com.pavlovich.elevators.domain.elevator.Direction;
import com.pavlovich.elevators.domain.controller.ElevatorCall;
import com.pavlovich.elevators.domain.controller.ElevatorsController;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
public class FloorButtonPanel {
    private final ElevatorsController elevatorsController;
    private final int floorNumber;
    @Setter(AccessLevel.NONE)
    private boolean isUpButtonPressed;
    @Setter(AccessLevel.NONE)
    private boolean isDownButtonPressed;

    public void pressUpButton() {
        if (!isUpButtonPressed) {
            ElevatorCall call = new ElevatorCall(Direction.UP, floorNumber);
            elevatorsController.callElevator(call);
            isUpButtonPressed = true;
        }
    }

    public void pressDownButton() {
        if (!isDownButtonPressed) {
            ElevatorCall call = new ElevatorCall(Direction.DOWN, floorNumber);
            elevatorsController.callElevator(call);
            isDownButtonPressed = true;
        }
    }

    public void resetUpButton() {
        isUpButtonPressed = false;
    }

    public void resetDownButton() {
        isDownButtonPressed = false;
    }
}
