package com.pavlovich.elevators.service;

import com.pavlovich.elevators.domain.Building;
import com.pavlovich.elevators.domain.BuildingSample;
import com.pavlovich.elevators.domain.controller.AbstractElevatorsController;
import com.pavlovich.elevators.domain.controller.ElevatorsControllerImpl;
import com.pavlovich.elevators.domain.elevator.Elevator;
import com.pavlovich.elevators.domain.floor.Floor;
import com.pavlovich.elevators.domain.floor.HumanGenerator;
import com.pavlovich.elevators.validator.ElevatorValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BuildingServiceTest {
    private BuildingService service;

    @BeforeEach
    public void beforeEach() {
        service = new BuildingService(new ElevatorValidator());
    }

    @Test
    public void createBuildingValid() {
        final int queueInitCapacity = 10;
        final int[] loadCapacities = new int[]{400, 300, 200, 100};
        final float[] upIntensityValues = {0.5f, 1f};
        final float[] downIntensityValues = {1.5f, 2f};

        final Building building = new BuildingService(new ElevatorValidator()).createBuilding(
                new ElevatorsControllerImpl(AbstractElevatorsController.MINIMUM_QUEUE_CAPACITY),
                queueInitCapacity, loadCapacities, upIntensityValues, downIntensityValues);

        List<Elevator> elevators = building.getElevators();

        final int expectedElevatorsAmount = 4;
        final int expectedFloorsAmount = 3;

        final int actualElevatorsAmount = elevators.size();
        final int actualFloorsAmount = building.getFloors().size();

        assertEquals(expectedElevatorsAmount, actualElevatorsAmount);
        assertEquals(expectedFloorsAmount, actualFloorsAmount);

        for (int i = 0; i < actualElevatorsAmount; i++) {
            final int expectedLoadCapacity = loadCapacities[i];
            final int actualLoadCapacity = elevators.get(i).getLoadCapacity();
            assertEquals(expectedLoadCapacity, actualLoadCapacity);
        }
    }

    @Test
    public void createBuildingInvalid() {
        final int queueInitCapacity = 10;
        final int[] loadCapacities = new int[]{400, 300, 200, 100};
        final float[] upIntensityValues = {0.5f, 1f};
        final float[] downIntensityValues = {1.5f, 2f};

        final int controllerCapacity = AbstractElevatorsController.MINIMUM_QUEUE_CAPACITY;

        assertThrows(NullPointerException.class, () ->
                new BuildingService(new ElevatorValidator()).createBuilding(
                        null, queueInitCapacity, loadCapacities, upIntensityValues, downIntensityValues));

        assertThrows(NullPointerException.class, () ->
                new BuildingService(new ElevatorValidator()).createBuilding(
                        new ElevatorsControllerImpl(controllerCapacity),
                        queueInitCapacity, null, upIntensityValues, downIntensityValues));

        assertThrows(NullPointerException.class, () ->
                new BuildingService(new ElevatorValidator()).createBuilding(
                        new ElevatorsControllerImpl(controllerCapacity),
                        queueInitCapacity, loadCapacities, null, downIntensityValues));

        assertThrows(NullPointerException.class, () ->
                new BuildingService(new ElevatorValidator()).createBuilding(
                        new ElevatorsControllerImpl(controllerCapacity),
                        queueInitCapacity, loadCapacities, upIntensityValues, null));


        assertThrows(IllegalArgumentException.class, () ->
                new BuildingService(new ElevatorValidator()).createBuilding(
                        new ElevatorsControllerImpl(controllerCapacity),
                        BuildingService.MINIMUM_QUEUE_CAPACITY - 1, loadCapacities, upIntensityValues,
                        downIntensityValues));

        assertThrows(IllegalArgumentException.class, () -> {
            int[] testEmptyArray = new int[0];
            new BuildingService(new ElevatorValidator()).createBuilding(
                    new ElevatorsControllerImpl(controllerCapacity),
                    queueInitCapacity, testEmptyArray, upIntensityValues, downIntensityValues);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            float[] testEmptyArray = new float[0];
            new BuildingService(new ElevatorValidator()).createBuilding(
                    new ElevatorsControllerImpl(controllerCapacity),
                    queueInitCapacity, loadCapacities, testEmptyArray, downIntensityValues);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            float[] testEmptyArray = new float[0];
            new BuildingService(new ElevatorValidator()).createBuilding(
                    new ElevatorsControllerImpl(controllerCapacity),
                    queueInitCapacity, loadCapacities, upIntensityValues, testEmptyArray);
        });


        assertThrows(IllegalArgumentException.class, () -> {
            float[] invalidDownIntensityValues = new float[upIntensityValues.length + 1];
            new BuildingService(new ElevatorValidator()).createBuilding(
                    new ElevatorsControllerImpl(controllerCapacity),
                    queueInitCapacity, loadCapacities, upIntensityValues, invalidDownIntensityValues);
        });
    }
}
