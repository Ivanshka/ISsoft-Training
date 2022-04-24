package com.pavlovich.elevators.service;

public class BuildingServiceSample {
    public static int getTimeToWaitByIntensivity(float humanPerSecond) {
        return (int) (1000 / humanPerSecond);
    }
}
