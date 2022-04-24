package com.pavlovich.elevators.domain;

import com.pavlovich.elevators.domain.controller.AbstractElevatorsController;
import com.pavlovich.elevators.domain.elevator.Elevator;
import com.pavlovich.elevators.domain.floor.Floor;
import com.pavlovich.elevators.domain.floor.HumanGenerator;
import com.pavlovich.elevators.domain.statistics.StatisticsStorage;
import com.pavlovich.elevators.service.FloorService;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class Building {
    @Setter
    private AbstractElevatorsController controller;
    private final List<Floor> floors;
    private final List<Elevator> elevators;
    private final List<HumanGenerator> generators;
    private final FloorService floorService;

    public Building(List<Floor> floors, List<Elevator> elevators, List<HumanGenerator> generators,
                    AbstractElevatorsController controller, StatisticsStorage statisticsStorage) {
        this.floors = floors;
        this.elevators = elevators;
        this.generators = generators;
        this.controller = controller;
        this.floorService = new FloorService(floors.size(), statisticsStorage);
    }

    public void startElevators() {
        for (Elevator elevator : elevators) {
            elevator.start();
        }
    }

    public void startGenerators() {
        for (HumanGenerator humanGenerator : generators) {
            humanGenerator.start();
        }
    }

    public void startController() {
        controller.start();
    }

    public void startBuilding() {
        startController();
        startElevators();
        startGenerators();
    }

    public void stopElevators() {
        for (Elevator elevator : elevators) {
            elevator.finish();
        }
    }

    public void stopGenerators() {
        for (HumanGenerator humanGenerator : generators) {
            humanGenerator.finish();
        }
    }

    public void stopController() {
        controller.finish();
    }

    public void stopBuilding() {
        stopController();
        stopElevators();
        stopGenerators();
    }

    public StatisticsStorage getStatistics() {
        return floorService.getStatistics();
    }
}
