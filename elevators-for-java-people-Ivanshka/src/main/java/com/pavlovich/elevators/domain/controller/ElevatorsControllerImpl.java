package com.pavlovich.elevators.domain.controller;

import com.pavlovich.elevators.domain.elevator.Direction;
import com.pavlovich.elevators.domain.elevator.Elevator;
import com.pavlovich.elevators.domain.elevator.ElevatorState;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

@Slf4j
public class ElevatorsControllerImpl extends AbstractElevatorsController {
    public ElevatorsControllerImpl(int callQueueCapacity) {
        super(callQueueCapacity);
    }

    /***
     * Processes elevator calls. Elevator is selected according to the characteristics:
     * state is wait,
     * direction is on his way + min people,
     * min people
     */
    @SneakyThrows
    protected void processCalls() {
        ElevatorCall call;

        if (calls.isEmpty()) {
            final int waitForNewCalls = 250;
            TimeUnit.MILLISECONDS.sleep(waitForNewCalls);

            return;
        } else {
            call = calls.take();
        }

        Optional<Elevator> elevator = elevators.stream()
                .filter(el -> el.getElevatorState() == ElevatorState.WAIT)
                .findFirst();

        if (elevator.isEmpty()) {
            log.info("Controller couldn't find waiting elevator");

            elevator = elevators.stream()
                    .filter(el -> isElevatorAppropriate(el, call))
                    .min(Comparator.comparing(Elevator::getPeopleAmount));
        }

        if (elevator.isEmpty()) {
            log.info("Controller couldn't find passing elevator");

            elevator = elevators.stream()
                    .min(Comparator.comparing(Elevator::getPeopleAmount));
        }

        elevator.ifPresent((el) -> {
            Lock locker = el.getTargetFloorsLocker();
            locker.lock();
            el.getTargetFloorsList().add(call.getFloorNumber());
            locker.unlock();

            el.getAwaitStateLocker().lock();
            el.setElevatorState(ElevatorState.MOVING);
            el.getAwaitCondition().signal();
            el.getAwaitStateLocker().unlock();

            log.info("Elevator call was processed");
        });
    }

    private boolean isElevatorAppropriate(Elevator elevator, ElevatorCall call) {
        final int elevatorCurrentFloor = elevator.getCurrentFloor();
        final int callFloorNumber = call.getFloorNumber();
        final Direction elevatorDirection = elevator.getDirection();

        return elevatorDirection == call.getDirection()
                && (elevatorDirection == Direction.UP && elevatorCurrentFloor < callFloorNumber
                || elevatorDirection == Direction.DOWN && elevatorCurrentFloor > callFloorNumber);
    }
}
