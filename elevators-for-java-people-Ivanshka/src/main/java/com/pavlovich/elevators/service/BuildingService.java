package com.pavlovich.elevators.service;

import com.google.common.base.Preconditions;
import com.pavlovich.elevators.domain.*;
import com.pavlovich.elevators.domain.controller.AbstractElevatorsController;
import com.pavlovich.elevators.domain.controller.ElevatorsController;
import com.pavlovich.elevators.domain.elevator.Direction;
import com.pavlovich.elevators.domain.elevator.Elevator;
import com.pavlovich.elevators.domain.floor.Floor;
import com.pavlovich.elevators.domain.floor.Human;
import com.pavlovich.elevators.domain.floor.HumanGenerator;
import com.pavlovich.elevators.domain.statistics.ElevatorStatistics;
import com.pavlovich.elevators.domain.statistics.StatisticsStorage;
import com.pavlovich.elevators.validator.ElevatorValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BuildingService {
    public static final int MINIMUM_QUEUE_CAPACITY = 10;

    private final ElevatorValidator validator;

    public BuildingService(ElevatorValidator validator) {
        Preconditions.checkNotNull(validator);

        this.validator = validator;
    }

    public Building createBuilding(AbstractElevatorsController controller, int queueInitCapacity,
                                   int[] loadCapacityValues, float[] upIntensityValues,
                                   float[] downIntensityValues) {
        Preconditions.checkNotNull(controller, "Controller can't be null");
        Preconditions.checkNotNull(loadCapacityValues, "Load capacity values array equals null");
        Preconditions.checkNotNull(upIntensityValues, "Array of generators intensities equals null");
        Preconditions.checkNotNull(downIntensityValues, "Array of generators intensities equals null");

        Preconditions.checkArgument(queueInitCapacity >= MINIMUM_QUEUE_CAPACITY,
                "Queue initialization capacity should be greater than or equal to minimum queue capacity");
        Preconditions.checkArgument(loadCapacityValues.length != 0,
                "'Load capacity values' is empty array");
        Preconditions.checkArgument(upIntensityValues.length != 0,
                "'Up intensity values' is empty array");
        Preconditions.checkArgument(downIntensityValues.length != 0,
                "'Down intensity values' is empty array");

        Preconditions.checkArgument(upIntensityValues.length == downIntensityValues.length,
                "The number of 'up' intensities should be equal amount of 'down' intensities");

        final int correctionOfLastFloor = 1;
        final int floorsAmount = upIntensityValues.length + correctionOfLastFloor;
        List<Floor> floors = new ArrayList<>();
        List<HumanGenerator> generators = new ArrayList<>();

        StatisticsStorage statisticsStorage = new StatisticsStorage();

        processFirstFloor(queueInitCapacity, upIntensityValues[0], floorsAmount, controller, floors, generators);

        for (int i = 1; i < floorsAmount - 1; i++) {
            processIntermediateFloor(i + 1, floorsAmount, floors, upIntensityValues[i],
                    downIntensityValues[i - 1], queueInitCapacity, controller, generators);
        }

        final float lastFloorDownIntensity = downIntensityValues[downIntensityValues.length - 1];
        processLastFloor(queueInitCapacity, lastFloorDownIntensity, floorsAmount, controller, floors, generators);

        Building building = new Building(floors, new ArrayList<>(), generators, controller, statisticsStorage);

        for (int loadCapacityValue : loadCapacityValues) {
            UUID elevatorId = UUID.randomUUID();
            Elevator elevator = new Elevator(elevatorId, loadCapacityValue);
            addElevator(building, elevator);
            statisticsStorage.getElevatorStatistics().put(elevatorId, new ElevatorStatistics());
        }

        controller.setElevators(building.getElevators());

        return building;
    }

    /***
     * Process the first floor of building
     */
    private void processFirstFloor(int queueInitCapacity, float generatorIntensity, int floorsAmount,
                                   ElevatorsController controller, List<Floor> floors,
                                   List<HumanGenerator> generators) {
        Preconditions.checkNotNull(controller, "Controller can't be null");
        Preconditions.checkNotNull(floors, "Floors list can't be null");
        Preconditions.checkNotNull(generators, "Generators list can't be null");

        List<Human> peopleQueue = new ArrayList<>(queueInitCapacity);
        Lock peopleQueueLocker = new ReentrantLock();

        final int firstFloorNumber = 1;

        Floor floor = new Floor(firstFloorNumber, peopleQueue, peopleQueueLocker,
                null, null, controller);
        HumanGenerator generator = new HumanGenerator(generatorIntensity, floorsAmount, floor,
                Direction.UP);

        floors.add(floor);
        generators.add(generator);
    }

    /***
     * Process the last floor of building
     */
    private void processLastFloor(int queueInitCapacity, float generatorIntensity, int floorsAmount,
                                  ElevatorsController controller, List<Floor> floors,
                                  List<HumanGenerator> generators) {
        Preconditions.checkNotNull(controller, "Controller can't be null");
        Preconditions.checkNotNull(floors, "Floors list can't be null");
        Preconditions.checkNotNull(generators, "Generators list can't be null");

        List<Human> peopleQueue = new ArrayList<>(queueInitCapacity);
        Lock peopleQueueLocker = new ReentrantLock();

        Floor floor = new Floor(floorsAmount, null, null,
                peopleQueue, peopleQueueLocker, controller);
        HumanGenerator generator = new HumanGenerator(generatorIntensity, floorsAmount, floor,
                Direction.DOWN);

        floors.add(floor);
        generators.add(generator);
    }

    /***
     * Process intermediate floors (i.e. [2..floorsAmount-1] ) of building
     */
    private void processIntermediateFloor(int floorNumber, int floorsAmount, List<Floor> floors,
                                          float upGeneratorIntensity, float downGeneratorIntensity,
                                          int queueInitCapacity, ElevatorsController controller,
                                          List<HumanGenerator> generators) {
        Preconditions.checkNotNull(floors, "Floors list can't be null");
        Preconditions.checkNotNull(controller, "Controller can't be null");
        Preconditions.checkNotNull(generators, "Generators list can't be null");

        List<Human> upQueue = new ArrayList<>(queueInitCapacity);
        List<Human> downQueue = new ArrayList<>(queueInitCapacity);
        Lock upQueueLocker = new ReentrantLock();
        Lock downQueueLocker = new ReentrantLock();

        Floor floor = new Floor(floorNumber, upQueue, upQueueLocker, downQueue, downQueueLocker, controller);
        floors.add(floor);

        HumanGenerator generator = new HumanGenerator(upGeneratorIntensity, floorsAmount, floor, Direction.UP);
        generators.add(generator);
        generator = new HumanGenerator(downGeneratorIntensity, floorsAmount, floor, Direction.DOWN);
        generators.add(generator);
    }

    private void addElevator(Building building, Elevator elevator) {
        Preconditions.checkNotNull(building, "Building can't be null");
        Preconditions.checkNotNull(elevator, "Elevator can't be bull");

        validator.validate(elevator);

        if (building.getElevators().contains(elevator)) {
            throw new RuntimeException("Elevator was already added");
        }

        if (building.getController() != null) {
            elevator.setElevatorsController(building.getController());
        }

        elevator.setBuilding(building);
        building.getElevators().add(elevator);
    }
}
