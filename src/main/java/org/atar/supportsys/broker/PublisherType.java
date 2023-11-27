package org.atar.supportsys.broker;

public enum PublisherType {
    ANIMAL,
    ANIMAL_LOCATION,
    AVALANCHE,
    BTS_LOCATION,
    SAT_PROBLEM,
    SIMULATION_CONDITIONS,
    STATISTIC_INFO,
    SYSTEM_EVENTS,
    TOURIST,
    TOURIST_LOCATION,
    WEATHER_INFO,
    WEATHER; // prawdopodobnie nie używane

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
