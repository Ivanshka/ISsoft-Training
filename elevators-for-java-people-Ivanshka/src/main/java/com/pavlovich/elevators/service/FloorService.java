package com.pavlovich.elevators.service;

import com.google.common.base.Preconditions;
import com.pavlovich.elevators.domain.controller.ElevatorCall;
import com.pavlovich.elevators.domain.elevator.Direction;
import com.pavlovich.elevators.domain.elevator.Elevator;
import com.pavlovich.elevators.domain.floor.FloorButtonPanel;
import com.pavlovich.elevators.domain.floor.Floor;
import com.pavlovich.elevators.domain.floor.Human;
import com.pavlovich.elevators.domain.statistics.ElevatorStatistics;
import com.pavlovich.elevators.domain.statistics.StatisticsStorage;
import lombok.Getter;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.locks.Lock;

@Getter
public class FloorService {
    private final int floorsAmount;
    private final StatisticsStorage statistics;

    public FloorService(int floorsAmount, StatisticsStorage statisticsStorage) {
        Preconditions.checkNotNull(statisticsStorage);

        this.floorsAmount = floorsAmount;
        this.statistics = statisticsStorage;
    }

    public void letOutPeopleAndUpdateStatistics(Elevator elevator) {
        Preconditions.checkNotNull(elevator);

        final int peopleAmountBefore = elevator.getPeopleAmount();

        elevator.getPeople().removeIf(human -> human.getTargetFloorNumber() == elevator.getCurrentFloor());

        final int peopleAmountAfter = elevator.getPeople().size();
        final int transportedPeople = peopleAmountBefore - peopleAmountAfter;
        final int passedFloors = Math.abs(elevator.getLastVisitedFloor() - elevator.getCurrentFloor());

        elevator.setPeopleAmount(peopleAmountAfter);

        final UUID key = elevator.getElevatorId();

        ElevatorStatistics elevatorStatistics = statistics.getElevatorStatistics().get(key);

        elevatorStatistics.addToTotalTransportedPeopleCount(transportedPeople);
        elevatorStatistics.incrementFloorsVisited();
        elevatorStatistics.addToPassedFloors(passedFloors);
    }

    public void letInPeople(Elevator elevator, Floor floor) {
        Preconditions.checkNotNull(elevator, "Elevator can't be null");
        Preconditions.checkNotNull(floor, "Floor can't be null");

        List<Human> queue;
        Lock queueLocker;

        if (elevator.getCurrentFloor() == floorsAmount) {
            queueLocker = floor.getDownQueueLocker();
            queue = floor.getDownQueue();
        } else if (elevator.getCurrentFloor() == 1) {
            queueLocker = floor.getUpQueueLocker();
            queue = floor.getUpQueue();
        } else {
            if (elevator.getDirection() == Direction.UP) {
                queueLocker = floor.getUpQueueLocker();
                queue = floor.getUpQueue();
            } else {
                queueLocker = floor.getDownQueueLocker();
                queue = floor.getDownQueue();
            }
        }

        int currentTotalMass = elevator.getPeople().stream().mapToInt(Human::getMass).sum();

        queueLocker.lock();
        elevator.getTargetFloorsLocker().lock();

        for (int i = 0; i < queue.size(); i++) {
            final Human human = queue.get(i);
            if (currentTotalMass + human.getMass() <= elevator.getLoadCapacity()) {
                queue.remove(i);
                i--;
                elevator.getPeople().add(human);
                currentTotalMass += human.getMass();
                elevator.getTargetFloorsList().add(human.getTargetFloorNumber());
            }
        }
        elevator.getTargetFloorsList().remove(elevator.getCurrentFloor());
        elevator.getTargetFloorsLocker().unlock();

        elevator.setPeopleAmount(elevator.getPeople().size());

        FloorButtonPanel panel = floor.getButtonPanel();

        if (!queue.isEmpty()) {
            final ElevatorCall call = new ElevatorCall(elevator.getDirection(), elevator.getCurrentFloor());
            panel.getElevatorsController().callElevator(call);
        } else if (elevator.getDirection() == Direction.UP)
            panel.resetUpButton();
        else
            panel.resetDownButton();

        queueLocker.unlock();
    }
}
