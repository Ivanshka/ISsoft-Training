package com.pavlovich.elevators.domain.controller;

@FunctionalInterface
public interface ElevatorsController {
    void callElevator(ElevatorCall call);
}
