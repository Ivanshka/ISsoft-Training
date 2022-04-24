package com.pavlovich.elevators.domain.controller;

import com.google.common.base.Preconditions;
import com.pavlovich.elevators.domain.elevator.Elevator;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Getter
@Setter
@Slf4j
public abstract class AbstractElevatorsController extends Thread implements ElevatorsController {
    public final static int MINIMUM_QUEUE_CAPACITY = 20;

    protected List<Elevator> elevators;
    protected BlockingQueue<ElevatorCall> calls;
    protected boolean needRun;

    public AbstractElevatorsController(int callQueueCapacity) {
        Preconditions.checkArgument(callQueueCapacity >= MINIMUM_QUEUE_CAPACITY,
                "Call queue capacity should be greater than or equals minimum queue capacity");

        this.calls = new ArrayBlockingQueue<>(callQueueCapacity);
        this.needRun = true;
    }

    @Override
    public void callElevator(ElevatorCall call) {
        calls.add(call);
    }

    @Override
    public void run() {
        log.info("Controller was started");

        while (needRun) {
            processCalls();
        }

        log.info("Controller was stopped");
    }

    public void finish() {
        needRun = false;
    }

    protected abstract void processCalls();

    public List<ElevatorCall> getCalls() {
        return new LinkedList<>(calls);
    }
}
