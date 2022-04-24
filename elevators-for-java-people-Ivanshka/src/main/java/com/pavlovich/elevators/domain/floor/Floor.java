package com.pavlovich.elevators.domain.floor;

import com.pavlovich.elevators.domain.controller.ElevatorsController;
import lombok.Getter;

import java.util.List;
import java.util.concurrent.locks.Lock;

@Getter
public class Floor {
    private final List<Human> upQueue;
    private final List<Human> downQueue;
    private final Lock upQueueLocker;
    private final Lock downQueueLocker;
    private final FloorButtonPanel buttonPanel;

    public Floor(int floorNumber, List<Human> upQueue, Lock upQueueLocker, List<Human> downQueue, Lock downQueueLocker,
                 ElevatorsController controller) {
        this.upQueue = upQueue;
        this.downQueue = downQueue;
        this.upQueueLocker = upQueueLocker;
        this.downQueueLocker = downQueueLocker;
        this.buttonPanel = new FloorButtonPanel(controller, floorNumber);
    }
}
