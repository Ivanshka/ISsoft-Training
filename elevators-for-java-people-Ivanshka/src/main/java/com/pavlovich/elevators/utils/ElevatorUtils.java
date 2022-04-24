package com.pavlovich.elevators.utils;

import com.pavlovich.elevators.domain.Building;
import com.pavlovich.elevators.domain.elevator.Elevator;
import com.pavlovich.elevators.domain.floor.Floor;

import java.util.List;

public class ElevatorUtils {

    public static void printElevators(List<Elevator> elevators) {
        System.out.println("     | Direction |       State       | People | Floor | TFloor | Target floors");
        for (int i = 0; i < elevators.size(); i++) {
            Elevator elevator = elevators.get(i);
            System.out.println("El " + i + " | " + ElevatorUtils.toTableString(elevator));
        }
        System.out.println();
    }

    private static String toTableString(Elevator elevator) {
        return String.format("%1$-9s | %2$-17s | %3$-6s | %4$-5s | %5$-6s | %6$s ",
                elevator.getDirection(), elevator.getElevatorState(), elevator.getPeopleAmount(),
                elevator.getCurrentFloor(), elevator.getTargetFloor(), elevator.getTargetFloorsList());
    }

    public static void printBuilding(Building building) {
        final List<Floor> floors = building.getFloors();
        final List<Elevator> elevators = building.getElevators();

        for (int i = floors.size() - 1; i > -1; i--) {
            System.out.print("|");
            for (int j = 0; j < elevators.size(); j++) {
                Elevator elevator = elevators.get(j);
                if (elevator.getCurrentFloor() - 1 == i)
                    System.out.print(" EL " + j + " |");
                else
                    System.out.print("      |");
            }
            int up = floors.get(i).getUpQueue() == null ? 0 : floors.get(i).getUpQueue().size();
            int down = floors.get(i).getDownQueue() == null ? 0 : floors.get(i).getDownQueue().size();
            System.out.println(" people: " + up + " UP & " + down + " DOWN");
        }
    }
}
