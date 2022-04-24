package com.pavlovich.elevators.domain.statistics;

import lombok.Getter;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class StatisticsStorage {
    private final Map<UUID, ElevatorStatistics> elevatorStatistics;

    public StatisticsStorage() {
        this.elevatorStatistics = new ConcurrentHashMap<>();
    }
}
