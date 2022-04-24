package com.pavlovich.elevators;

import com.pavlovich.elevators.domain.Building;
import com.pavlovich.elevators.domain.elevator.Elevator;
import com.pavlovich.elevators.domain.controller.ElevatorsControllerImpl;
import com.pavlovich.elevators.domain.statistics.ElevatorStatistics;
import com.pavlovich.elevators.service.BuildingService;
import com.pavlovich.elevators.utils.ElevatorUtils;
import com.pavlovich.elevators.validator.ElevatorValidator;
import lombok.SneakyThrows;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Runner {

    @SneakyThrows
    public static void main(String[] args) {
        final int callQueueCapacity = 1000;
        final int queueInitCapacity = 15;
        final int[] loadCapacityValues = {400, 400};
        final float[] upIntensityValues = {0.5f, 0.5f};
        final float[] downIntensityValues = {0.5f, 0.5f};

        final int updateInfoFrequency = 1;
        final int delayTimeMillis = 1000 / updateInfoFrequency;

        ElevatorsControllerImpl controller = new ElevatorsControllerImpl(callQueueCapacity);
        BuildingService buildingService = new BuildingService(new ElevatorValidator());
        Building building = buildingService.createBuilding(controller, queueInitCapacity, loadCapacityValues,
                upIntensityValues, downIntensityValues);
        building.startBuilding();

        List<Elevator> elevators = building.getElevators();

        while (true) {
            clear();

            ElevatorUtils.printBuilding(building);
            System.out.println();
            ElevatorUtils.printElevators(elevators);

            for (int i = 0; i < elevators.size(); i++) {
                Elevator elevator = elevators.get(i);
                final UUID key = elevator.getElevatorId();
                ElevatorStatistics statistics = building.getStatistics().getElevatorStatistics().get(key);

                if (statistics != null)
                    System.out.println("EL " + i + " total: " + statistics.getTotalTransportedPeopleCount() +
                            ", visited floors: " + statistics.getFloorsVisited() + ", passed floors: " +
                            statistics.getFloorsPassed());
            }

            TimeUnit.MILLISECONDS.sleep(delayTimeMillis);
        }
    }

    @SneakyThrows
    private static void clear() {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    }
}
