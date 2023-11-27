package org.atar.supportsys.event;

import com.google.gson.Gson;

public class Event {

    private final String type;
    private final String realTime;
    private final String simulationTime;
    private final String message;

    public Event(String _type, String _realTime, String _simulationTime, String _message) {
        this.type = _type;
        this.realTime = _realTime;
        this.simulationTime = _simulationTime;
        this.message = _message;
    }

    public static Event createEventFromString(String eventString) {
        Gson gson = new Gson();
        Event event = gson.fromJson(eventString, Event.class);
        return event;
    }

    public String getType() {
        return type;
    }

    public String getRealTime() {
        return realTime;
    }

    public String getSimulationTime() {
        return simulationTime;
    }

    public String getMessage() {
        return message;
    }
}
