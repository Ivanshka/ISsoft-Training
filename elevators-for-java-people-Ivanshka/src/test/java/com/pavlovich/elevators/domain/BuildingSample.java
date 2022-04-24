package com.pavlovich.elevators.domain;

import com.pavlovich.elevators.domain.controller.ElevatorsControllerImpl;
import com.pavlovich.elevators.service.BuildingService;
import com.pavlovich.elevators.validator.ElevatorValidator;

public class BuildingSample {
    private static final int QUEUE_INIT_CAPACITY = 50;
    private static final int[] LOAD_CAPACITIES = new int[]{400};
    private static final float[] UP_INTENSITY_VALUES = {1f};
    private static final float[] DOWN_INTENSITY_VALUES = {0.5f};

    public static Building getTestBuilding() {

        return new BuildingService(new ElevatorValidator()).createBuilding(
                new ElevatorsControllerImpl(ElevatorsControllerImpl.MINIMUM_QUEUE_CAPACITY),
                QUEUE_INIT_CAPACITY, LOAD_CAPACITIES, UP_INTENSITY_VALUES, DOWN_INTENSITY_VALUES);
    }
}
